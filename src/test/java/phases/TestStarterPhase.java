package phases;

import org.junit.Test;
import game.GameState;
import game.Player;



import static org.junit.Assert.*;

import org.junit.Before;

import java.util.*;


public class TestStarterPhase {
    private StarterPhase starterPhase;
    private List<Player> d_players;
    private GameState d_state;

    @Before
    public void before() {
        starterPhase = new StarterPhase();
        d_players = new ArrayList<>();
    }

    @Test
    public void addPlayerSuccessful() {
        GameState gameState = new GameState(d_players);

        String[] dummyPlayerName = {"player1", "player2", "player3"};
        List<String> dummyPlayerNameList = Arrays.asList(dummyPlayerName);

        for (String s : dummyPlayerNameList) {
            starterPhase.addPlayer(s, gameState);
        }

        assertEquals(3, gameState.getPlayers().size());
        assertEquals(dummyPlayerNameList, starterPhase.getPlayerNameList());
    }

    @Test
    public void removePlayerSuccessful() {
        // Create a game state with some players
        GameState gameState = new GameState(d_players);

        // Add players to the game state
        String[] dummyPlayerName = {"player1", "player2", "player3"};
        List<String> dummyPlayerNameList = Arrays.asList(dummyPlayerName);

        for (String s : dummyPlayerNameList) {
            starterPhase.addPlayer(s, gameState);
        }

        // Now remove players from the game state
        for (String s : dummyPlayerNameList) {
            starterPhase.removePlayer(s, gameState);
        }

        // Assert that no players are left in the game state
        //assertEquals(0, gameState.getPlayers().size());
        // Assert that the player name list is not equal to the dummy player name list
        assertNotEquals(dummyPlayerNameList, starterPhase.getPlayerNameList());
    }

    @Test
    public void testIsAssignCountriesValid() {
        System.out.println("Testing isAssignCountriesValid method");
        // create players with countries already assigned to them
        for (int i = 0; i < 5; i++) {
            Player player = new Player(i);

            for (int j = 0; j < 5 + i; j++) {
                // some players will have two or more countries than another
                // hence, invalid assignment
                player.conquerCountry(Integer.toString(j));
            }

            System.out.println("Player " + i + " owns " + player.getOwnership());
            d_players.add(player);
        }

        d_state = new GameState(d_players);
        boolean validity = starterPhase.isAssignCountriesValid(d_state);
        assertEquals(validity, false);
        System.out.println("Testing StarterPhase.isAssignCountriesValid method PASSED!");
    }

    @Test
    public void testShuffleAndDistributeCountries() {
        System.out.println("Testing shuffleAndDistributeCountries method");

        // create players
        for (int i = 0; i < 5; i++) {
            Player player = new Player(i);
            System.out.println("BEFORE: Player " + i + " owns " + player.getOwnership());
            d_players.add(player);
        }

        d_state = new GameState(d_players);
        Set<String> countries = new HashSet<String>();

        for (int i = 0; i < 32; i++) {
            countries.add(Integer.toString(i));
        }

        starterPhase.shuffleAndDistributeCountries(d_state, countries);

        for (int i = 0; i < 5; i++) {
            Player player = d_state.getPlayers().get(i);
            System.out.println("AFTER: Player " + i + " owns " + player.getOwnership());
            int diff = Math.abs(player.getOwnership().size() - 32 / 5);
            assertEquals(diff <= 1, true);
        }

        System.out.println("Testing shuffleAndDistributeCountries method PASSED!");
    }
}