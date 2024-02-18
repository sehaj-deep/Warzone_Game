package phases;
import java.util.*;
import game.*;
import iohandlers.InputHandler;
import map.MapEditor;

public class IssueOrdersPhase {
    private boolean d_isDeployDone;  //// indicate whether deployment is finished

    // list indicating whether a player requires to give input commands. Same order as players list in GameState
    private List<Integer> d_requireInputCommand;
    //private InputHandler inputHandler;
    Scanner l_scanner;

    /** Unparameterized constructor for IssueOrderPhase
     *  
     */
    //public IssueOrderPhase(GameState p_state, GameMap p_gMap) {
    public IssueOrdersPhase() {
        d_isDeployDone = false;
        d_requireInputCommand = new ArrayList<Integer>();
        //inputHandler = new InputHandler(p_gMap, p_state);
        l_scanner = new Scanner(System.in);
    }

    /** get user input command for order
     *  ask an user input for order command and store the user input in a GameState object
     *  
     * @param p_gameMap data representation of game map
     * @param p_state the current game state
     */
    public void getOrderCommand(MapEditor p_gameMap, GameState p_state) {
        System.out.print("Enter order command: ");
        String l_userInput = l_scanner.nextLine().trim();
        InputHandler inputHandler = new InputHandler(p_gameMap, p_state);
        inputHandler.parseUserCommand(l_userInput);
    }

    /** getter function for requireInputCommand list
     *  Maybe needed when computer players are added
     *  
     * @return a list of integers where value 1 in i-th means user input command needed for player in i-th index
     *  					value 0 means no user input command needed for that user
     */
    public List<Integer> getRequireInputCommand() {
        return d_requireInputCommand;
    }

    /** setter function for requireInputCommand list
     * 
     * @param p_requireInputCommand new requireInputCommand list
     */
    public void setRequireInputCommand(List<Integer> p_requireInputCommand) {
        d_requireInputCommand = p_requireInputCommand;
    }


    /** create an object of order that is given by a player
     * 
     * @return an Order class object represents the order issued by the user
     */
    public Order createInputOrder(GameState p_state) {
        Order newOrder;
        String order_type = p_state.getOrderInput()[0];
        switch (order_type) {
            default:  // deploy order is the default order
                int numArmy = Integer.parseInt(p_state.getOrderInput()[2]);
                String countryName = p_state.getOrderInput()[1];
                newOrder = new Deploy(numArmy, countryName);
                break;
        }
        return newOrder;
    }


    /** get an order input given by a player but not through user input command
     *  potentially, used for computer players
     
     
    public void getOrderWithoutCommand(GameState p_state, String[] p_orderTokens) {
        p_state.setOrderInput(p_orderTokens);
    }
    */


    /** check if deploy orders are all issued for all players
     * 
     * @param p_state the current game state
     */
    public void checkIsDeployDone(GameState p_state) {
        int totalReinforcements = 0;
        for(int i = 0; i < p_state.getPlayers().size(); i++) {
            totalReinforcements = totalReinforcements + p_state.getReinforcements().get(i);
        }
        if(totalReinforcements == 0) {
            d_isDeployDone = true;
        }
    }


    /** run an issue order phase of the players in a round-robin fashion
     * 
     * @param p_state the current game state
     * @param p_gMap data representing the map used in the current game
     */
    public void run(GameState p_state, MapEditor p_gMap) {
        Order newOrder;
        while(true) {
            checkIsDeployDone(p_state);
            if(d_isDeployDone) {
                break;
            }
            for(int i = 0; i < p_state.getPlayers().size(); i++) {
                // round-robin fashion issue orders
                System.out.println("player" + i);  // debugging
                if(p_state.getReinforcements().get(i) == 0) {
                    // player already used all of his or her reinforcement armies, so skip the player
                    System.out.println("Skip player" + i);  // debugging
                    continue;
                }
                Player plyr = p_state.getPlayers().get(i);
                while (true) {  // request for user command until the order is a valid one
                    System.out.println("Player " + plyr.getPlayerName() + "'s turn to issue an order");
                    getOrderCommand(p_gMap, p_state);
                    newOrder = createInputOrder(p_state);
                    if(newOrder.isValid(p_state, i)) {
                        // change the game state to help validation of subsequent orders
                        System.out.println("Command Valid");  // debugging
                        newOrder.changeGameState(p_state, i);
                        break;
                    }
                    System.out.println("Invalid Order Input. Try again");
                }
                plyr.issue_order(newOrder);
                System.out.println("order added");  // debugging
            }
        }
    }
}