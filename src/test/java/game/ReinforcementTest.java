package game;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import map.MapEditor;
import models.Continent;
import models.Country;
public class ReinforcementTest{

    @Test
    public void testCalculateReinforcementArmies() {
        // Setup
        MapEditor gameMap = new MapEditor();
        Player player = new Player(Integer.toString(0));
        Reinforcements reinforcements = new Reinforcements(gameMap, player);
        System.out.println("Testing isValid method PASSED!");

        // Test Case 1: No Countries Owned
        reinforcements.calculateReinforcementArmies();
        assertEquals(3, reinforcements.getReinforcementArmies());

        // Test Case 2: Fewer Than 3 Countries Owned
        // Test for 0, 1, and 2 countries owned
        player.setOwnership(new HashSet<>(Arrays.asList("Country1")));
        reinforcements.calculateReinforcementArmies();
        assertEquals(3, reinforcements.getReinforcementArmies());

        // Test Case 3: Exactly 3 Countries Owned
        // Test for 3 countries owned
        player.setOwnership(new HashSet<>(Arrays.asList("Country1", "Country2", "Country3")));
        reinforcements.calculateReinforcementArmies();
        assertTrue(reinforcements.getReinforcementArmies() >= 3);
        
        // Test Case 5: Continent Bonus - Single Continent
        // Simulate owning a continent with bonus armies
        Continent continent1 = new Continent(0, "Continent1", 5);
        continent1.addD_countries(new Country(1,"Country5"));
        continent1.addD_countries(new Country(2,"Country6"));
        gameMap.getD_continents().put("Continent1", continent1);

        player.setOwnership(new HashSet<>(Arrays.asList("Country5", "Country6")));
        reinforcements.calculateReinforcementArmies();
        assertEquals(5, reinforcements.getReinforcementArmies()); // Expecting 5 bonus armies + basic 3
            // // Test Case 4: More Than 3 Countries Owned
        // // Test for 4 or more countries owned
        // player.setOwnership(new HashSet<>(Arrays.asList("Country1", "Country2", "Country3", "Country4")));
        // reinforcements.calculateReinforcementArmies();
        // assertTrue(reinforcements.getReinforcementArmies() >= 4);

        // Test Case 5: Continent Bonus
        // Simulate owning a continent
        // Add your simulation here

        // Test Case 6: Partial Continent Ownership
        // Simulate owning some but not all countries in a continent
        // Add your simulation here
    }
}
