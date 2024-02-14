package game;

import java.util.*;

/* GameEngine Class
 * This Class runs a game by integrating all the functions and classes needed for Warzone
 */
public class GameEngine {
    private List<Player> d_players;  // list of players playing the game
    private HashMap<String, Integer> d_board;  // key: country name. val: num army in the country
    //private GameMap d_gmap;

    /* Unparameterized Constructor
     *
     */
    GameEngine() {
        d_players = new ArrayList<>();
        d_board = new HashMap<String, Integer>();
    }
}
