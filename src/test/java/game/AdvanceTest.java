package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import models.Country;

public class AdvanceTest {
	static Advance d_advanceOrder;
	static List<Player> d_players;
	static int d_plyrId = 0;
	static int d_opponentId = 1;
	static GameState d_state;
	GameEngineNew d_gameEngine;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before
	public void before() {
		// game engine set up
		d_gameEngine = new GameEngineNew();
		d_players = new ArrayList<>();
		d_players.add(new Player("0"));
		d_players.add(new Player("1"));
		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("korea", "usa", "italy"));
		Set<String> l_ownedCountries2 = new HashSet<>(Arrays.asList("japan", "greece"));
		d_players.get(0).setOwnership(l_ownedCountries);
		d_players.get(1).setOwnership(l_ownedCountries2);
		d_gameEngine.setD_players(d_players);
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
		d_state = new GameState(d_players);
		HashMap<String, Integer> l_board = new HashMap<String, Integer>(); // key: country name. val: num
		l_board.put("korea", 3);
		l_board.put("usa", 12);
		l_board.put("italy", 4);
		l_board.put("japan", 1);
		l_board.put("greece", 1000);
		d_state.setGameBoard(l_board);
	}

	/*
	 * @Test public void testing() {
	 * 
	 * Random random = new Random(); List<Integer> list = new ArrayList<>(); for
	 * (int i = 0; i < 100; i++) { list.add(random.nextInt(10 - 1 + 1) + 1); }
	 * System.out.println(list); }
	 */

	/**
	 * test isValidIssue methods of Advance class test one valid case where
	 * expecting output true from isValid test three invalid cases where expecting
	 * output false from isValid
	 */
	@Test
	public void testIsValidIssue() {
		System.out.println("Testing isValidIssue method of Advance class");

		System.out.println("Testing Valid cases");
		d_advanceOrder = new Advance(1, "korea", "italy", d_gameEngine);
		assertTrue(d_advanceOrder.isValidIssue(d_state, d_plyrId));
		d_advanceOrder = new Advance(1, "korea", "greece", d_gameEngine);
		assertTrue(d_advanceOrder.isValidIssue(d_state, d_plyrId));

		// Invalid case 1: country not occupied by the player
		System.out.println("Testing invalid advance: advance from country not owned");
		d_advanceOrder = new Advance(1, "japan", "italy", d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_state, d_plyrId));

		// Invalid case 2: more armies to be deployed than the armies available in the
		// source country
		System.out.println("Testing invalid advance: advance more armies than availabe in the source country");
		d_advanceOrder = new Advance(4, "korea", "italy", d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_state, d_plyrId));

		// Invalid case 3: negative number of armies for deploy
		System.out.println("Testing invalid advance: negative number of armies for advance");
		d_advanceOrder = new Advance(-1, "korea", "italy", d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_state, d_plyrId));

		// Invalid case: no link from source to target
		System.out.println("Testing invalid advance: advance to a non-neighbor country");
		d_advanceOrder = new Advance(1, "korea", "usa", d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_state, d_plyrId));
		d_advanceOrder = new Advance(1, "korea", "japan", d_gameEngine);
		assertFalse(d_advanceOrder.isValidIssue(d_state, d_plyrId));

		System.out.println("Testing advance.isValidIssue method PASSED!");
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
		d_advanceOrder = new Advance(1, "korea", "italy", d_gameEngine);
		d_advanceOrder.isValidExecute(d_state, d_plyrId);
		assertFalse(d_advanceOrder.getIsAttack());

		System.out.println("isValidExecute Case: attacking the country not owned by the player");
		d_advanceOrder = new Advance(1, "korea", "greece", d_gameEngine);
		d_advanceOrder.isValidExecute(d_state, d_plyrId);
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
		d_advanceOrder = new Advance(9, "usa", "japan", d_gameEngine);
		d_advanceOrder.attack(d_state, d_plyrId);
		assertTrue(d_players.get(d_plyrId).getOwnership().contains("japan"));
		assertFalse(d_players.get(d_opponentId).getOwnership().contains("japan"));

		System.out.println("Attack Case: defender succeeding defence");
		d_advanceOrder = new Advance(3, "italy", "greece", d_gameEngine);
		d_advanceOrder.attack(d_state, d_plyrId);
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
		d_advanceOrder = new Advance(1, "korea", "italy", d_gameEngine);
		d_advanceOrder.isValidExecute(d_state, d_plyrId);
		d_advanceOrder.execute(d_state, d_opponentId);
		int l_numArmy = d_state.getGameBoard().get("italy");
		assertEquals(5, l_numArmy);

		System.out.println("Execute Case: attack some armies from source to target");
		d_advanceOrder = new Advance(7, "usa", "japan", d_gameEngine);
		d_advanceOrder.isValidExecute(d_state, d_plyrId);
		d_advanceOrder.execute(d_state, d_plyrId);
		System.out.println(d_players.get(d_plyrId).getOwnership());
		System.out.println(d_players.get(d_opponentId).getOwnership());

		assertTrue(d_players.get(d_plyrId).getOwnership().contains("japan"));
		assertFalse(d_players.get(d_opponentId).getOwnership().contains("japan"));
		System.out.println("Testing advance.execute method PASSED!");
	}
}