//package phases;
//
//import game.GameEngine;
//import game.GameState;
//import game.Player;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.*;
//
//import static org.junit.Assert.*;
//
//public class PlaySetupTest {
//    private PlaySetup playSetup;
//    private List<Player> d_players;
//    private GameState d_state;
//    private Player player;
//    GameEngine newGameEngine;
//
//    /**
//     * To set up tests
//     */
//    @Before
//    public void before() {
//        newGameEngine = new GameEngine();
//        playSetup = new PlaySetup(newGameEngine);
//        d_players = new ArrayList<>();
//
//
//    }
//
//    @Test
//    public void addPlayers() {
//        System.out.println("BEFORE: players in the game: " + newGameEngine.getD_players());
//
//        String[] dummyPlayerName = {"player1", "player2", "player3"};
//
//        for (String s : dummyPlayerName) {
//            playSetup.addPlayers(s);
//        }
//
//        System.out.println("AFTER: players in the game: " + newGameEngine.getD_players());
//        assertEquals(3, newGameEngine.getD_players().size());
//        assertEquals("player1", newGameEngine.getD_players().get(0).getPlayerName());
//        assertEquals("player2", newGameEngine.getD_players().get(1).getPlayerName());
//        assertEquals("player3", newGameEngine.getD_players().get(2).getPlayerName());
//    }
//
//    @Test
//    public void removePlayers() {
//
//        Player player1 = new Player("player1");
//
//        newGameEngine.getD_players().add(player1);
//
//        assertEquals(1, newGameEngine.getD_players().size());
//        assertTrue(newGameEngine.getD_players().contains(player1));
//
//        playSetup.removePlayers("player1");
//
//        assertEquals(0, newGameEngine.getD_players().size());
//        assertFalse(newGameEngine.getD_players().contains(player1));
//    }
//
//    @Test
//    public void isAssignCountriesValid() {
//        System.out.println("Testing isAssignCountriesValid method");
//        // create players with countries already assigned to them
//        for (int i = 0; i < 5; i++) {
//            Player player = new Player(Integer.toString(i));
//            int addition = 0;
//            if (i >= 4) {
//                addition = 2;
//            }
//            for (int j = 0; j < 5 + addition; j++) {
//                // some players will have two or more countries than another
//                // hence, invalid assignment
//                player.conquerCountry(Integer.toString(j));
//            }
//
//            System.out.println("Player " + i + " owns " + player.getOwnership());
//            d_players.add(player);
//        }
//
//        d_state = new GameState(d_players);
//        boolean validity = playSetup.isAssignCountriesValid(d_state);
//        assertFalse(validity);
//        System.out.println("Testing StarterPhase.isAssignCountriesValid method PASSED!");
//    }
//
//    @Test
//    public void assignCountries() {
//        System.out.println("Testing shuffleAndDistributeCountries method");
//
//        // create players
//        for (int i = 0; i < 5; i++) {
//            Player player = new Player(Integer.toString(i));
//            System.out.println("BEFORE: Player " + i + " owns " + player.getOwnership());
//            d_players.add(player);
//        }
//
//        d_state = new GameState(d_players);
//        Set<String> countries = new HashSet<>();
//
//        for (int i = 0; i < 32; i++) {
//            countries.add(Integer.toString(i));
//        }
//
//        for (int i = 0; i < 5; i++) {
//            Player player = d_state.getPlayers().get(i);
//            System.out.println("AFTER: Player " + i + " owns " + player.getOwnership());
//            int diff = Math.abs(player.getOwnership().size() - 32 / 5);
//            assertTrue(diff > 1);
//        }
//
//        System.out.println("Testing shuffleAndDistributeCountries method PASSED!");
//    }
//}