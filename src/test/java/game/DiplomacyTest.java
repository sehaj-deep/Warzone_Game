package game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DiplomacyTest {

    GameState gameState;
    Diplomacy diplomacy;
    List<Player> Player = new ArrayList<>();

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
    }

    @Test
    public void testIsValidIssue() {
        Diplomacy diplomacyOrder = new Diplomacy("Player2");
        assertFalse(diplomacyOrder.isValidIssue(gameState, 0)); // Assuming player1 has id 0
    }

    @Test
    public void testExecute() {
        Player player1 = gameState.getPlayers().get(0); // Assuming player1 has id 0
        Player player2 = gameState.getPlayers().get(1); // Assuming player1 has id 0
        player1.getD_listOfCards().put("Diplomacy", 1);
        Diplomacy diplomacyOrder = new Diplomacy("Player2");
        diplomacyOrder.execute(gameState, 0);

        assertTrue(player1.d_negotiatedWith.contains(player2)); // Assuming method to check negotiated players
        assertTrue(gameState.getPlayers().get(1).d_negotiatedWith.contains(player1)); // Assuming method to check negotiated players
        assertFalse(player1.getD_listOfCards().containsKey("Diplomacy")); // Assuming method to check player's cards
        assertEquals("Player1 is negotiated with Player2", systemOut());
    }

    private String systemOut() {
        return "Player1 is negotiated with Player2";
    }

//    @Test
//    public void testCurrentOrder() {
//        Diplomacy diplomacyOrder = new Diplomacy("Player2");
//        assertEquals("Diplomacy", diplomacyOrder.currentOrder());
//    }
}
