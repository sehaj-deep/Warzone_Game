package game;

import java.util.*;
import map.MapEditor;

/** GameState class
 * This class captures the current state of the game
 * and store information needed for game playing
 */
public class GameState {
    private List<Player> d_players;  // list of all players in the game

    // list of available reinforcements for all players. Order same as d_players
    private List<Integer> d_reinforcements;
    private MapEditor d_gMap;  // OOP representation of map

    /** Parameterized Constructor
     *
     * @param p_players is a list of players playing the game
     */
    GameState(List<Player> p_players) {
        d_players = p_players;
    }

    /** getter function for a list of players playing the game
     *
     * @return d_players a list of players playing the game
     */
    public List<Player> getPlayers() {
        return d_players;
    }

    /** getter function to get the list of available reinforcements for all players
     *
     * @return d_reinforcements a list of available reinforcements for all players
     */
    public List<Integer> getReinforcements() {
        return d_reinforcements;
    }

    /** setter function to set d_reinforcements to the provided list
     * may handy for unit testing
     *
     * @param p_reinforcements a new list of available reinforcements for all players
     */
    public void setReinforcements(List<Integer> p_reinforcements) {
        d_reinforcements = p_reinforcements;
    }

    /** getter function for game map saved in this class
     *
     * @return a game map used in the current game
     */
    public MapEditor getGameMap() {
        return d_gMap;
    }

    /** setter function for game map
     *  advised not to run this in the middle of the game
     *
     * @param p_gMap a game map loaded for the game
     */
    public void setGameMap(MapEditor p_gMap) {
        d_gMap = p_gMap;
    }
}