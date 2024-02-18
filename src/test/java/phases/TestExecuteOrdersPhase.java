package phases;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import game.Player;
import game.GameState;
import game.Deploy;
import map.MapEditor;
import phases.ExecuteOrdersPhase;
import java.util.*;

/** This class tests executeOrderPhase class in JUnit testing
 * 
 */
public class TestExecuteOrdersPhase {
    private static GameState d_state;
    private static MapEditor d_gMap;
    ExecuteOrdersPhase executeOrdersPhase;

    /** This is the common setup for all test cases and will be run before each test 
     * 
     */
    @Before public void before() {
        executeOrdersPhase = new ExecuteOrdersPhase();
        System.out.println("Setting up a game state to test ExecuteOrderPhase");
        List<Integer> reinforcements = new ArrayList<>(Arrays.asList(10, 15));  // set up reinforcement pool
        System.out.println("Reinforcements available to Player0 and Player1: " + reinforcements);
        // set up players list
        List<Player> players = new ArrayList<>();
        for(int i=0; i < 2; i++) {
            Player player = new Player(i);
            player.setPlayerName(Integer.toString(i));
            player.setOwnership(new HashSet<String>(Arrays.asList(Integer.toString(2 * i),
                                    Integer.toString(2 * i + 1))));  // provide countries owned by this player
            System.out.println("Player" + i + " has countries: " + player.getOwnership());
            int deployNumArmy = reinforcements.get(i) / (2 + i);
            for(int j=0; j < (2+i); j++) {  // add Orders for testing purpose
                // Here, player0 has more orders than player1
                player.issue_order(new Deploy(deployNumArmy, Integer.toString( (2*i)+ (j%2))));
            }
            players.add(player);
        }
        d_state = new GameState(players);
        HashMap<String, Integer> boardBefore = new HashMap<String, Integer>();
        boardBefore.put("0", 0); boardBefore.put("1", 0); boardBefore.put("2", 0); boardBefore.put("3", 0);
        d_state.setGameBoard(boardBefore);
        d_state.setReinforcements(reinforcements);
    }
    
    
    /** Test roundRobinExecution method
     *  Check whether the game board is changed according to the issued orders
     */
    @Test public void testRoundRobinExecution() {
        System.out.println("Testing RoundRobinExecution method");
        System.out.println("Game Board before: " + d_state.getGameBoard());
        executeOrdersPhase.roundRobinExecution(d_state, d_gMap);
        HashMap<String, Integer> boardAfter = new HashMap<String, Integer>();
        boardAfter.put("0", 5); boardAfter.put("1", 0); boardAfter.put("2", 5); boardAfter.put("3", 0);
        System.out.println("Game Board After: " + d_state.getGameBoard());
        assertEquals(d_state.getGameBoard(), boardAfter);
        System.out.println("Testing ExecuteOrdersPhase.RoundRobinExecution methods PASSSED");
    }
    
    
    /** Test the getNumAllOrders method
     *  Check whether total number of orders for all players is computed correctly
     */
    @Test public void testGetNumAllOrders() {
        System.out.println("Testing getNumAllOrders method");
        int totNumOrders = executeOrdersPhase.getNumAllOrders(d_state);
        assertEquals(totNumOrders, 5);
        System.out.println("Testing ExecuteOrdersPhase.getNumAllOrders() method PASSED!");
    }


    /** Test the whole phase in ExecuteOrdersPhase
     * 
     */
    @Test public void testExecuteOrdersPhase() {
        System.out.println("Testing ExecuteOrdersPhase method");
        System.out.println("Game Board before: " + d_state.getGameBoard());
        executeOrdersPhase.run(d_state, d_gMap);
        // expected state of the game board after successful execution of all orders
        HashMap<String, Integer> boardAfter = new HashMap<String, Integer>();
        boardAfter.put("0", 5); boardAfter.put("1", 5); boardAfter.put("2", 10); boardAfter.put("3", 5);
        System.out.println("Game Board After: " + d_state.getGameBoard());
        assertEquals(d_state.getGameBoard(), boardAfter);
        int totNumOrders = 0;
        for(int i = 0; i < d_state.getPlayers().size(); i++) {
        		// print out a list of orders per player after the execute orders phase
            Player player = d_state.getPlayers().get(i);
            System.out.println("Player " + player.getPlayerName() + " orders left: " + player.getListOrders());
            totNumOrders = totNumOrders + player.getListOrders().size();
        }
        assertEquals(totNumOrders, 0);
        System.out.println("Testing ExecuteOrdersPhase.RoundRobinExecution methods PASSSED");
    }
}