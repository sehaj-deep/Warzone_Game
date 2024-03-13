package phases;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import game.Deploy;
import game.GameEngineNew;
import game.GameState;
import game.Player;
import map.MapEditor;

/**
 * This class tests executeOrderPhase class in JUnit testing
 * 
 */
public class ExecuteOrdersPhaseTest {
	private static GameState d_state;
	private static MapEditor d_gMap;
	ExecuteOrdersPhase d_executeOrdersPhase;
	private GameEngineNew d_gameEngine;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 * 
	 */
	@Before
	public void before() {
		d_gameEngine = new GameEngineNew();
		d_executeOrdersPhase = new ExecuteOrdersPhase(d_gameEngine);
		System.out.println("Setting up a game state to test ExecuteOrderPhase");
		List<Integer> l_reinforcements = new ArrayList<>(Arrays.asList(10, 15)); // set up reinforcement pool
		System.out.println("Reinforcements available to Player0 and Player1: " + l_reinforcements);
		// set up players list
		List<Player> l_players = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Player l_player = new Player(Integer.toString(i));
			// provide countries owned by this player
			l_player.setOwnership(new HashSet<String>(Arrays.asList(Integer.toString(2 * i), Integer.toString(2 * i + 1))));
			System.out.println("Player" + i + " has countries: " + l_player.getOwnership());
			int l_deployNumArmy = l_reinforcements.get(i) / (2 + i);
			for (int j = 0; j < (2 + i); j++) { // add Orders for testing purpose
				// Here, player0 has more orders than player1
				l_player.issue_order(new Deploy(l_deployNumArmy, Integer.toString((2 * i) + (j % 2))));
			}
			l_players.add(l_player);
		}
		d_state = new GameState(l_players);
		HashMap<String, Integer> boardBefore = new HashMap<String, Integer>();
		boardBefore.put("0", 0);
		boardBefore.put("1", 0);
		boardBefore.put("2", 0);
		boardBefore.put("3", 0);
		d_state.setGameBoard(boardBefore);
		d_state.setReinforcements(l_reinforcements);
	}

	/**
	 * Test roundRobinExecution method Check whether the game board is changed
	 * according to the issued orders
	 */
	@Test
	public void testRoundRobinExecution() {
		System.out.println("Testing RoundRobinExecution method");
		System.out.println("Game Board before: " + d_state.getGameBoard());
		d_executeOrdersPhase.roundRobinExecution(d_state, d_gMap);
		HashMap<String, Integer> l_boardAfter = new HashMap<String, Integer>();
		l_boardAfter.put("0", 5);
		l_boardAfter.put("1", 0);
		l_boardAfter.put("2", 5);
		l_boardAfter.put("3", 0);
		System.out.println("Game Board After: " + d_state.getGameBoard());
		assertEquals(l_boardAfter, d_state.getGameBoard());
		System.out.println("Testing ExecuteOrdersPhase.RoundRobinExecution methods PASSSED");
	}

	/**
	 * Test the getNumAllOrders method Check whether total number of orders for all
	 * players is computed correctly
	 */
	@Test
	public void testGetNumAllOrders() {
		System.out.println("Testing getNumAllOrders method");
		int l_totNumOrders = d_executeOrdersPhase.getNumAllOrders(d_state);
		assertEquals(5, l_totNumOrders);
		System.out.println("Testing ExecuteOrdersPhase.getNumAllOrders() method PASSED!");
	}

	/**
	 * Test the whole phase in ExecuteOrdersPhase
	 */
	@Test
	public void testExecuteOrdersPhase() {
		System.out.println("Testing ExecuteOrdersPhase method");
		System.out.println("Game Board before: " + d_state.getGameBoard());
		d_executeOrdersPhase.run(d_state, d_gMap);
		// expected state of the game board after successful execution of all orders
		HashMap<String, Integer> l_boardAfter = new HashMap<String, Integer>();
		l_boardAfter.put("0", 5);
		l_boardAfter.put("1", 5);
		l_boardAfter.put("2", 10);
		l_boardAfter.put("3", 5);
		System.out.println("Game Board After: " + d_state.getGameBoard());
		assertEquals(l_boardAfter, d_state.getGameBoard());
		int l_totNumOrders = 0;
		for (int i = 0; i < d_state.getPlayers().size(); i++) {
			// print out a list of orders per player after the execute orders phase
			Player l_player = d_state.getPlayers().get(i);
			System.out.println("Player " + l_player.getPlayerName() + " orders left: " + l_player.getListOrders());
			l_totNumOrders = l_totNumOrders + l_player.getListOrders().size();
		}
		assertEquals(0, l_totNumOrders);
		System.out.println("Testing ExecuteOrdersPhase.RoundRobinExecution methods PASSSED");
	}
}