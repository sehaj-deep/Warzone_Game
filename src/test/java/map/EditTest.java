package map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import constants.GameConstants;
import game.GameEngineNew;

public class EditTest {

	private GameEngineNew d_gameEngine;
	private Preload d_preload;
	private String d_fileName;

	@Before
	public void setup() {
		d_gameEngine = new GameEngineNew();
		d_preload = new Preload(d_gameEngine);
		d_gameEngine.setPhase(d_preload);
	}

	/**
	 * test a connected graph
	 */
	@Test
	public void testValidateMapConnectedGraph() {
		// Create a MapEditor instance with a disconnected graph map
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test.map";
		d_preload.readMap(d_fileName, false);
		assertTrue(d_preload.validateMap());
	}

	/**
	 * test a disconnected graph
	 */
	@Test
	public void testValidateMapDisconnectedGraph() {
		// Create a MapEditor instance with a disconnected graph map
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_disconnected_graph.map";
		d_preload.readMap(d_fileName, false);
		assertFalse(d_preload.validateMap());
	}

	/**
	 * test a disconnected empty subgraph
	 */
	@Test
	public void testValidateMapContinentWithNoCountries() {
		// Create a MapEditor instance with a continent having no countries
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_empty_continent.map";
		d_preload.readMap(d_fileName, false);
		assertFalse(d_preload.validateMap());
	}

	/**
	 * test a disconnected subgraph
	 */
	@Test
	public void testValidateMapDisconnectedContinentGraph() {
		// Create a MapEditor instance with a continent containing a
		// disconnectedsubgraph
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_disconnected_subgraph.map";
		d_preload.readMap(d_fileName, false);
		assertFalse(d_preload.validateMap());
	}
}
