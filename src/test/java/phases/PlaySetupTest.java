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
    /**
     * Private field to store an instance of the {@link PlaySetup} class, which manages the setup phase of the game.
     */
    private PlaySetup d_playSetup;

    /**
     * Private field to store a list of {@link Player} objects representing the players in the game.
     */
    private List<Player> d_players;

    /**
     * Private field to store an instance of the {@link GameState} class, representing the state of the game.
     */
    private GameState d_state;

    /**
     * Private field to store an instance of the {@link GameEngine} class, which controls the game's logic and flow.
     */
    private GameEngine d_newGameEngine;

    /**
     * Sets up the test environment before each test case execution.
     * It initializes the {@link GameEngine}, {@link PlaySetup}, players list, and game state.
     */
    @Before
    public void before() {
        d_newGameEngine = new GameEngine();
        d_playSetup = new PlaySetup(d_newGameEngine);
        d_players = new ArrayList<>();
        d_state = new GameState();
    }

    /**
     * Tests the {@link PlaySetup#addPlayers(String)} method.
     * It verifies whether players are successfully added to the game.
     */
    @Test
    public void addPlayers() {
        System.out.println("BEFORE: players in the game: " + d_newGameEngine.getGameState().getPlayers());

        String[] dummyPlayerName = {"player1", "player2", "player3"};

        for (String s : dummyPlayerName) {
            d_playSetup.addPlayers(s);
        }

        System.out.println("AFTER: players in the game: " + d_newGameEngine.getGameState().getPlayers());
        assertEquals(3, d_newGameEngine.getGameState().getPlayers().size());
        assertEquals("player1", d_newGameEngine.getGameState().getPlayers().get(0).getPlayerName());
        assertEquals("player2", d_newGameEngine.getGameState().getPlayers().get(1).getPlayerName());
        assertEquals("player3", d_newGameEngine.getGameState().getPlayers().get(2).getPlayerName());
    }

    /**
     * Tests the {@link PlaySetup#removePlayers(String)} method.
     * It verifies whether players are successfully removed from the game.
     */
    @Test
    public void removePlayers() {

        Player player1 = new Player("player1");

        d_newGameEngine.getGameState().getPlayers().add(player1);

        assertEquals(1, d_newGameEngine.getGameState().getPlayers().size());
        assertTrue(d_newGameEngine.getGameState().getPlayers().contains(player1));

        d_playSetup.removePlayers("player1");

        assertEquals(0, d_newGameEngine.getGameState().getPlayers().size());
        assertFalse(d_newGameEngine.getGameState().getPlayers().contains(player1));
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
            Player l_player = new Player(Integer.toString(i));
            int l_addition = 0;
            if (i == 4) {
                l_addition = 2;
            }
            for (int j = 0; j < 5 + l_addition; j++) {
                // some players will have two or more countries than another
                // hence, invalid assignment
                l_player.conquerCountry(Integer.toString(j));
            }

            System.out.println("Player " + i + " owns " + l_player.getOwnership());
            d_players.add(l_player);
        }

        boolean l_validity = d_playSetup.isAssignCountriesValid();
        assertTrue(l_validity);
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
            Player l_player = new Player(Integer.toString(i));
            System.out.println("BEFORE: Player " + i + " owns " + l_player.getOwnership());
            d_players.add(l_player);
        }

        // Initialize GameState and countries
        d_state = new GameState();
        Set<String> l_countries = new HashSet<>();
        for (int i = 0; i < 32; i++) {
            l_countries.add(Integer.toString(i));
        }
        d_state.setPlayers(d_players);

        d_playSetup.assignCountries();

        // After assigning countries, verify the ownership distribution
        for (int i = 0; i < 5; i++) {
            Player l_player = d_state.getPlayers().get(i);
            System.out.println("AFTER: Player " + i + " owns " + l_player.getOwnership());
            int diff = Math.abs(l_player.getOwnership().size() - 32 / 5);
            assertFalse(diff <= 1);
        }

        System.out.println("Testing shuffleAndDistributeCountries method PASSED!");
    }
}