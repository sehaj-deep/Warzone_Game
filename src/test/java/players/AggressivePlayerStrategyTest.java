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
 * AggressivePlayerStrategy
 */
public class AggressivePlayerStrategyTest {
	/**
	 * List of players
	 */
	static Player d_player;

	static AggressivePlayerStrategy d_strategy;

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
		d_strategy = new AggressivePlayerStrategy();
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
	 * Test findStrongestCountry method. Case: country with the most army bordering
	 * enemy territories
	 */
	@Test
	public void testFindStrongestCountryMostArmyInBorder() {
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 12);
		l_board.put("3", 9);
		l_board.put("4", 8);
		d_gameEngine.setGameBoard(l_board);
		d_strategy.findStrongestCountry(d_player, d_gameEngine);
		String l_strongest = d_strategy.getStrongest();
		assertEquals("2", l_strongest);
	}

	/**
	 * Test findStrongestCountry method. Case: country with the most army not border
	 * enemy territories
	 */
	@Test
	public void testFindStrongestCountryMostArmyNotInBorder() {
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 4);
		l_board.put("3", 9);
		l_board.put("4", 8);
		d_gameEngine.setGameBoard(l_board);
		d_strategy.findStrongestCountry(d_player, d_gameEngine);
		String l_strongest = d_strategy.getStrongest();
		assertEquals("2", l_strongest);
		assertFalse("3" == l_strongest);
	}

	@Test
	public void testFindStrongestCountryAllZeroArmies() {
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 0);
		l_board.put("3", 0);
		l_board.put("4", 0);
		d_gameEngine.setGameBoard(l_board);
		d_strategy.findStrongestCountry(d_player, d_gameEngine);
		String l_strongest = d_strategy.getStrongest();
		HashSet<String> l_set = new HashSet<String>();
		l_set.add("2");
		l_set.add("3");
		l_set.add("4");
		assertTrue(l_set.contains(l_strongest));
	}

	/**
	 * Test deployAggressive method. Check whether created Deploy object has the
	 * expected country name and number of army as its data
	 */
	@Test
	public void testDeployAggressive() {
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 4);
		l_board.put("3", 9);
		l_board.put("4", 8);
		d_gameEngine.setGameBoard(l_board);
		Deploy l_order = (Deploy) d_strategy.deployAggressive(d_player, d_gameEngine);
		assertEquals("2", l_order.getCountryName());
		int l_numAvailableArmy = d_reinforcements.get(0);
		assertEquals(l_numAvailableArmy, l_order.getNumArmy());
	}

	/**
	 * Test orderAttack method. Check whether created Advance object has the
	 * expected source country name, target country name, and number of army as its
	 * data
	 */
	@Test
	public void testOrderAttack() {
		HashMap<String, Integer> l_board = new HashMap<>();
		// My armies
		l_board.put("2", 4);
		l_board.put("3", 9);
		l_board.put("4", 8);
		// Opponent armies
		l_board.put("5", 2);
		l_board.put("1", 1);
		d_gameEngine.setGameBoard(l_board);
		System.out.println("Attack countries: " + d_gameEngine.getD_countries());
		System.out.println("Owns: " + d_player.getOwnership());
		System.out.println("board: " + d_gameEngine.getGameBoard());
		d_strategy.findStrongestCountry(d_player, d_gameEngine); // need to know strongest country
		Advance l_order = (Advance) d_strategy.orderAttack(d_player, d_gameEngine);
		System.out.println("Strongest: " + d_strategy.getStrongest());
		System.out.println("Target: " + l_order.getTargetCountry());
		assertEquals(d_strategy.getStrongest(), l_order.getSourceCountry()); // check source country
		assertEquals("1", l_order.getTargetCountry()); // check target country
		int l_numArmyFromStrongest = l_board.get(d_strategy.getStrongest()) - 1;
		assertEquals(l_numArmyFromStrongest, l_order.getNumArmy());
	}

	/**
	 * Test moveToStrongestNeighbors method. Case: here, Country 4 has more armies
	 * than its neighbors 2 and 3. Country 3 is adjacent to 4 only. Country 2
	 * neighboring 4 and territories not owned by the player. Country C adjacent to
	 * A and other player's country. Country A adjacent to C and more armies than C.
	 * Country B neighboring other player's country. Expect targetCountry: 4 or A
	 * 
	 */
	@Test
	public void testMoveToStrongestNeighbor() {
		// set up game board
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 2);
		l_board.put("3", 9);
		l_board.put("4", 16);
		l_board.put("1", 100);
		l_board.put("5", 100);
		l_board.put("B", 1);
		l_board.put("A", 4);
		l_board.put("C", 3);
		d_gameEngine.setGameBoard(l_board);
		// Add more countries
		Country l_countryB, l_countryA, l_countryC;
		l_countryB = new Country(6, "B");
		l_countryA = new Country(7, "A");
		l_countryC = new Country(8, "C");
		l_countryB.addNeighbors(d_gameEngine.getD_countries("1"));
		l_countryA.addNeighbors(l_countryC);
		l_countryC.addNeighbors(l_countryA);
		l_countryC.addNeighbors(d_gameEngine.getD_countries("5"));
		HashMap<String, Country> l_countries = d_gameEngine.getD_countries();
		l_countries.put("B", l_countryB);
		l_countries.put("A", l_countryA);
		l_countries.put("C", l_countryC);

		d_player.conquerCountry("B");
		d_player.conquerCountry("A");
		d_player.conquerCountry("C");

		// set of countries that can appear as target countries in this test case
		HashSet<String> l_setTarget = new HashSet<String>();
		l_setTarget.add("4");
		l_setTarget.add("A");

		// set of countries not owned by the player
		HashSet<String> l_setNotOwned = new HashSet<String>();
		l_setNotOwned.add("1");
		l_setNotOwned.add("5");

		assertEquals(0, d_strategy.getWaitingOrders().size());
		d_strategy.moveToStrongestNeighbor(d_player, d_gameEngine);
		// check whether benevolent move is correct for all orders in waiting orders
		// queue in the benevolent player strategy
		for (int i = 0; i < d_strategy.getWaitingOrders().size(); i++) {
			Advance l_order = (Advance) d_strategy.getWaitingOrders().remove();
			assertTrue(l_setTarget.contains(l_order.getTargetCountry()));
			String l_sourceName = l_order.getSourceCountry();
			assertFalse(l_setNotOwned.contains(l_sourceName)); // can't advance from non-occupied country
			int l_diff = l_board.get(l_sourceName) - 1 - l_order.getNumArmy();
			assertEquals(0, l_diff);
		}
	}

	/**
	 * Test moveToStrongestNeighbors method. Case: All countries has no armies.
	 * Expect no order in waitingOrders queue
	 */
	@Test
	public void testMoveToStrongestNeighborAllZeroArmies() {
		// set up game board
		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 0);
		l_board.put("3", 0);
		l_board.put("4", 0);
		l_board.put("1", 10);
		l_board.put("5", 10);
		d_gameEngine.setGameBoard(l_board);
		assertEquals(0, d_strategy.getWaitingOrders().size());
		d_strategy.moveToStrongestNeighbor(d_player, d_gameEngine);
		assertEquals(0, d_strategy.getWaitingOrders().size());
	}

	/**
	 * Test createOrder method
	 */
	@Test
	public void testCreateOrder() {
		// set up game board

		HashMap<String, Integer> l_board = new HashMap<>();
		l_board.put("2", 2);
		l_board.put("3", 9);
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

		// test attack step
		assertEquals(true, d_strategy.getCanAttack());
		l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
		Advance l_advance = new Advance("placeholder", "example", 1, d_gameEngine);
		assertTrue(l_order.getClass() == l_advance.getClass());
		assertEquals(false, d_strategy.getCanAttack());

		// test army movement step
		assertEquals(0, d_strategy.getWaitingOrders().size());
		int i = 0;
		do {
			l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
			if (i == 0) {
				assertTrue(d_strategy.getWaitingOrders().size() > 0);
			}
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
		l_board.put("1", 1);
		l_board.put("5", 1);
		d_gameEngine.setGameBoard(l_board);

		// test deploy step
		assertEquals(true, d_strategy.getCanDeploy());
		Order l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
		Deploy l_deploy = new Deploy("placeholder", 1, d_gameEngine);
		assertTrue(l_order.getClass() == l_deploy.getClass());
		assertEquals(false, d_strategy.getCanDeploy());

		// test attack step
		assertEquals(true, d_strategy.getCanAttack());
		l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
		assertTrue(l_order == null);
		assertEquals(false, d_strategy.getCanAttack());

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
		l_board.put("2", 2);
		l_board.put("3", 9);
		l_board.put("4", 16);
		d_gameEngine.setGameBoard(l_board);
		// setting CanDeploy and CanAttack false for simplicity of testing
		d_strategy.setCanDeploy(false);
		d_strategy.setCanAttack(false);
		Queue<Order> l_waitingOrders = d_strategy.getWaitingOrders();
		l_waitingOrders.add(new Advance("3", "4", 10, d_gameEngine));
		assertEquals(1, d_strategy.getWaitingOrders().size());
		Order l_order = d_strategy.createOrder(d_player, null, d_gameEngine);
		assertEquals(false, d_strategy.getHasOrder());
		assertEquals(0, d_strategy.getWaitingOrders().size());
		assertEquals(null, l_order);
	}
}