package orders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import game.GameEngine;
import map.Country;
import players.Player;

/**
 * Represents a test class for validating the functionality of advance orders.
 */
public class AdvanceTest {

	/**
	 * The advance order instance to be tested.
	 */
	static Advance d_advanceOrder;

	/**
	 * The list of players involved in the test.
	 */
	static List<Player> d_players;

	/**
	 * The ID of the player initiating the advance order.
	 */
	static int d_plyrId = 0;

	/**
	 * The ID of the opponent player in the test.
	 */
	static int d_opponentId = 1;

	/**
	 * The game engine instance used for testing.
	 */
	GameEngine d_gameEngine;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before
	public void before() {
		// game engine set up
		d_gameEngine = new GameEngine();
		d_players = d_gameEngine.getPlayers();
		d_players.add(new Player("0"));
		d_players.add(new Player("1"));
		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("korea", "usa", "italy"));
		Set<String> l_ownedCountries2 = new HashSet<>(Arrays.asList("japan", "greece"));
		d_players.get(0).setOwnership(l_ownedCountries);
		d_players.get(1).setOwnership(l_ownedCountries2);

		Country l_country1, l_country2, l_country3, l_country4, l_country5;
		l_country1 = new Country(0, "korea");
		l_country2 = new Country(1, "usa");
		l_country3 = new Country(2, "italy");
		l_country4 = new Country(3, "japan");
		l_country5 = new Country(4, "greece");

		l_country1.addNeighbors(l_country3);
		l_country1.addNeighbors(l_country5);
		l_country2.addNeighbors(l_country4);
		l_country3.addNeighbors(l_country5);
		HashMap<String, Country> l_countries = d_gameEngine.getD_countries();
		l_countries.put("korea", l_country1);
		l_countries.put("usa", l_country2);
		l_countries.put("italy", l_country3);
		l_countries.put("japan", l_country4);
		l_countries.put("greece", l_country5);

		// game board set up
		HashMap<String, Integer> l_board = new HashMap<String, Integer>(); // key: country name. val: num
		l_board.put("korea", 3);
		l_board.put("usa", 12);
		l_board.put("italy", 4);
		l_board.put("japan", 1);
		l_board.put("greece", 1000);
		d_gameEngine.setGameBoard(l_board);
	}

	/**
     * Test isValidIssue method of Advance class for valid cases.
     */
    @Test
    public void testIsValidIssue_ValidCases() {

		System.out.println("Testing Valid cases");
        d_advanceOrder = new Advance("korea", "italy", 1, d_gameEngine);
        assertTrue(d_advanceOrder.isValidIssue(d_plyrId));
        d_advanceOrder = new Advance("korea", "greece", 1, d_gameEngine);
        assertTrue(d_advanceOrder.isValidIssue(d_plyrId));

		System.out.println("Testing advance.isValidIssue method for valid cases PASSED!");

    }

	/**
	 * Test isValidIssue method of Advance class for invalid cases where expecting
	 */
	@Test
	public void testIsValidIssue_InvalidCases() {
		System.out.println("Testing invalid advance: advance from country not owned");
		d_advanceOrder = new Advance("japan", "italy", 1, d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_plyrId));
		
		System.out.println("Testing invalid advance: advance more armies than available in the source country");
		d_advanceOrder = new Advance("korea", "italy", 4, d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_plyrId));
		
		System.out.println("Testing invalid advance: negative number of armies for advance");
		d_advanceOrder = new Advance("korea", "italy", -1, d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_plyrId));
		
		System.out.println("Testing invalid advance: advance to a non-neighbor country");
		d_advanceOrder = new Advance("korea", "usa", 1, d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_plyrId));
		d_advanceOrder = new Advance("korea", "japan", 1, d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_plyrId));
		
		System.out.println("Testing advance.isValidIssue method for invalid cases PASSED!");
	}

	/**
	 * Test isValidExecute methods of Advance class Only test whether d_isAttacking
	 * is set true if attacking advancement Other validation won't be tested because
	 * isValidExecute calls isValidIssue for that
	 * 
	 */
	@Test
	public void testIsValidExecute() {
		System.out.println("Testing isValidExecute method of Advance class");

		System.out.println("isValidExecute Case: advancing to the country owned by the player");
		d_advanceOrder = new Advance("korea", "italy", 1, d_gameEngine);
		d_advanceOrder.isValidExecute(d_plyrId);
		assertFalse(d_advanceOrder.getIsAttack());

		System.out.println("isValidExecute Case: attacking the country not owned by the player");
		d_advanceOrder = new Advance("korea", "greece", 1, d_gameEngine);
		d_advanceOrder.isValidExecute(d_plyrId);
		assertTrue(d_advanceOrder.getIsAttack());
		System.out.println("Testing advance.isValidExecute method PASSED!");
	}

	/**
	 * Test attack method. Check whether the change of ownership of target country
	 * is reflected
	 * 
	 */
	@Test
	public void testAttack() {
		System.out.println("Testing Attack method of Advance class");

		System.out.println("Attack Case: conquer succeded");
		d_advanceOrder = new Advance("usa", "japan", 9, d_gameEngine);
		d_advanceOrder.attack(d_plyrId);
		assertTrue(d_players.get(d_plyrId).getOwnership().contains("japan"));
		assertFalse(d_players.get(d_opponentId).getOwnership().contains("japan"));
		// check the move of armies after conquering
		int l_numArmy = d_gameEngine.getGameBoard().get("japan");
		assertTrue(l_numArmy > 1 && l_numArmy <= 9);

		System.out.println("Attack Case: defender succeeding defence");
		d_advanceOrder = new Advance("italy", "greece", 3, d_gameEngine);
		d_advanceOrder.attack(d_plyrId);
		assertFalse(d_players.get(d_plyrId).getOwnership().contains("greece"));
		assertTrue(d_players.get(d_opponentId).getOwnership().contains("greece"));
		System.out.println("Testing advance.attack method PASSED!");
	}

	/**
	 * Test execute method of Advance class. Check whether moving armies or attack
	 * is properly performed based on the advance order inputs
	 * 
	 */
	@Test
	public void testExecute() {
		System.out.println("Testing Execute method of Advance class");

		System.out.println("Execute Case: move some armies from source to target");
		d_advanceOrder = new Advance("korea", "italy", 1, d_gameEngine);
		d_advanceOrder.isValidExecute(d_plyrId);
		d_advanceOrder.execute(d_opponentId);
		int l_numArmy = d_gameEngine.getGameBoard().get("italy");
		assertEquals(5, l_numArmy);

		System.out.println("Execute Case: attack some armies from source to target");
		d_advanceOrder = new Advance("usa", "japan", 7, d_gameEngine);
		d_advanceOrder.isValidExecute(d_plyrId);
		d_advanceOrder.execute(d_plyrId);
		System.out.println("Player owns " + d_players.get(d_plyrId).getOwnership());
		System.out.println("Opponent owns " + d_players.get(d_opponentId).getOwnership());

		assertTrue(d_players.get(d_plyrId).getOwnership().contains("japan"));
		assertFalse(d_players.get(d_opponentId).getOwnership().contains("japan"));
		System.out.println("Testing advance.execute method PASSED!");
	}
}