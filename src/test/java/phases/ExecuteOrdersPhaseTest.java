package phases;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import game.Advance;
import game.GameEngine;
import game.GameState;
import game.Player;
import models.Country;

/**
 * This class tests executeOrderPhase class in JUnit testing
 * 
 */
public class ExecuteOrdersPhaseTest {
	private static GameState d_state;
	ExecuteOrdersPhase d_executeOrdersPhase;
	private GameEngine d_gameEngine;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 * 
	 */
	@Before
	public void before() {
		d_gameEngine = new GameEngine();
		d_state = d_gameEngine.getGameState();
		HashMap<String, Integer> boardBefore = new HashMap<String, Integer>();
		boardBefore.put("0", 0);
		boardBefore.put("1", 0);
		boardBefore.put("2", 0);
		boardBefore.put("3", 0);
		d_state.setGameBoard(boardBefore);
		List<Integer> l_reinforcements = new ArrayList<>(Arrays.asList(10, 15)); // set up reinforcement pool
		d_state.setReinforcements(l_reinforcements);
		d_executeOrdersPhase = new ExecuteOrdersPhase(d_gameEngine);
		System.out.println("Setting up a game state to test ExecuteOrderPhase");
		System.out.println("Reinforcements available to Player0 and Player1: " + l_reinforcements);
		// set up players list
		for (int i = 0; i < 2; i++) {
			Player l_player = new Player(Integer.toString(i));
			// provide countries owned by this player
			d_gameEngine.getGameState().getPlayers().add(l_player);
			l_player.setOwnership(new HashSet<String>(Arrays.asList(Integer.toString(2 * i), Integer.toString(2 * i + 1))));
			System.out.println("Player" + i + " has countries: " + l_player.getOwnership());
			int l_deployNumArmy = l_reinforcements.get(i) / (2 + i);
			for (int j = 0; j < (2 + i); j++) { // add Orders for testing purpose
				// Here, player0 has more orders than player1
				String[] l_tokens = { "deploy", Integer.toString((2 * i) + (j % 2)), Integer.toString(l_deployNumArmy) };
				l_player.issue_order(l_tokens, d_gameEngine);
			}
		}
	}

