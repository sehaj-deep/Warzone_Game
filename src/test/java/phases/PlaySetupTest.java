package phases;

import game.GameEngine;
import game.GameState;
import game.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * This class contains unit tests for the {@link PlaySetup} class.
 * It tests methods such as adding players, removing players, checking the validity of assigned countries,
 * and assigning countries to players.
 */
public class PlaySetupTest {
    private PlaySetup playSetup;
    private List<Player> d_players;
    private GameState d_state;
    private Player player;
    GameEngine newGameEngine;
    Set<String> countries = new HashSet<>();

    /**
     * Sets up the test environment before each test case execution.
     * It initializes the {@link GameEngine}, {@link PlaySetup}, players list, and game state.
     */
    @Before
    public void before() {
        newGameEngine = new GameEngine();
        playSetup = new PlaySetup(newGameEngine);
        d_players = new ArrayList<>();
        d_state = new GameState();
    }

    /**
     * Tests the {@link PlaySetup#addPlayers(String)} method.
     * It verifies whether players are successfully added to the game.
     */
    @Test
    public void addPlayers() {
        System.out.println("BEFORE: players in the game: " + newGameEngine.getGameState().getPlayers());

        String[] dummyPlayerName = {"player1", "player2", "player3"};

        for (String s : dummyPlayerName) {
            playSetup.addPlayers(s);
        }

        System.out.println("AFTER: players in the game: " + newGameEngine.getGameState().getPlayers());
        assertEquals(3, newGameEngine.getGameState().getPlayers().size());
        assertEquals("player1", newGameEngine.getGameState().getPlayers().get(0).getPlayerName());
        assertEquals("player2", newGameEngine.getGameState().getPlayers().get(1).getPlayerName());
        assertEquals("player3", newGameEngine.getGameState().getPlayers().get(2).getPlayerName());
    }

    /**
     * Tests the {@link PlaySetup#removePlayers(String)} method.
     * It verifies whether players are successfully removed from the game.
     */
    @Test
    public void removePlayers() {

        Player player1 = new Player("player1");

        newGameEngine.getGameState().getPlayers().add(player1);

        assertEquals(1, newGameEngine.getGameState().getPlayers().size());
        assertTrue(newGameEngine.getGameState().getPlayers().contains(player1));

        playSetup.removePlayers("player1");

        assertEquals(0, newGameEngine.getGameState().getPlayers().size());
        assertFalse(newGameEngine.getGameState().getPlayers().contains(player1));
    }

    /**
     * Tests the {@link PlaySetup#isAssignCountriesValid()} method.
     * It checks the validity of assigned countries to players.
     */
    @Test
    public void isAssignCountriesValid() {
        System.out.println("Testing isAssignCountriesValid method");
        // create players with countries already assigned to them
        for (int i = 0; i < 5; i++) {
            Player player = new Player(Integer.toString(i));
            int addition = 0;
            if (i == 4) {
                addition = 2;
            }
            for (int j = 0; j < 5 + addition; j++) {
                // some players will have two or more countries than another
                // hence, invalid assignment
                player.conquerCountry(Integer.toString(j));
            }

            System.out.println("Player " + i + " owns " + player.getOwnership());
            d_players.add(player);
        }

        boolean validity = playSetup.isAssignCountriesValid();
        assertTrue(validity);
        System.out.println("Testing StarterPhase.isAssignCountriesValid method PASSED!");
    }

    /**
     * Tests the {@link PlaySetup#assignCountries()} method.
     * It verifies the distribution of countries among players.
     */
    @Test
    public void assignCountries() {
        System.out.println("Testing shuffleAndDistributeCountries method");

        // Create players
        for (int i = 0; i < 5; i++) {
            Player player = new Player(Integer.toString(i));
            System.out.println("BEFORE: Player " + i + " owns " + player.getOwnership());
            d_players.add(player);
        }

        // Initialize GameState and countries
        d_state = new GameState();
        Set<String> countries = new HashSet<>();
        for (int i = 0; i < 32; i++) {
            countries.add(Integer.toString(i));
        }
        d_state.setPlayers(d_players);

        playSetup.assignCountries();

        // After assigning countries, verify the ownership distribution
        for (int i = 0; i < 5; i++) {
            Player player = d_state.getPlayers().get(i);
            System.out.println("AFTER: Player " + i + " owns " + player.getOwnership());
            int diff = Math.abs(player.getOwnership().size() - 32 / 5);
            assertFalse(diff <= 1);
        }

        System.out.println("Testing shuffleAndDistributeCountries method PASSED!");
    }
}