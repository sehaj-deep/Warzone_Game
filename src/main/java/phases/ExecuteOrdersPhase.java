package phases;

import game.GameState;
import game.Order;
import game.Player;
import map.MapEditor;
import java.util.*;


public class ExecuteOrdersPhase {
    public ExecuteOrdersPhase() {

    }

    /** execute orders of players in the game in round-robin fashion for one time
     *
     * @param p_state current game state
     * @param p_gMap the map that the players are using for the game
     * @return a list of two elements(if order can't be executed due to being invalid) or none(no invalid order)
     *          if invalid order exist in current loop of round-robin, then index0: player index, index1: 0(false)
     */
    public List<Integer> roundRobinExecution(GameState p_state, MapEditor p_gMap) {
        List<Integer> errorLog = new ArrayList<>();
        for(int i = 0; i < p_state.getPlayers().size(); i++) {
            Player player = p_state.getPlayers().get(i);
            if(player.getListOrders().size() == 0) {
                System.out.println("Player " + player.getPlayerName() + " has no more orders to execute");
                continue;
            }
            Order order = player.next_order();  // get the next order from player's orders list
            if(order.isValid(p_state, i)) {
                // order is valid in the current state, so execute it
                order.execute(p_state, i);
            } else { // order can't be executed, so update error log and return it
                errorLog.add(i); errorLog.add(0);
                return errorLog;
            }
        }
        return errorLog;
    }

    /** get number of all outstanding orders for all players in the game
     *
     * @param p_state current game state
     * @return the total number of outstanding orders for all players
     */
    public int getNumAllOrders(GameState p_state) {
        int totNumber = 0;
        for(int i = 0; i < p_state.getPlayers().size(); i++) {
            Player player = p_state.getPlayers().get(i);
            totNumber = totNumber + player.getListOrders().size();
        }
        return totNumber;
    }

    /** run issue orders phase for the current round in the game
     * 
     * @param p_state current state of the game
     * @param p_gMap data representing the map used in the game
     */
    public void run(GameState p_state, MapEditor p_gMap) {
        int totNumOrders = getNumAllOrders(p_state);
        while(totNumOrders > 0) {
            List<Integer> errorLog = roundRobinExecution(p_state, p_gMap);
            if(errorLog.size() > 0) {  // couldn't execute all orders because of an invalid order
                //TODO: handle this invalid order and following orders in build2
                System.out.println("Invalid Order Exists. Can't execute such order");
                return;
            }
            totNumOrders = getNumAllOrders(p_state);
        }
        System.out.println("All Orders Executed in the Execute Orders Phase");
    }
}