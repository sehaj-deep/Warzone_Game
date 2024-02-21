package map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import constants.GameConstants;

/**
 * To test MapEditor class
 */
public class MapEditorTest {

	private MapEditor d_mapEditor;
	private String d_fileName;

	/**
	 * set up the tests
	 */
	@Before
	public void setUp() {
		d_mapEditor = new MapEditor();
	}

	/**
	 * test a connected graph
	 */
	@Test
	public void testValidateMapConnectedGraph() {
		// Create a MapEditor instance with a disconnected graph map
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test.map";
		MapReader.readMap(d_mapEditor, d_fileName, false);
		assertTrue(d_mapEditor.validateMap());
	}

	/**
	 * test a disconnected graph
	 */
	@Test
	public void testValidateMapDisconnectedGraph() {
		// Create a MapEditor instance with a disconnected graph map
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_disconnected_graph.map";
		MapReader.readMap(d_mapEditor, d_fileName, false);
		assertFalse(d_mapEditor.validateMap());
	}

	/**
	 * test a disconnected empty subgraph
	 */
	@Test
	public void testValidateMapContinentWithNoCountries() {
		// Create a MapEditor instance with a continent having no countries
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_empty_continent.map";
		MapReader.readMap(d_mapEditor, d_fileName, false);
		assertFalse(d_mapEditor.validateMap());
	}

	/**
	 * test a disconnected subgraph
	 */
	@Test
	public void testValidateMapDisconnectedContinentGraph() {
		// Create a MapEditor instance with a continent containing a disconnected
		// subgraph
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_disconnected_subgraph.map";
		MapReader.readMap(d_mapEditor, d_fileName, false);
		assertTrue(d_mapEditor.validateMap());
	}

}
