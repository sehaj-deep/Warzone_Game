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
	 * The ID of the player executing the blockade order.
	 */
	private int d_playerId;

	/**
	 * The game engine instance used for testing.
	 */
	GameEngine d_gameEngine;

	/**
	 * Sets up the test environment before each test case.
	 */
	@Before
	public void setUp() {
		d_gameEngine = new GameEngine();
		d_blockadeOrder = new Blockade("targetCountry", d_gameEngine);
		List<Player> players = new ArrayList<>();

		d_playerId = 0;
		players.add(new Player("0"));
		d_gameEngine.setPlayers(players);
		Map<String, Integer> l_gameBoard = d_gameEngine.getGameBoard();
		l_gameBoard.put("targetCountry", 1);
		d_gameEngine.setGameBoard(l_gameBoard);
	}

	/**
	 * Tests the isValidIssue method with a valid country and army units.
	 */
	@Test
	public void testIsValidIssueWithValidCountryAndArmyUnits() {
		assertTrue(d_blockadeOrder.isValidIssue(d_playerId));
	}

	/**
	 * Tests the isValidIssue method with an invalid country.
	 */
	@Test
	public void testIsValidIssueWithInvalidCountry() {
		d_blockadeOrder = new Blockade("invalidCountry", new GameEngine());
		assertFalse(d_blockadeOrder.isValidIssue(d_playerId));
	}

	/**
	 * Tests the isValidIssue method with no army units in the target country.
	 */
	@Test
	public void testIsValidIssueWithNoArmyUnits() {
		d_gameEngine.getGameBoard().put("targetCountry", 0); // Setting army count to 0
		assertFalse(d_blockadeOrder.isValidIssue(d_playerId));
	}

	/**
	 * Tests the execute method to ensure the army count triples after execution.
	 */
	@Test
	public void testExecute() {
		int initialArmyCount = d_gameEngine.getGameBoard().get("targetCountry");
		d_blockadeOrder.execute(d_playerId);
		int finalArmyCount = d_gameEngine.getGameBoard().get("targetCountry");
		assertEquals(initialArmyCount * 3, finalArmyCount); // Army count should be tripled after execution
	}
}
