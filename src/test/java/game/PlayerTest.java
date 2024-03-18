package game;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is designed to do unit test of features in Player class
 */
public class PlayerTest {
	static Player d_player;
	static GameEngine d_gameEngine;
	static List<Integer> d_reinforcements;
	static int d_plyrId = 0;
	static GameState d_state;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before
	public void before() {
		d_player = new Player(Integer.toString(0));
		System.out.println("Test Player Setup completed");
		d_gameEngine = new GameEngine();
		d_state = d_gameEngine.getGameState();
		d_reinforcements = d_state.getReinforcements();
		d_reinforcements.clear();
		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("korea", "usa", "italy"));
		d_gameEngine.getGameState().getPlayers().add(d_player);
		d_gameEngine.getGameState().getPlayers().get(0).setOwnership(l_ownedCountries);
		d_reinforcements.add(9);
		d_state.setReinforcements(d_reinforcements);
	}

	/**
	 * test issue_order function of Player
	 * 
	 * check whether issuance of an order successfully adds that order to the list
	 * of orders
	 */
	@Test
	public void testIssueOrder() {
		System.out.println("Testing issue_order method of Player class");
		System.out.println("Initial list of orders: " + d_player.getListOrders());

		System.out.println("Order1 from the player");
		String[] l_tokens = { "deploy", "korea", "4" };
		d_player.issue_order(l_tokens, d_gameEngine);
		assertEquals(1, d_player.getListOrders().size());

		System.out.println("Order2 from the player");
		String[] l_tokens2 = { "deploy", "usa", "3" };
		d_player.issue_order(l_tokens2, d_gameEngine);
		assertEquals(2, d_player.getListOrders().size());

		System.out.println("Testing issue_order method PASSED!\n");
	}

	/**
	 * test next_order function of Player
	 * 
	 * check whether next_order successfully extracts an order from the list of
	 * orders
	 */
	@Test
	public void testNextOrder() {
		System.out.println("Testing next_order method of Player class");
		String[] l_tokens = { "deploy", "korea", "4" };
		d_player.issue_order(l_tokens, d_gameEngine);
		String[] l_tokens2 = { "deploy", "usa", "3" };
		d_player.issue_order(l_tokens2, d_gameEngine);
		System.out.println("Initial list of orders: " + d_player.getListOrders());
		Order nextOrder = d_player.next_order();
		System.out.println("Next Order for execution: " + nextOrder);
		assertEquals(1, d_player.getListOrders().size());

		System.out.println("Testing next_order method PASSED!\n");
	}
}