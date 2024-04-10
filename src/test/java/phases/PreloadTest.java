package phases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import constants.GameConstants;
import game.GameEngine;
import map.Country;

/**
 * This class contains unit tests for the Preload class, which is responsible
 * for reading and initializing game map data from map files.
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
     * Test for reading a valid map file.
     */
    @Test
    public void testReadValidMap() {
        d_fileName = GameConstants.SRC_TEST_RESOURCES + "test.map";
        d_preload.editMap(d_fileName);
        
        assertContinentsPresence();
        assertCountriesPresence();
        testContinentBonuses();
        testCountryAssignments();
        testCountryNeighbors();
    }

    /**
     * Test for reading an empty map file.
     */
    @Test
    public void testReadMapWithEmptyFile() {
        String d_fileName = "empty_map.txt";
        d_preload.editMap(d_fileName);
        assertEquals(0, d_gameEngine.getD_continents().size());
        assertEquals(0, d_gameEngine.getD_countries().size());
    }

    /**
     * Test for reading a map file with invalid data.
     */
    @Test
    public void testReadMapWithInvalidData() {
        String d_fileName = "invalid_map.txt";
        d_preload.editMap(d_fileName);
        assertEquals(0, d_gameEngine.getD_continents().size());
        assertEquals(0, d_gameEngine.getD_countries().size());
    }

    /**
     * Asserts the presence of continents in the game engine.
     */
    public void assertContinentsPresence() {
        assertEquals(2, d_gameEngine.getD_continents().size());
        assertTrue(d_gameEngine.getD_continents().containsKey("Continent1"));
        assertTrue(d_gameEngine.getD_continents().containsKey("Continent2"));
    }

    /**
     * Asserts the presence of countries in the game engine.
     */
    public void assertCountriesPresence() {
        assertEquals(3, d_gameEngine.getD_countries().size());
        assertTrue(d_gameEngine.getD_countries().containsKey("Country1"));
        assertTrue(d_gameEngine.getD_countries().containsKey("Country2"));
        assertTrue(d_gameEngine.getD_countries().containsKey("Country3"));
    }

    /**
     * Tests the continent bonuses after reading the map file.
     */
    public void testContinentBonuses() {
        assertEquals(3, d_gameEngine.getD_continents().get("Continent1").getD_continentBonusArmies());
        assertEquals(4, d_gameEngine.getD_continents().get("Continent2").getD_continentBonusArmies());
    }

    /**
     * Tests the country assignments to continents after reading the map file.
     */
    public void testCountryAssignments() {
        assertEquals(1, d_gameEngine.getD_countries().get("Country1").getD_id());
        assertEquals(2, d_gameEngine.getD_countries().get("Country2").getD_id());
        assertEquals(3, d_gameEngine.getD_countries().get("Country3").getD_id());
    }

    /**
     * Tests the neighbor countries after reading the map file.
     */
    public void testCountryNeighbors() {
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
}
