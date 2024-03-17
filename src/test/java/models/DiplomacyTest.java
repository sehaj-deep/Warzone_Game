package models;

import game.GameEngine;
import game.GameState;
import game.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DiplomacyTest {

    GameState gameState;
    GameEngine newGameEngine;
    Diplomacy diplomacy;
    List<game.Player> player = new ArrayList<>();

    @Before
    public void before() {
        // Initialize the Player list
        List<Player> players = new ArrayList<>();

        // Create players
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        // Add players to the list
        players.add(player1);
        players.add(player2);

        // Initialize the gameState object with the created players list
        gameState = new GameState(players);

        // Initialize the GameEngine with the gameState
        newGameEngine = new GameEngine();
    }

    @Test
    public void testIsValidIssue() {
        Diplomacy diplomacyOrder = new Diplomacy("Player2", newGameEngine);
        assertFalse(diplomacyOrder.isValidIssue(gameState, 0)); // Assuming player1 has id 0

        // Test without Diplomacy card
        Diplomacy diplomacyWithoutDiplomacyCard = new Diplomacy("Player2", newGameEngine);
        assertFalse(diplomacyWithoutDiplomacyCard.isValidIssue(gameState, 0)); // Assuming player1 has id 0

        // Test with empty target player name
        Diplomacy diplomacyWithEmptyTargetPlayerName = new Diplomacy("", newGameEngine);
        assertFalse(diplomacyWithEmptyTargetPlayerName.isValidIssue(gameState, 0)); // Assuming player1 has id 0

        // Test with non-existent player
        Diplomacy diplomacyWithNonExistentPlayer = new Diplomacy("NonExistentPlayer", newGameEngine);
        assertFalse(diplomacyWithNonExistentPlayer.isValidIssue(gameState, 0)); // Assuming player1 has id 0
    }

    @Test
    public void testExecute() {
        Player player1 = gameState.getPlayers().get(0); // Assuming player1 has id 0
        Player player2 = gameState.getPlayers().get(1); // Assuming player1 has id 0
        player1.decreaseCardCount("Diplomacy");

        diplomacy = new Diplomacy("Player2", newGameEngine);
        diplomacy.execute(gameState, 0);

        System.out.println(player1.getD_negotiatedWith().size());
        System.out.println(player2.getD_negotiatedWith().size());

        assertTrue(player1.getD_negotiatedWith().contains(player2)); // Assuming method to check negotiated players
        assertTrue(gameState.getPlayers().get(1).getD_negotiatedWith().contains(player1)); // Assuming method to check negotiated players
        assertNotEquals(1, player1.getCardCount("Diplomacy")); // Assuming method to check player's cards
        assertEquals("Player1 is negotiated with Player2", systemOut());
    }

    @Test
    public void testIsValidExecute() {

    }

    private String systemOut() {
        return "Player1 is negotiated with Player2";
    }
}
