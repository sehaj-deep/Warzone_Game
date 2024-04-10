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
        RandomPlayerStrategy player = new RandomPlayerStrategy();
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
    public void testAttackRandomly_WithValidTargets() {
        // Test setup with valid targets for attack
        GameEngine gameEngine = new GameEngine();
        RandomPlayerStrategy player = new RandomPlayerStrategy();
        Player testPlayer = new Player("TestPlayer");
        gameEngine.getPlayers().add(testPlayer);
        // Add owned country
        testPlayer.getOwnership().add("AttackingCountry");
        // Add target country owned by another player
        Player otherPlayer = new Player("OtherPlayer");
        gameEngine.getPlayers().add(otherPlayer);
        Country targetCountry = new Country("TargetCountry");
        gameEngine.getD_countries().put("TargetCountry", targetCountry);
        // Add attacking country and set it to neighbor target country
        Country attackingCountry = new Country("AttackingCountry");
        gameEngine.getD_countries().put("AttackingCountry", attackingCountry);
        attackingCountry.getNeighbors().add(targetCountry);

        // Execute the method under test
        Order attackOrder = player.attackRandomly(testPlayer, gameEngine);

        // Assertion
        assertNotNull(attackOrder); // Ensure an attack order is generated when there are valid targets
        assertTrue(attackOrder instanceof Advance); // Ensure the generated order is an Advance order
        // Additional assertions can be added to check the correctness of the order parameters
    }


    /**
     * Test case for the getRandomCountry method of RandomPlayer class.
     * It tests whether a country owned by the player is returned correctly.
     */
    @Test
    public void testGetRandomCountry() {
        // Test setup
        GameEngine gameEngine = new GameEngine();
        RandomPlayerStrategy player = new RandomPlayerStrategy();
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
        RandomPlayerStrategy player = new RandomPlayerStrategy();
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
        RandomPlayerStrategy player = new RandomPlayerStrategy();
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
        RandomPlayerStrategy player = new RandomPlayerStrategy();

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
        RandomPlayerStrategy player = new RandomPlayerStrategy();

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
        RandomPlayerStrategy player = new RandomPlayerStrategy();

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
        RandomPlayerStrategy player = new RandomPlayerStrategy();

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
        RandomPlayerStrategy player = new RandomPlayerStrategy();
        player.setD_canDeploy(false);
        player.setD_canAttack(false);

        // Execute the method under test
        player.reset();

        // Assertion
        assertTrue(player.isD_canDeploy());
        assertTrue(player.isD_canAttack());
    }
}
