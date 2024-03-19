package phases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import game.GameEngine;
import game.GameState;
import game.Player;
import models.Continent;
import models.Country;

/**
 * Reinforcement Test class
 */
public class ReinforcePhaseTest {
	/**
	 * Game Engine Instance
	 */
	GameEngine d_gameEngine;
	/**
	 * Reinforcement Instance
	 */
	ReinforcePhase d_reinforcePhase;
	/**
	 * The instance of Game State
	 */
	GameState d_gameState;
	/**
	 * The instance of Player
	 */
	Player d_player;

	/**
	 * Setup Method of the Reinforcement Test
	 */
	@Before
	public void setup() {
		d_gameEngine = new GameEngine();
		d_reinforcePhase = new ReinforcePhase(d_gameEngine);
		d_gameState = d_gameEngine.getGameState();
		// Add a player to the game state
		d_player = new Player("0");
		d_gameState.getPlayers().add(d_player);
	}

	/**
	 * Method to check Reinforcements for no country
	 */
	@Test
	public void testCalculateReinforcementArmiesWithNoCountry() {

		// Test Case 1: No Countries Owned
		d_reinforcePhase.calculateReinforcements(); // Calculate reinforcements first
		assertEquals(3, d_gameState.getReinforcements().get(0).intValue());
	}

	/**
	 * Method to check Reinforcements for less than 3 countries
	 */
	@Test
	public void testCalculateReinforcementArmiesLessCountries() {
		// Test for 0, 1, and 2 countries owned
		d_gameEngine.getD_countries("Country1");
		d_player.setOwnership(new HashSet<>(Arrays.asList("Country1")));
		d_reinforcePhase.calculateReinforcements(); // Calculate reinforcements first
		assertEquals(3, d_gameState.getReinforcements().get(0).intValue());
	}

	/**
	 * Method to check Reinforcements for 3 countries
	 */
	@Test
	public void testCalculateReinforcementArmiesThreeCountries() {
		d_reinforcePhase.calculateReinforcements(); // Calculate reinforcements first
		assertTrue(d_gameState.getReinforcements().get(0) >= 3);
	}

	/**
	 * Method to check Reinforcements when a player has a continent
	 */
	@Test
	public void testCalculateReinforcementArmiesContinentOwned() {
		// Simulate owning a continent with bonus armies
		Continent continent1 = new Continent(0, "Continent1", 5);
		Country l_country1 = new Country(1, "Country5");
		Country l_country2 = new Country(2, "Country6");
		d_gameEngine.getD_continents().put("Continent1", continent1);

		continent1.getD_countries().add(l_country1);
		continent1.getD_countries().add(l_country2);

		d_player.getOwnership().add("Country5");
		d_player.getOwnership().add("Country6");
		d_reinforcePhase.calculateReinforcements(); // Calculate reinforcements first
		assertEquals(5, d_gameState.getReinforcements().get(0).intValue());
	}

}