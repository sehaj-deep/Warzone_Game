package map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import constants.GameConstants;
import game.GameEngine;
import models.Country;

/**
 * This class contains unit tests for the Preload class, which is responsible for reading and initializing game map data from map files.
 */
public class PreloadTest {

	/**
     * The game engine used for testing.
     */
	private GameEngine d_gameEngine;

	/**
     * The Preload object under test.
     */
	private Preload d_preload;

	/**
     * The name of the file to be used for testing.
     */
	private String d_fileName;

	/**
     * Setup method to initialize objects before each test case.
     */
	@Before
	public void setup() {
		d_gameEngine = new GameEngine();
		d_preload = new Preload(d_gameEngine);
		d_gameEngine.setPhase(d_preload);
	}

	/**
	 * To test read file method for valid file
	 */
	@Test
	public void testReadMap() {
//		MapReader.readMap(d_mapEditor, d_fileName, false);
		d_fileName = GameConstants.SRC_TEST_RESOURCES + "test.map";
		d_preload.editMap(d_fileName);

		// Assert continents
		assertEquals(2, d_gameEngine.getD_continents().size());
		assertTrue(d_gameEngine.getD_continents().containsKey("Continent1"));
		assertTrue(d_gameEngine.getD_continents().containsKey("Continent2"));

		// Assert countries
		assertEquals(3, d_gameEngine.getD_countries().size());
		assertTrue(d_gameEngine.getD_countries().containsKey("Country1"));
		assertTrue(d_gameEngine.getD_countries().containsKey("Country2"));
		assertTrue(d_gameEngine.getD_countries().containsKey("Country3"));

		// Test continent bonuses
		assertEquals(3, d_gameEngine.getD_continents().get("Continent1").getD_continentBonusArmies());
		assertEquals(4, d_gameEngine.getD_continents().get("Continent2").getD_continentBonusArmies());

		// Test country assignments to continents
		assertEquals(1, d_gameEngine.getD_countries().get("Country1").getD_id());
		assertEquals(2, d_gameEngine.getD_countries().get("Country2").getD_id());
		assertEquals(3, d_gameEngine.getD_countries().get("Country3").getD_id());

		// Test country neighbors
		Set<Country> neighborsCountry1 = d_gameEngine.getD_countries().get("Country1").getNeighbors();
		assertEquals(2, neighborsCountry1.size());
		assertTrue(neighborsCountry1.contains(d_gameEngine.getD_countries().get("Country2")));
		assertTrue(neighborsCountry1.contains(d_gameEngine.getD_countries().get("Country3")));

		Set<Country> neighborsCountry2 = d_gameEngine.getD_countries().get("Country2").getNeighbors();
		assertEquals(1, neighborsCountry2.size());
		assertTrue(neighborsCountry2.contains(d_gameEngine.getD_countries().get("Country1")));

		Set<Country> neighborsCountry3 = d_gameEngine.getD_countries().get("Country3").getNeighbors();
		assertEquals(1, neighborsCountry3.size());
		assertTrue(neighborsCountry3.contains(d_gameEngine.getD_countries().get("Country1")));

	}

	/**
	 * To test read file method for empty file
	 */
	@Test
	public void testReadMapWithEmptyFile() {
		String d_fileName = "empty_map.txt";
		d_preload.editMap(d_fileName);
		assertEquals(0, d_gameEngine.getD_continents().size());
		assertEquals(0, d_gameEngine.getD_countries().size());
	}

	/**
	 * To test read file method for invalid file
	 */
	@Test
	public void testReadMapWithInvalidData() {
		String d_fileName = "invalid_map.txt";
		d_preload.editMap(d_fileName);

		// Assert that no continents or countries were added due to invalid data
		assertEquals(0, d_gameEngine.getD_continents().size());
		assertEquals(0, d_gameEngine.getD_countries().size());
	}

}
