package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import models.Continent;
import models.Country;
import phases.ReinforcePhase;

public class ReinforcementTest {

    /**
     * Test method to verify calculation of reinforcement armies
     */
    @Test
    public void testCalculateReinforcementArmies() {
        // Setup
        GameEngine gameEngine = new GameEngine();
        ReinforcePhase reinforcePhase = new ReinforcePhase(gameEngine);
        GameState gameState = gameEngine.getGameState();

        // Add a player to the game state
        Player player = new Player("0");
        gameState.getPlayers().add(player);

        //Test Case 1: No Countries Owned
        reinforcePhase.calculateReinforcements(); // Calculate reinforcements first
        assertEquals(0, gameState.getReinforcements().get(0).intValue());

        // Test for 0, 1, and 2 countries owned
        // player.setOwnership(new HashSet<>(Arrays.asList("Country1")));
        // reinforcePhase.calculateReinforcements(); // Calculate reinforcements first
        // assertEquals(3, gameState.getReinforcements().get(0).intValue());

        // Test for 3 countries owned
        // player.setOwnership(new HashSet<>(Arrays.asList("Country1", "Country2", "Country3")));
        // reinforcePhase.calculateReinforcements(); // Calculate reinforcements first
        // assertTrue(gameState.getReinforcements().get(0) >= 3);

        // Simulate owning a continent with bonus armies
        // Continent continent1 = new Continent(0, "Continent1", 5);
        // continent1.addD_countries(new Country(1, "Country5"));
        // continent1.addD_countries(new Country(2, "Country6"));
        // gameEngine.getD_continents().put("Continent1", continent1);

        // player.setOwnership(new HashSet<>(Arrays.asList("Country5", "Country6")));
        // reinforcePhase.calculateReinforcements(); // Calculate reinforcements first
        // assertEquals(3, gameState.getReinforcements().get(0).intValue());
    }
}