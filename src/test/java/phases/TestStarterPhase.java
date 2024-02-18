package phases;

import org.testng.annotations.Test;
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

    @org.junit.Test
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

    @org.junit.Test
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