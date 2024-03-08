package phases;

import java.util.Scanner;

import game.Deploy;
import game.GameState;
import game.Order;
import game.Player;
import iohandlers.InputHandler;
import map.MapEditor;

/**
 * Represents the phase where players issue orders in the game.
 */
public class IssueOrdersPhase {
	private boolean d_isDeployDone; // indicate whether deployment is finished

	// private InputHandler inputHandler;
	Scanner d_scanner;

	/**
	 * Unparameterized constructor for IssueOrderPhase
	 * 
	 */
	// public IssueOrderPhase(GameState p_state, GameMap p_gMap) {
	public IssueOrdersPhase() {
		d_isDeployDone = false;
		d_scanner = new Scanner(System.in);
	}

	/**
	 * get user input command for order ask an user input for order command and
	 * store the user input in a GameState object
	 * 
	 * @param p_gameMap data representation of game map
	 * @param p_state   the current game state
	 */
	public void getOrderCommand(MapEditor p_gameMap, GameState p_state) {
		String l_userInput = "showmap";
		while (l_userInput.equalsIgnoreCase("showmap")) {
			System.out.print("Enter order command: ");
			l_userInput = d_scanner.nextLine().trim();
			InputHandler l_inputHandler = new InputHandler(p_gameMap, p_state);
			l_inputHandler.parseUserCommand(l_userInput);
		}

	}

	/**
	 * create an object of order that is given by a player
	 * 
	 * @param p_state The state of the game
	 * @return an Order class object represents the order issued by the user
	 */
	public Order createInputOrder(GameState p_state) {
		Order l_newOrder;
		String l_order_type = p_state.getOrderInput()[0];
		switch (l_order_type) {
		default: // deploy order is the default order
			int l_numArmy = Integer.parseInt(p_state.getOrderInput()[2]);
			String l_countryName = p_state.getOrderInput()[1];
			l_newOrder = new Deploy(l_numArmy, l_countryName);
			break;
		}
		return l_newOrder;
	}

	/**
	 * check if deploy orders are all issued for all players
	 * 
	 * @param p_state the current game state
	 */
	public void checkIsDeployDone(GameState p_state) {
		int l_totalReinforcements = 0;
		for (int i = 0; i < p_state.getPlayers().size(); i++) {
			l_totalReinforcements = l_totalReinforcements + p_state.getReinforcements().get(i);
		}
		if (l_totalReinforcements == 0) {
			d_isDeployDone = true;
		}
	}

	/**
	 * run an issue order phase of the players in a round-robin fashion
	 * 
	 * @param p_state the current game state
	 * @param p_gMap  data representing the map used in the current game
	 */
	public void run(GameState p_state, MapEditor p_gMap) {
		Order l_newOrder;
		while (true) {
			checkIsDeployDone(p_state);
			if (d_isDeployDone) {
				break;
			}
			for (int i = 0; i < p_state.getPlayers().size(); i++) {
				// round-robin fashion issue orders
				Player l_plyr = p_state.getPlayers().get(i);
				if (p_state.getReinforcements().get(i) == 0) {
					// player already used all of his or her reinforcement armies, so skip the
					// player
					System.out.println("No more reinforcement armies for " + l_plyr.getPlayerName() + ".");
					continue;
				}
				while (true) { // request for user command until the order is a valid one
					System.out.println("Player " + l_plyr.getPlayerName() + "'s turn to issue an order");
					System.out.println("Avilable reinforcements: " + p_state.getReinforcements().get(i));
					System.out.println("Countries owned by the current player: " + l_plyr.getOwnership());
					getOrderCommand(p_gMap, p_state);
					l_newOrder = createInputOrder(p_state);
					if (l_newOrder.isValidIssue(p_state, i)) {
						// change the game state to help validation of subsequent orders
						l_newOrder.changeGameState(p_state, i);
						break;
					}
					System.out.println("Invalid Order Input. Try again");
				}
				l_plyr.issue_order(l_newOrder);
			}
		}
	}
}