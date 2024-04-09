package players;

import static org.junit.Assert.*;
import org.junit.Test;
import game.GameEngine;
import map.Country;
import players.Player;
import orders.*;

/**
 * The RandomPlayerTest class contains unit tests for the RandomPlayer class.
 * It tests various methods and functionalities of the RandomPlayer class.
 */
public class RandomPlayerTest {

    /**
     * Test case for the deployRandomly method of RandomPlayer class.
     * It tests whether a deploy order is generated correctly when a player owns a country.
     */
    @Test
    public void testDeployRandomly() {
        // Test setup
        GameEngine gameEngine = new GameEngine();
        RandomPlayer player = new RandomPlayer();
        Player testPlayer = new Player("TestPlayer");
        gameEngine.getPlayers().add(testPlayer);
        Country country = new Country("CountryName");
        testPlayer.getOwnership().add("CountryName");
        gameEngine.getD_countries().put("CountryName", country);

        // Execute the method under test
        Order deployOrder = player.deployRandomly(testPlayer, gameEngine);

        // Assertion
        assertNull(deployOrder); // Ensure no deploy order is generated when no valid deploy location exists
        assertFalse(deployOrder instanceof Deploy); // Ensure the order is not of type Deploy
    }

    /**
     * Test case for the attackRandomly method of RandomPlayer class.
     * It tests whether an attack order is generated correctly when there are no valid targets.
     */
    @Test
    public void testAttackRandomly() {
        // Test setup
        GameEngine gameEngine = new GameEngine();
        RandomPlayer player = new RandomPlayer();
        Player testPlayer = new Player("TestPlayer");
        gameEngine.getPlayers().add(testPlayer);
        testPlayer.getOwnership().add("Country1");
        testPlayer.getOwnership().add("Country2");
        Player otherPlayer = new Player("OtherPlayer");
        gameEngine.getPlayers().add(otherPlayer);
        Country neighborCountry = new Country("NeighborCountry");
        gameEngine.getD_countries().put("NeighborCountry", neighborCountry);
        Country country1 = new Country("Country1");
        Country country2 = new Country("Country2");
        gameEngine.getD_countries().put("Country1", country1);
        gameEngine.getD_countries().put("Country2", country2);
        country1.getNeighbors().add(neighborCountry);

        // Execute the method under test
        Order attackOrder = player.attackRandomly(testPlayer, gameEngine);

        // Assertion
        assertNull(attackOrder); // Ensure no attack order is generated when there are no valid targets
    }

    /**
     * Test case for the getRandomCountry method of RandomPlayer class.
     * It tests whether a country owned by the player is returned correctly.
     */
    @Test
    public void testGetRandomCountry() {
        // Test setup
        GameEngine gameEngine = new GameEngine();
        RandomPlayer player = new RandomPlayer();
        Player testPlayer = new Player("TestPlayer");
        gameEngine.getPlayers().add(testPlayer);
        testPlayer.getOwnership().add("testCountry");

        // Execute the method under test
        String randomCountry = player.getRandomCountry(testPlayer, gameEngine);

        // Assertion
        assertEquals("testCountry", randomCountry);
    }

    /**
     * Test case for the getRandomCountryOwnedByOtherPlayer method of RandomPlayer class.
     * It tests whether a country owned by another player is returned correctly.
     */
    @Test
    public void testGetRandomCountryOwnedByOtherPlayer() {
        // Test setup
        GameEngine gameEngine = new GameEngine();
        RandomPlayer player = new RandomPlayer();
        Player testPlayer = new Player("TestPlayer");
        Player otherPlayer = new Player("OtherPlayer");
        gameEngine.getPlayers().add(otherPlayer);
        otherPlayer.getOwnership().add("country");

        // Execute the method under test
        String randomCountry = player.getRandomCountryOwnedByOtherPlayer(testPlayer, gameEngine);

        // Assertion
        assertEquals("country", randomCountry);
    }

    /**
     * Test case for the getRandomPlayerId method of RandomPlayer class.
     * It tests whether the ID of a random player is returned correctly.
     */
    @Test
    public void testGetRandomPlayerId() {
        // Test setup
        GameEngine gameEngine = new GameEngine();
        RandomPlayer player = new RandomPlayer();
        Player testPlayer = new Player("TestPlayer");
        Player otherPlayer = new Player("OtherPlayer");
        gameEngine.getPlayers().add(otherPlayer);

        // Execute the method under test
        String randomPlayerId = player.getRandomPlayerId(testPlayer, gameEngine);

        // Assertion
        assertEquals("OtherPlayer", randomPlayerId);
    }

    /**
     * Test case for the isD_canDeploy method of RandomPlayer class.
     * It tests whether the deployment permission flag is initially true.
     */
    @Test
    public void testIsD_canDeploy() {
        // Test setup
        RandomPlayer player = new RandomPlayer();

        // Assertion
        assertTrue(player.isD_canDeploy());
    }

    /**
     * Test case for the setD_canDeploy method of RandomPlayer class.
     * It tests whether the deployment permission flag is correctly set.
     */
    @Test
    public void testSetD_canDeploy() {
        // Test setup
        RandomPlayer player = new RandomPlayer();

        // Execute the method under test
        player.setD_canDeploy(false);

        // Assertion
        assertFalse(player.isD_canDeploy());
    }

    /**
     * Test case for the isD_canAttack method of RandomPlayer class.
     * It tests whether the attack permission flag is initially true.
     */
    @Test
    public void testIsD_canAttack() {
        // Test setup
        RandomPlayer player = new RandomPlayer();

        // Assertion
        assertTrue(player.isD_canAttack());
    }

    /**
     * Test case for the setD_canAttack method of RandomPlayer class.
     * It tests whether the attack permission flag is correctly set.
     */
    @Test
    public void testSetD_canAttack() {
        // Test setup
        RandomPlayer player = new RandomPlayer();

        // Execute the method under test
        player.setD_canAttack(false);

        // Assertion
        assertFalse(player.isD_canAttack());
    }

    /**
     * Test case for the reset method of RandomPlayer class.
     * It tests whether the deployment and attack permission flags are reset to true.
     */
    @Test
    public void testReset() {
        // Test setup
        RandomPlayer player = new RandomPlayer();
        player.setD_canDeploy(false);
        player.setD_canAttack(false);

        // Execute the method under test
        player.reset();

        // Assertion
        assertTrue(player.isD_canDeploy());
        assertTrue(player.isD_canAttack());
    }
}
