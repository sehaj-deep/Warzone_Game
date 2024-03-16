import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains JUnit tests for the Blockade class.
 */
public class BlockadeTest {
	private Blockade d_blockadeOrder;
	private GameState d_gameState;
	private int d_playerId;

	/**
	 * Sets up the test environment before each test case.
	 */
	@Before
	public void setUp() {
		d_blockadeOrder = new Blockade("targetCountry", new GameEngineNew());
		List<Player> players = new ArrayList<>();
		players.add(new Player("0"));
		d_gameState = new GameState(players);
		d_gameState.getGameBoard().put("targetCountry", 1); // Adding one army to the target country for testing
		d_playerId = 0;
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
		d_blockadeOrder = new Blockade("invalidCountry", new GameEngineNew());
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

	/**
	 * Tests the changeGameState method to ensure one reinforcement is deducted
	 * after execution.
	 */
	@Test
	public void testChangeGameState() {
		List<Integer> reinforcements = Collections.singletonList(10);
		d_gameState.setReinforcements(reinforcements);
		d_blockadeOrder.changeGameState(d_gameState, d_playerId);
		assertEquals(9, d_gameState.getReinforcements().get(d_playerId).intValue()); // One reinforcement should be deducted
	}
}
