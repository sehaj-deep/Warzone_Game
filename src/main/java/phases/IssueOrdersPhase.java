package phases;
import game.Deploy;
import game.GameEngineNew;
import game.GameState;
import game.Order;
import game.Player;
import iohandlers.InputHandler;
import map.MapEditor;

import java.util.Scanner;

/**
 * IssueOrdersPhase class represents the phase in the game where players issue orders.
 * It extends the MainPlay class and implements the Phase interface.
 */
public class IssueOrdersPhase extends MainPlay {

    private boolean d_isDeployDone; // indicate whether deployment is finished
    private Scanner d_scanner;

    /**
     * Constructor for IssueOrdersPhase class.
     *
     * @param p_gameEngine The GameEngineNew object associated with the phase.
     */
    public IssueOrdersPhase(GameEngineNew p_gameEngine) {
        super(p_gameEngine);
        d_isDeployDone = false;
        d_scanner = new Scanner(System.in);
    }

    /**
     * Gets the user input command for an order.
     *
     * @param p_gameMap The MapEditor object.
     * @param p_state   The GameState object.
     */
    public void getOrderCommand(MapEditor p_gameMap, GameState p_state) {
        String userInput = "showmap";
        while (userInput.equalsIgnoreCase("showmap")) {
            System.out.print("Enter order command: ");
            userInput = d_scanner.nextLine().trim();
            InputHandler inputHandler = new InputHandler(p_gameMap, p_state);
            inputHandler.parseUserCommand(userInput);
        }
    }

    /**
     * Creates and validates an order issued by a player.
     *
     * @param p_tokens   The array of strings representing the order tokens.
     * @param p_state    The GameState object.
     * @param p_playerId The ID of the player issuing the order.
     * @return The created Order object if valid, otherwise null.
     */
    public Order createAndValidateOrder(String[] p_tokens, GameState p_state, int p_playerId) {
        Order newOrder = null;
        String orderType = p_tokens[0];
        // logic for creating an order based on input token
        switch (orderType.toLowerCase()) {
            case "deploy":
                if (p_tokens.length == 3) {
                    String countryName = p_tokens[1];
                    Player currentPlayer = p_state.getPlayers().get(p_playerId);
                    if (currentPlayer.getOwnership().contains(countryName)) { // Check if player owns the country
                        try {
                            int numArmy = Integer.parseInt(p_tokens[2]);
                            newOrder = new Deploy(numArmy, countryName);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number format for number of armies.");
                        }
                    } else {
                        System.out.println("Country where army to be deployed is not owned by the player issued the order");
                    }
                } else {
                    System.out.println("Invalid deploy command format.");
                }
                break;
            // Add more cases for other order types....
            default:
                System.out.println("Invalid order type.");
                break;
        }

        // Validate the order
        if (newOrder != null && newOrder.isValidIssue(p_state, p_playerId)) {
            // If the order is valid, add it to the player's list of orders
            p_state.getPlayers().get(p_playerId).issue_order(newOrder);
        } else {
            // If the order is invalid, set newOrder to null
            newOrder = null;
        }

        return newOrder;
    }

    /**
     * Checks if deploy orders are all issued for all players.
     *
     * @param p_state The GameState object.
     */
    public void checkIsDeployDone(GameState p_state) {
        int totalReinforcements = 0;
        for (int i = 0; i < p_state.getPlayers().size(); i++) {
            totalReinforcements += p_state.getReinforcements().get(i);
        }
        if (totalReinforcements == 0) {
            d_isDeployDone = true;
        }
    }

    /**
     * Runs the issue orders phase of the game.
     *
     * @param p_state The GameState object.
     * @param p_gMap  The MapEditor object.
     */
    @Override
    public void run(GameState p_state, MapEditor p_gMap) {
        Order l_newOrder;
        while (true) {
            checkIsDeployDone(p_state);
            if (d_isDeployDone) {
                break;
            }
            for (int i = 0; i < p_state.getPlayers().size(); i++) {
                // Round-robin fashion issue orders
                Player l_plyr = p_state.getPlayers().get(i);
                if (p_state.getReinforcements().get(i) == 0) {
                    // Player already used all of his or her reinforcement armies, so skip the player
                    System.out.println("No more reinforcement armies for " + l_plyr.getPlayerName() + ".");
                    continue;
                }
                while (true) { // Request for user command until the order is a valid one
                    System.out.println("Player " + l_plyr.getPlayerName() + "'s turn to issue an order");
                    System.out.println("Available reinforcements: " + p_state.getReinforcements().get(i));
                    System.out.println("Countries owned by the current player: " + l_plyr.getOwnership());
                    getOrderCommand(p_gMap, p_state);
                    String[] orderTokens = getPlayerOrderTokens(i); // Get order tokens from player input
                    l_newOrder = createAndValidateOrder(orderTokens, p_state, i);
                    if (l_newOrder != null) {
                        // Change the game state to help validation of subsequent orders
                        l_newOrder.changeGameState(p_state, i);
                        break;
                    }
                    System.out.println("Invalid Order Input. Try again");
                }
                l_plyr.issue_order(l_newOrder);
            }
        }
    }
    

    /**
     * Example method to get player's order tokens.
     * Replace this with your own logic to get order tokens from the player.
     *
     * @param p_playerId The ID of the player.
     * @return The array of strings representing the order tokens.
     */
     String[] getPlayerOrderTokens(int p_playerId) {
        //logic to get order tokens from player input
        return d_scanner.nextLine().split(" ");
    }

    @Override
    public void loadMap(String p_filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadMap'");
    }

    @Override
    public void editMap(String p_filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editMap'");
    }

    @Override
    public void addContinent(String p_continentName, int p_bonusArmies) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addContinent'");
    }

    @Override
    public void removeContinent(String p_continentName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeContinent'");
    }

    @Override
    public void addCountry(String p_countryName, String p_continent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCountry'");
    }

    @Override
    public void removeCountry(String p_countryName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeCountry'");
    }

    @Override
    public void addNeighbor(String p_country, String p_neighbor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addNeighbor'");
    }

    @Override
    public void removeNeighbor(String p_country, String p_neighbor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeNeighbor'");
    }

    @Override
    public void saveMap(String p_filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveMap'");
    }

    @Override
    public void reinforce() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reinforce'");
    }

    @Override
    public void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public void fortify() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fortify'");
    }

    @Override
    public void next() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'next'");
    }
}
