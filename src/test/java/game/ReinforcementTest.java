package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import map.MapEditor;
import models.Continent;
import models.Country;

/**
 * Test Reinforcement Armies
 */
public class ReinforcementTest {

	/**
	 * Test method to verify calculation of reinforcement armies
	 */
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

		// Test for 0, 1, and 2 countries owned
		player.setOwnership(new HashSet<>(Arrays.asList("Country1")));
		reinforcements.calculateReinforcementArmies();
		assertEquals(3, reinforcements.getReinforcementArmies());

		// Test for 3 countries owned
		player.setOwnership(new HashSet<>(Arrays.asList("Country1", "Country2", "Country3")));
		reinforcements.calculateReinforcementArmies();
		assertTrue(reinforcements.getReinforcementArmies() >= 3);

		// Simulate owning a continent with bonus armies
		Continent continent1 = new Continent(0, "Continent1", 5);
		continent1.addD_countries(new Country(1, "Country5"));
		continent1.addD_countries(new Country(2, "Country6"));
		gameMap.getD_continents().put("Continent1", continent1);

		player.setOwnership(new HashSet<>(Arrays.asList("Country5", "Country6")));
		reinforcements.calculateReinforcementArmies();
		assertEquals(3, reinforcements.getReinforcementArmies());
	}
}
