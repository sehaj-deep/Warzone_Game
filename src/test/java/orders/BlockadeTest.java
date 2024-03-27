package orders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.GameEngine;
import game.GameState;
import orders.Blockade;
import players.Player;

/**
 * This class contains JUnit tests for the Blockade class.
 */
public class BlockadeTest {
	/**
	 * The Blockade order instance to be tested.
	 */
	private Blockade d_blockadeOrder;

	/**
	 * The GameState instance for testing.
	 */
	private GameState d_gameState;

	/**
	 * The ID of the player executing the blockade order.
	 */
	private int d_playerId;

	/**
	 * Sets up the test environment before each test case.
	 */
	@Before
	public void setUp() {
		d_blockadeOrder = new Blockade("targetCountry", new GameEngine());
		d_gameState = new GameState();
		List<Player> players = new ArrayList<>();

		d_playerId = 0;
		players.add(new Player("0"));
		d_gameState.setPlayers(players);

		Map<String, Integer> l_gameBoard = d_gameState.getGameBoard();
		l_gameBoard.put("targetCountry", 1);
		d_gameState.setGameBoard(l_gameBoard);
	}

	/**
	 * Tests the isValidIssue method with a valid country and army units.
	 */
	@Test
	public void testIsValidIssueWithValidCountryAndArmyUnits() {
		assertTrue(d_blockadeOrder.isValidIssue(d_gameState, d_playerId));
	}

	/**
	 * Tests the isValidIssue method with an invalid country.
	 */
	@Test
	public void testIsValidIssueWithInvalidCountry() {
		d_blockadeOrder = new Blockade("invalidCountry", new GameEngine());
		assertFalse(d_blockadeOrder.isValidIssue(d_gameState, d_playerId));
	}

	/**
	 * Tests the isValidIssue method with no army units in the target country.
	 */
	@Test
	public void testIsValidIssueWithNoArmyUnits() {
		d_gameState.getGameBoard().put("targetCountry", 0); // Setting army count to 0
		assertFalse(d_blockadeOrder.isValidIssue(d_gameState, d_playerId));
	}

	/**
	 * Tests the execute method to ensure the army count triples after execution.
	 */
	@Test
	public void testExecute() {
		int initialArmyCount = d_gameState.getGameBoard().get("targetCountry");
		d_blockadeOrder.execute(d_gameState, d_playerId);
		int finalArmyCount = d_gameState.getGameBoard().get("targetCountry");
		assertEquals(initialArmyCount * 3, finalArmyCount); // Army count should be tripled after execution
	}
}