	/**
	 * Test roundRobinExecution method Check whether the game board is changed
	 * according to the issued orders
	 */
	@Test
	public void testRoundRobinExecution() {
		System.out.println("Testing RoundRobinExecution method");
		System.out.println("Game Board before: " + d_state.getGameBoard());
		d_executeOrdersPhase.roundRobinExecution(d_state);
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
	 * Test the whole phase in ExecuteOrdersPhase for deploy scenario
	 */
	@Test
	public void testExecuteOrdersPhaseDeploy() {
		System.out.println("Testing ExecuteOrdersPhase method");
		System.out.println("Game Board before: " + d_state.getGameBoard());
		d_executeOrdersPhase.executeAllOrders();
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

	/**
	 * Test giveCard method check whether a player's total number of card increased
	 * if the player conquered at least one country
	 */
	@Test
	public void testGiveCard() {
		System.out.println("Testing ExecuteOrdersPhase giveCard method");

		// Case: first player no conquer, and second player one conquer successful
		List<Integer> l_countConquestPerPlayer = new ArrayList<>();
		for (int i = 0; i < d_gameEngine.getGameState().getPlayers().size(); i++) {
			l_countConquestPerPlayer.add(i);
		}
		d_executeOrdersPhase.setCountConquestPerPlayer(l_countConquestPerPlayer);
		d_executeOrdersPhase.giveCard();
		List<Player> l_players = d_gameEngine.getGameState().getPlayers();
		int l_numCards;
		l_numCards = l_players.get(0).getCardCount("Bomb") + l_players.get(0).getCardCount("Blockade")
				+ l_players.get(0).getCardCount("Airlift") + l_players.get(0).getCardCount("Diplomacy");
		assertEquals(0, l_numCards);
		l_numCards = l_players.get(1).getCardCount("Bomb") + l_players.get(1).getCardCount("Blockade")
				+ l_players.get(1).getCardCount("Airlift") + l_players.get(1).getCardCount("Diplomacy");
		assertEquals(1, l_numCards);
		System.out.println("Testing ExecuteOrdersPhase.giveCard methods PASSSED");
	}

	/**
	 * Test attack method of ExecuteOrdersPhase check whether player who conquered
	 * one country has incremented value in d_countConquestPerPlayer in
	 * ExecuteOrdersPhase
	 */
	@Test
	public void testAttack() {
		System.out.println("Testing ExecuteOrdersPhase attack method");
		// advance order setup
		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("usa"));
		List<Player> l_players = d_gameEngine.getGameState().getPlayers();

		Player l_player = new Player("2");
		l_player.setOwnership(l_ownedCountries);
		Country l_country1, l_country2;
		l_country1 = new Country(1, "usa");
		l_country2 = new Country(2, "japan");
		l_country1.addNeighbors(l_country2);
		l_players.get(0).conquerCountry("japan");

		HashMap<String, Country> l_countries = d_gameEngine.getD_countries();
		l_countries.put("usa", l_country1);
		l_countries.put("japan", l_country2);

		d_state.getGameBoard().put("usa", 12);
		d_state.getGameBoard().put("italy", 4);
		d_state.getGameBoard().put("japan", 1);

		Advance l_advanceOrder = new Advance("usa", "japan", 9, d_gameEngine);
		d_gameEngine.getGameState().getPlayers().add(l_player);

		System.out.println("Testing ExecuteOrdersPhase attack in attack method");
		List<Integer> l_countConquestPerPlayer = new ArrayList<>();
		for (int i = 0; i < d_gameEngine.getGameState().getPlayers().size(); i++) {
			l_countConquestPerPlayer.add(0);
		}
		d_executeOrdersPhase.setCountConquestPerPlayer(l_countConquestPerPlayer);
		l_advanceOrder.isValidExecute(d_state, 2);
		d_executeOrdersPhase.attack(d_state, 2, l_advanceOrder);
		int l_count = d_executeOrdersPhase.getCountConquestPerPlayer().get(2);
		assertEquals(1, l_count);

		System.out.println("Testing ExecuteOrdersPhase.attack methods PASSSED");
	}

	/**
	 * Test attack method of ExecuteOrdersPhase inside the roundRobin method. Check
	 * whether player who conquered one country has incremented value in
	 * d_countConquestPerPlayer in ExecuteOrdersPhase
	 */
	@Test
	public void testRoundRobinAttack() {
		System.out.println("Testing ExecuteOrdersPhase attack method inside rounRobin method");
		// advance order setup
		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("usa"));
		List<Player> l_players = d_gameEngine.getGameState().getPlayers();

		Player l_player = new Player("2");
		l_player.setOwnership(l_ownedCountries);
		Country l_country1, l_country2;
		l_country1 = new Country(1, "usa");
		l_country2 = new Country(2, "japan");
		l_country1.addNeighbors(l_country2);
		l_players.get(0).conquerCountry("japan");

		HashMap<String, Country> l_countries = d_gameEngine.getD_countries();
		l_countries.put("usa", l_country1);
		l_countries.put("japan", l_country2);

		d_state.getGameBoard().put("usa", 12);
		d_state.getGameBoard().put("italy", 4);
		d_state.getGameBoard().put("japan", 1);
		l_players.add(l_player);

		String[] l_tokens = { "advance", "usa", "japan", "9" };
		l_player.issue_order(l_tokens, d_gameEngine);
		d_gameEngine.getGameState().getPlayers().add(l_player);

		System.out.println("Testing ExecuteOrdersPhase attack in attack method");
		List<Integer> l_countConquestPerPlayer = new ArrayList<>();
		for (int i = 0; i < d_gameEngine.getGameState().getPlayers().size(); i++) {
			l_countConquestPerPlayer.add(0);
		}
		d_executeOrdersPhase.setCountConquestPerPlayer(l_countConquestPerPlayer);
		d_executeOrdersPhase.roundRobinExecution(d_state);
		int l_count = d_executeOrdersPhase.getCountConquestPerPlayer().get(2);
		assertEquals(1, l_count);

		System.out.println("Testing ExecuteOrdersPhase.attack inside rounRobin method PASSSED");
	}
}