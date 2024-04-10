package phases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import constants.GameConstants;
import game.GameEngine;

/**
 * Unit tests for various phases in the game.
 */
public class PhaseTest {

	/**
	 * The instance of GameEngine used for testing.
	 */
	private GameEngine d_gameEngine;

	/**
	 * The instance of Preload phase used for testing.
	 */
	private Preload d_preload;

	/**
	 * The filename of the map being tested.
	 */
	private String d_fileName;

	/**
	 * Sets up the test environment before each test method execution.
	 */
	@Before
	public void setup() {
		d_gameEngine = new GameEngine();
		d_preload = new Preload(d_gameEngine);
		d_gameEngine.setPhase(d_preload);
	}

	/**
	 * test a connected graph
	 */
	@Test
	public void testValidateMapConnectedGraph() {
		// Create a GameEngine instance with a disconnected graph map
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test.map";
		d_preload.readMap(d_fileName, false);
		assertTrue(d_preload.validateMap());
	}

	/**
	 * test a disconnected graph
	 */
	@Test
	public void testValidateMapDisconnectedGraph() {
		// Create a GameEngine instance with a disconnected graph map
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_disconnected_graph.map";
		d_preload.readMap(d_fileName, false);
		assertFalse(d_preload.validateMap());
	}

	/**
	 * test a disconnected empty subgraph
	 */
	@Test
	public void testValidateMapContinentWithNoCountries() {
		// Create a GameEngine instance with a continent having no countries
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_empty_continent.map";
		d_preload.readMap(d_fileName, false);
		assertFalse(d_preload.validateMap());
	}

	/**
	 * test a disconnected subgraph
	 */
	@Test
	public void testValidateMapDisconnectedContinentGraph() {
		// Create a GameEngine instance with a continent containing a
		// disconnectedsubgraph
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_disconnected_subgraph.map";
		d_preload.readMap(d_fileName, false);
		assertFalse(d_preload.validateMap());
	}

}
