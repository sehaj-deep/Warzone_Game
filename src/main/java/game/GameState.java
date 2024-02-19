package game;
import java.util.*;

/** GameState class
 * This class captures the current state of the game
 * and store information needed for game playing
 */
public class GameState {
    private List<Player> d_players;  // list of all players in the game

    // list of available reinforcements for all players. Order same as d_players
    private List<Integer> d_reinforcements;
    private HashMap<String, Integer> d_board;  // key: country name. value: number of army in the country
    private String[] d_orderInput;  // a list of tokens given in the user input command to issue an order

    /** Parameterized Constructor
     *
     * @param p_players is a list of players playing the game
     */
    public GameState(List<Player> p_players) {
        d_players = p_players;
        d_reinforcements = new ArrayList<>();
        d_board = new HashMap<String, Integer>();
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
    //public GameMap getGameMap() {
    //    return d_gMap;
    //}

    /** setter function for game map
     *  advised not to run this in the middle of the game
     *
     * @param p_gMap a game map loaded for the game
     */
    //public void setGameMap(GameMap p_gMap) {
    //    d_gMap = p_gMap;
    //}

    /** getter function to access the game board for the current game
     *
     * @return current game board as hash map (key: country id, value: number of armies)
     */
    public HashMap<String, Integer> getGameBoard() {
        return d_board;
    }

    /** setter function
     * to assign new hash map for the game board capturing the current state of the game
     *
     * @param p_board a new game board that captures the current state of the game
     */
    public void setGameBoard(HashMap<String, Integer> p_board) {
        d_board = p_board;
    }

    /** getter function to access order input given by the user in command
     *
     * @return list of tokens in order command
     */
    public String[] getOrderInput() {
        return d_orderInput;
    }

    /** setter function to update the order input given by the user in command
     *
     * @param p_orderInput list of tokens for Order given by the user in command
     */
    public void setOrderInput(String[] p_orderInput) {
        d_orderInput = p_orderInput;
    }
}