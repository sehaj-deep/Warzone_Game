package map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import constants.GameConstants;
import models.Country;

public class MapReaderTest {

	private MapEditor d_mapEditor;
	private String d_fileName;

	@Before
	public void setUp() {
		d_mapEditor = new MapEditor();
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test.map";
	}

	@Test
	public void testReadMap() {
		MapReader.readMap(d_mapEditor, d_fileName, false);

		// Assert continents
		assertEquals(2, d_mapEditor.getD_continents().size());
		assertTrue(d_mapEditor.getD_continents().containsKey("Continent1"));
		assertTrue(d_mapEditor.getD_continents().containsKey("Continent2"));

		// Assert countries
		assertEquals(3, d_mapEditor.getD_countries().size());
		assertTrue(d_mapEditor.getD_countries().containsKey("Country1"));
		assertTrue(d_mapEditor.getD_countries().containsKey("Country2"));
		assertTrue(d_mapEditor.getD_countries().containsKey("Country3"));

		// Test continent bonuses
		assertEquals(3, d_mapEditor.getD_continents().get("Continent1").getD_continentBonusArmies());
		assertEquals(4, d_mapEditor.getD_continents().get("Continent2").getD_continentBonusArmies());

		// Test country assignments to continents
		assertEquals(1, d_mapEditor.getD_countries().get("Country1").getD_id());
		assertEquals(2, d_mapEditor.getD_countries().get("Country2").getD_id());
		assertEquals(3, d_mapEditor.getD_countries().get("Country3").getD_id());

		// Test country neighbors
		Set<Country> neighborsCountry1 = d_mapEditor.getD_countries().get("Country1").getNeighbors();
		assertEquals(2, neighborsCountry1.size());
		assertTrue(neighborsCountry1.contains(d_mapEditor.getD_countries().get("Country2")));
		assertTrue(neighborsCountry1.contains(d_mapEditor.getD_countries().get("Country3")));

		Set<Country> neighborsCountry2 = d_mapEditor.getD_countries().get("Country2").getNeighbors();
		assertEquals(1, neighborsCountry2.size());
		assertTrue(neighborsCountry2.contains(d_mapEditor.getD_countries().get("Country1")));

		Set<Country> neighborsCountry3 = d_mapEditor.getD_countries().get("Country3").getNeighbors();
		assertEquals(1, neighborsCountry3.size());
		assertTrue(neighborsCountry3.contains(d_mapEditor.getD_countries().get("Country1")));

		// Test for non-existent file
		String nonExistentFile = "non_existent_map.txt";
		MapEditor emptyEditor = new MapEditor();
		MapReader.readMap(emptyEditor, nonExistentFile, false);
		assertEquals(0, emptyEditor.getD_continents().size());
		assertEquals(0, emptyEditor.getD_countries().size());
	}

	@Test
	public void testReadMapWithEmptyFile() {
		String emptyFile = "empty_map.txt";
		MapEditor emptyEditor = new MapEditor();
		MapReader.readMap(emptyEditor, emptyFile, false);
		assertEquals(0, emptyEditor.getD_continents().size());
		assertEquals(0, emptyEditor.getD_countries().size());
	}

	@Test
	public void testReadMapWithInvalidData() {
		String invalidFile = "invalid_map.txt";
		MapEditor invalidEditor = new MapEditor();
		MapReader.readMap(invalidEditor, invalidFile, false);
		// Assert that no continents or countries were added due to invalid data
		assertEquals(0, invalidEditor.getD_continents().size());
		assertEquals(0, invalidEditor.getD_countries().size());
	}

}
