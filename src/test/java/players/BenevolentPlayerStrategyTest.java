package players;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import game.GameEngine;
import map.Country;
import orders.Advance;
import orders.Deploy;
import orders.Order;

/**
 * This class is designed to do unit test of features in
 * BenevolentPlayerStrategy
 */
public class BenevolentPlayerStrategyTest {
	/**
	 * List of players
	 */
	static Player d_player;

	static BenevolentPlayerStrategy d_strategy;

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
		d_player = new Player(Integer.toString(0));
		d_strategy = new BenevolentPlayerStrategy();
		d_player.setPlayerStrategy(d_strategy);
		d_gameEngine = new GameEngine();
		d_reinforcements = d_gameEngine.getReinforcements();
		d_reinforcements.clear();
		d_gameEngine.getPlayers().add(d_player);
		d_reinforcements.add(9);
		d_gameEngine.setReinforcements(d_reinforcements);

		// setup map
		// 1 -> 3, 5
		// 2 -> 1, 4, 5
		// 3 -> 4
		// 4 -> 2, 3
		// 5 -> 1, 2
		Country l_country1, l_country2, l_country3, l_country4, l_country5;
		l_country1 = new Country(1, "1");
		l_country2 = new Country(2, "2");
		l_country3 = new Country(3, "3");
		l_country4 = new Country(4, "4");
		l_country5 = new Country(5, "5");
		l_country1.addNeighbors(l_country3);
		l_country1.addNeighbors(l_country5);
		l_country3.addNeighbors(l_country4);
		l_country2.addNeighbors(l_country4);
		l_country2.addNeighbors(l_country5);
		l_country2.addNeighbors(l_country1);
		l_country5.addNeighbors(l_country1);
		l_country5.addNeighbors(l_country2);
		l_country4.addNeighbors(l_country3);
		l_country4.addNeighbors(l_country2);
		HashMap<String, Country> l_countries = d_gameEngine.getD_countries();
		l_countries.put("1", l_country1);
		l_countries.put("2", l_country2);
		l_countries.put("3", l_country3);
		l_countries.put("4", l_country4);
		l_countries.put("5", l_country5);
		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("2", "3", "4"));
		d_gameEngine.getPlayers().get(0).setOwnership(l_ownedCountries);

	}

	/**
	 * Test findWeakestCountry method. Case: the weakest country is not in border.
	 */
	@Test
	public void testFindWeakestContryNotInBorder() {
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 5);
		l_board.put("3", 4);
		l_board.put("4", 8);
		d_gameEngine.setGameBoard(l_board);
		d_strategy.findWeakestCountry(d_player, d_gameEngine);
		String l_weakest = d_strategy.getWeakest();
		assertEquals("3", l_weakest);
	}

	/**
	 * Test findWeakestCountry method. Case: the weakest country is in border.
	 */
	@Test
	public void testFindWeakestContryInBorder() {
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 2);
		l_board.put("3", 4);
		l_board.put("4", 3);
		d_gameEngine.setGameBoard(l_board);
		d_strategy.findWeakestCountry(d_player, d_gameEngine);
		String l_weakest = d_strategy.getWeakest();
		assertEquals("2", l_weakest);
	}

	/**
	 * Test findWeakestCountry method. Case: All countries has no armies
	 */
	@Test
	public void testFindWeakestContryAllZeroArmies() {
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 0);
		l_board.put("3", 0);
		l_board.put("4", 0);
		d_gameEngine.setGameBoard(l_board);
		d_strategy.findWeakestCountry(d_player, d_gameEngine);
		String l_weakest = d_strategy.getWeakest();
		HashSet<String> l_set = new HashSet<String>();
		l_set.add("2");
		l_set.add("3");
		l_set.add("4");
		assertTrue(l_set.contains(l_weakest));
	}

	/**
	 * Test deployToWeakest method. Check whether created Deploy object has the
	 * expected country name and number of army as its data
	 */
	@Test
	public void testDeployToWeakest() {
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 2);
		l_board.put("3", 4);
		l_board.put("4", 3);
		d_gameEngine.setGameBoard(l_board);
		assertEquals(true, d_strategy.getCanDeploy());
		Deploy l_order = (Deploy) d_strategy.deployToWeakest(d_player, d_gameEngine);
		assertEquals("2", l_order.getCountryName());
		int l_numAvailableArmy = d_reinforcements.get(0);
		assertEquals(l_numAvailableArmy, l_order.getNumArmy());
		assertEquals(false, d_strategy.getCanDeploy());
	}

	/**
	 * Test moveToWeakerNeighbors method. Case: here, Country 4 has more armies than
	 * its neighbors 2 and 5. Country 3 is close to 4 only. Country 2 adjacent to 4
	 * and territories not owned by the player.
	 */
	@Test
	public void testMoveToWeakerNeighbors() {
		// set up game board
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 1);
		l_board.put("3", 4);
		l_board.put("4", 16);
		l_board.put("1", 1);
		l_board.put("5", 1);
		d_gameEngine.setGameBoard(l_board);
		HashSet<String> l_set = new HashSet<String>();
		l_set.add("1");
		l_set.add("5");

		d_strategy.moveToWeakerNeighbors(d_player, d_gameEngine);
		System.out.println("Test moveToWeakerNeighbors output: " + d_strategy.getWaitingOrders());
		// check whether benevolent move is correct for all orders in waiting orders
		// queue in the benevolent player strategy
		for (int i = 0; i < d_strategy.getWaitingOrders().size(); i++) {
			Advance l_order = (Advance) d_strategy.getWaitingOrders().remove();
			assertEquals("4", l_order.getSourceCountry());
			String l_neighborName = l_order.getTargetCountry();
			assertFalse(l_set.contains(l_neighborName));
			int l_diff = l_board.get("4") / 3 - l_board.get(l_neighborName) - l_order.getNumArmy();
			assertEquals(0, l_diff);
		}
	}

	/**
	 * Test moveToWeakerNeighbors method. Case: All countries has no armies. Expect
	 * no order in waitingOrders queue in the BenevolentPlayerStrategy object
	 */
	@Test
	public void testMoveToWeakerNeighborsAllZeroArmies() {
		// set up game board
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 0);
		l_board.put("3", 0);
		l_board.put("4", 0);
		l_board.put("1", 10);
		l_board.put("5", 10);
		d_gameEngine.setGameBoard(l_board);
		d_strategy.moveToWeakerNeighbors(d_player, d_gameEngine);
		assertEquals(0, d_strategy.getWaitingOrders().size());
	}

	/**
	 * Test createOrder method
	 */
	@Test
	public void testCreateOrder() {
		// set up game board

		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 1);
		l_board.put("3", 4);
		l_board.put("4", 16);
		l_board.put("1", 1);
		l_board.put("5", 1);
		d_gameEngine.setGameBoard(l_board);

		// test deploy step
		assertEquals(true, d_strategy.getCanDeploy());
		Order l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
		Deploy l_deploy = new Deploy("placeholder", 1, d_gameEngine);
		assertTrue(l_order.getClass() == l_deploy.getClass());
		assertEquals(false, d_strategy.getCanDeploy());

		// test army movement step
		assertEquals(0, d_strategy.getWaitingOrders().size());
		int i = 0;
		do {
			l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
			if (i == 0) {
				assertTrue(d_strategy.getWaitingOrders().size() > 0);
			}
			Advance l_advance = new Advance("placeholder", "example", 1, d_gameEngine);
			assertTrue(l_order.getClass() == l_advance.getClass());
			i++;
		}
		while (d_strategy.getWaitingOrders().size() == 0);

	}

	/**
	 * Test createOrder method. Special Case: All countries has no armies. Expect no
	 * order in waitingOrders queue in the BenevolentPlayerStrategy object
	 */
	@Test
	public void testCreateOrderAllZeroArmies() {
		// set up game board
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 0);
		l_board.put("3", 0);
		l_board.put("4", 0);
		l_board.put("1", 10);
		l_board.put("5", 10);
		d_gameEngine.setGameBoard(l_board);

		// test deploy step
		assertEquals(true, d_strategy.getCanDeploy());
		Order l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
		Deploy l_deploy = new Deploy("placeholder", 1, d_gameEngine);
		assertTrue(l_order.getClass() == l_deploy.getClass());
		assertEquals(false, d_strategy.getCanDeploy());
		// test army movement step
		assertEquals(0, d_strategy.getWaitingOrders().size());
		d_strategy.createOrder(d_player, null, d_gameEngine);
		assertEquals(0, d_strategy.getWaitingOrders().size());
	}

	/**
	 * Test createOrder method. Special Case: Invalid order created in the method.
	 * createOrder method expects to ignore the invalid Order and return null
	 */
	@Test
	public void testCreateOrderInvalidOrder() {
		// set up game board
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 1);
		l_board.put("3", 4);
		l_board.put("4", 16);
		d_gameEngine.setGameBoard(l_board);
		d_strategy.setCanDeploy(false);
		Queue<Order> l_waitingOrders = d_strategy.getWaitingOrders();
		l_waitingOrders.add(new Advance("3", "4", 10, d_gameEngine));
		assertEquals(1, d_strategy.getWaitingOrders().size());
		Order l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
		assertEquals(false, d_strategy.getHasOrder());
		assertEquals(0, d_strategy.getWaitingOrders().size());
		assertEquals(null, l_order);
	}
}