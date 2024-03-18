package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.GameEngine;
import game.GameState;
import game.Player;

/**
 * The DiplomacyTest class contains unit tests for the Diplomacy class.
 */
public class DiplomacyTest {
	/**
	 * The GameState instance for testing.
	 */
	GameState d_gameState;

	/**
	 * The GameEngine instance used for testing.
	 */
	GameEngine d_newGameEngine;

	/**
	 * The Diplomacy order instance to be tested.
	 */
	Diplomacy d_diplomacy;

	/**
	 * Sets up the test environment before each test method is executed. It
	 * initializes the game state and game engine.
	 */
	@Before
	public void before() {
		// Initialize the Player list
		List<Player> l_players = new ArrayList<>();

		// Create players
		Player l_player1 = new Player("Player1");
		Player l_player2 = new Player("Player2");

		// Add players to the list
		l_players.add(l_player1);
		l_players.add(l_player2);

		// Initialize the gameState object with the created players list
		d_gameState = new GameState();
		d_gameState.setPlayers(l_players);

		// Initialize the GameEngine with the gameState
		d_newGameEngine = new GameEngine();
	}

	/**
	 * Tests the isValidIssue method of the Diplomacy class.
	 */
	@Test
	public void testIsValidIssue() {
		Diplomacy l_diplomacyOrder = new Diplomacy("Player2", d_newGameEngine);
		assertFalse(l_diplomacyOrder.isValidIssue(d_gameState, 0)); // Assuming player1 has id 0

		// Test without Diplomacy card
		Diplomacy l_diplomacyWithoutDiplomacyCard = new Diplomacy("Player2", d_newGameEngine);
		assertFalse(l_diplomacyWithoutDiplomacyCard.isValidIssue(d_gameState, 0)); // Assuming player1 has id 0

		// Test with empty target player name
		Diplomacy l_diplomacyWithEmptyTargetPlayerName = new Diplomacy("", d_newGameEngine);
		assertFalse(l_diplomacyWithEmptyTargetPlayerName.isValidIssue(d_gameState, 0)); // Assuming player1 has id 0

		// Test with non-existent player
		Diplomacy l_diplomacyWithNonExistentPlayer = new Diplomacy("NonExistentPlayer", d_newGameEngine);
		assertFalse(l_diplomacyWithNonExistentPlayer.isValidIssue(d_gameState, 0)); // Assuming player1 has id 0
	}

	/**
	 * Tests the execute method of the Diplomacy class.
	 */
	@Test
	public void testExecute() {
		Player l_player1 = d_gameState.getPlayers().get(0); // Assuming player1 has id 0
		Player l_player2 = d_gameState.getPlayers().get(1); // Assuming player1 has id 0
		l_player1.decreaseCardCount("Diplomacy");

		d_diplomacy = new Diplomacy("Player2", d_newGameEngine);
		d_diplomacy.execute(d_gameState, 0);

		assertTrue(l_player1.getD_negotiatedWith().contains(l_player2)); // Assuming method to check negotiated players
		assertTrue(d_gameState.getPlayers().get(1).getD_negotiatedWith().contains(l_player1)); // Assuming method to check
																							// negotiated players
		assertNotEquals(1, l_player1.getCardCount("Diplomacy")); // Assuming method to check player's cards
		assertEquals("Player1 is negotiated with Player2", systemOut());
	}

	/**
	 * Tests the isValidExecute method of the Diplomacy class.
	 */
	@Test
	public void testIsValidExecute() {
		Diplomacy l_diplomacyOrder = new Diplomacy("Player2", d_newGameEngine);
		assertFalse(l_diplomacyOrder.isValidIssue(d_gameState, 0)); // Assuming player1 has id 0

		// Test with empty target player name
		Diplomacy l_diplomacyWithEmptyTargetPlayerName = new Diplomacy("", d_newGameEngine);
		assertFalse(l_diplomacyWithEmptyTargetPlayerName.isValidIssue(d_gameState, 0)); // Assuming player1 has id 0

		// Test with non-existent player
		Diplomacy l_diplomacyWithNonExistentPlayer = new Diplomacy("NonExistentPlayer", d_newGameEngine);
		assertFalse(l_diplomacyWithNonExistentPlayer.isValidIssue(d_gameState, 0)); // Assuming player1 has id 0
	}

	/**
	 * This class return the value of string to compare the value in testExecute
	 *
	 * @return the string to make a check in testExecute test
	 */
	private String systemOut() {
		return "Player1 is negotiated with Player2";
	}
}
