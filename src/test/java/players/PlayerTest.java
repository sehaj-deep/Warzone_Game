package players;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import game.GameEngine;
import map.Country;
import orders.Order;
import utils.ValidationException;

/**
 * This class is designed to do unit test of features in Player class
 */
public class PlayerTest {
	/**
	 * List of players
	 */
	static Player d_player;

	/**
	 * The game engine instance used for testing.
	 */
	static GameEngine d_gameEngine;

	/**
	 * The list of reinforcement army
	 */
	static List<Integer> d_reinforcements;

	/**
	 * The ID of the player initiating the advance order.
	 */
	static int d_plyrId = 0;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before
	public void before() {
		d_player = new Player(Integer.toString(0), new BenevolentPlayerStrategy());
		System.out.println("Test Player Setup completed");
		d_gameEngine = new GameEngine();
		d_reinforcements = d_gameEngine.getReinforcements();
		d_reinforcements.clear();
		// Korea -> Italy, USA. USA -> Korea, Italy. Italy -> Korea
		Country l_country1, l_country2, l_country3;
		l_country1 = new Country(1, "korea");
		l_country2 = new Country(2, "usa");
		l_country3 = new Country(3, "italy");
		l_country2.addNeighbors(l_country1);
		l_country2.addNeighbors(l_country3);
		l_country1.addNeighbors(l_country3);
		l_country1.addNeighbors(l_country2);
		l_country3.addNeighbors(l_country1);
		HashMap<String, Country> l_countries = d_gameEngine.getD_countries();
		l_countries.put("korea", l_country1);
		l_countries.put("usa", l_country2);
		l_countries.put("italy", l_country3);

		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("korea", "usa", "italy"));
		d_gameEngine.getPlayers().add(d_player);
		d_gameEngine.getPlayers().get(0).setOwnership(l_ownedCountries);
		d_reinforcements.add(9);
		d_gameEngine.setReinforcements(d_reinforcements);

		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("korea", 3);
		l_board.put("usa", 12);
		l_board.put("italy", 4);
		d_gameEngine.setGameBoard(l_board);
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

		assertEquals(0, d_player.getListOrders().size());
		System.out.println("Order1 from the player");
		try {
			d_player.issue_order(null, d_gameEngine);
			assertEquals(1, d_player.getListOrders().size());

			System.out.println("Order2 from the player");
			d_player.issue_order(null, d_gameEngine);
			assertEquals(2, d_player.getListOrders().size());
		}
		catch (ValidationException e) {
			System.out.println("Invallid Order");
		}

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
		try {
			d_player.issue_order(null, d_gameEngine);
			d_player.issue_order(null, d_gameEngine);
		}
		catch (ValidationException e) {
			System.out.println("Invallid Order");
		}

		System.out.println("Initial list of orders: " + d_player.getListOrders());
		Order nextOrder = d_player.next_order();
		System.out.println("Next Order for execution: " + nextOrder);
		assertEquals(1, d_player.getListOrders().size());

		System.out.println("Testing next_order method PASSED!\n");
	}
}