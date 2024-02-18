package map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import constants.GameConstants;

public class MapEditorTest {

	private MapEditor d_mapEditor;
	private String d_fileName;

	@Before
	public void setUp() {
		d_mapEditor = new MapEditor();
	}

	@Test
	public void testValidateMapConnectedGraph() {
		// Create a MapEditor instance with a disconnected graph map
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test.map";
		MapReader.readMap(d_mapEditor, d_fileName, false);
		assertTrue(d_mapEditor.validateMap());
	}

	@Test
	public void testValidateMapDisconnectedGraph() {
		// Create a MapEditor instance with a disconnected graph map
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_disconnected_graph.map";
		MapReader.readMap(d_mapEditor, d_fileName, false);
		assertFalse(d_mapEditor.validateMap());
	}

	@Test
	public void testValidateMapContinentWithNoCountries() {
		// Create a MapEditor instance with a continent having no countries
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_empty_continent.map";
		MapReader.readMap(d_mapEditor, d_fileName, false);
		assertFalse(d_mapEditor.validateMap());
	}

	@Test
	public void testValidateMapDisconnectedContinentGraph() {
		// Create a MapEditor instance with a continent containing a disconnected
		// subgraph
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test_disconnected_subgraph.map";
		MapReader.readMap(d_mapEditor, d_fileName, false);
		assertFalse(d_mapEditor.validateMap());
	}

}
