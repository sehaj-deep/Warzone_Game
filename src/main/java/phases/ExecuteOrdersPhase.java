package phases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.GameEngine;
import game.GameState;
import game.Order;
import game.Player;

/**
 * Executes orders of players in the game in a round-robin fashion for one time.
 */
public class ExecuteOrdersPhase extends MainPlay {
	private List<Integer> d_countConquestPerPlayer;

	/**
	 * constructor of Execute Orders Phase
	 * 
	 * @param p_gameEngine a context object for ExecuteOrders phase
	 */
	public ExecuteOrdersPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * move to the next state(phase)
	 */
	public void next() {
		d_gameEngine.setPhase(new ReinforcePhase(d_gameEngine));
	}

	/**
	 * execute orders of players in the game in round-robin fashion for one time
	 *
	 * @param p_state current game state
	 * @param p_gMap  the map that the players are using for the game
	 * @return a list of two elements(if order can't be executed due to being
	 *         invalid) or none(no invalid order) if invalid order exist in current
	 *         loop of round-robin, then index0: player index, index1: 0(false)
	 */
	public List<Integer> roundRobinExecution(GameState p_state) {
		List<Integer> l_errorLog = new ArrayList<>();
		for (int i = 0; i < p_state.getPlayers().size(); i++) {
			Player l_player = p_state.getPlayers().get(i);
			if (l_player.getListOrders().size() == 0) {
				System.out.println(l_player.getPlayerName() + " has no more orders to execute");
				continue;
			}
			Order l_order = l_player.next_order(); // get the next order from player's orders list
			if (l_order.isValidExecute(p_state, i)) {
				// order is valid in the current state, so execute it
				if (l_order.getIsAttack()) {
					attack(p_state, i, l_order);
				} else {
					l_order.execute(p_state, i);
				}
			} else { // order can't be executed, so update error log and return it
				l_errorLog.add(i);
				l_errorLog.add(0);
				return l_errorLog;
			}
		}
		return l_errorLog;
	}

	/**
	 * get number of all outstanding orders for all players in the game
	 *
	 * @param p_state current game state
	 * @return the total number of outstanding orders for all players
	 */
	public int getNumAllOrders(GameState p_state) {
		int l_totNumber = 0;
		for (int i = 0; i < p_state.getPlayers().size(); i++) {
			Player l_player = p_state.getPlayers().get(i);
			l_totNumber = l_totNumber + l_player.getListOrders().size();
		}
		return l_totNumber;
	}

	/**
	 * run execute orders phase for the current round in the game
	 * 
	 * @param p_state current state of the game
	 * @param p_gMap  data representing the map used in the game
	 */

	public void executeAllOrders() {
		d_countConquestPerPlayer = new ArrayList<>(); // count how many successful conquer by player
		for (int i = 0; i < d_gameEngine.getGameState().getPlayers().size(); i++) {
			d_countConquestPerPlayer.add(0);
		}
		int l_totNumOrders = getNumAllOrders(d_gameEngine.getGameState());
		while (l_totNumOrders > 0) {
			List<Integer> l_errorLog = roundRobinExecution(d_gameEngine.getGameState());
			if (l_errorLog.size() > 0) { // couldn't execute all orders because of an invalid order
				// TODO: handle this invalid order and following orders in build2
				System.out.println("Invalid Order Exists. Can't execute such order");
				return;
			}
			l_totNumOrders = getNumAllOrders(d_gameEngine.getGameState());
		}
		giveCard(); // give cards to players
		System.out.println("All Orders Executed in the Execute Orders Phase");

		// check if there is a winner
		if (d_gameEngine.getGameState().getPlayers().size() == 1) {
			System.out.println("Player " + d_gameEngine.getGameState().getPlayers().get(0) + " has won the game");

			// TODO log program exit
			System.exit(0);
		}
		this.next();
	}

	/**
	 * Check whether attack has been successful and conquered the target country
	 * 
	 * @param p_state    current state of the game
	 * @param p_playerId the index of the player given the order in players' list
	 * @param p_order    the player's attack order
	 */
	public void attack(GameState p_state, int p_playerId, Order p_order) {
		List<Player> l_players = d_gameEngine.getGameState().getPlayers();
		String l_targetCountry = p_order.getTargetCountry();
		p_order.execute(p_state, p_playerId);
		if (l_players.get(p_playerId).getOwnership().contains(l_targetCountry)) {
			int l_count = d_countConquestPerPlayer.get(p_playerId);
			d_countConquestPerPlayer.set(p_playerId, l_count + 1);
		}
	}

	/**
	 * Give a player a card if conquered at least one country in this execution
	 * phase and do it for all players
	 */
	public void giveCard() {
		List<Player> l_players = d_gameEngine.getGameState().getPlayers();
		for (int i = 0; i < d_gameEngine.getGameState().getPlayers().size(); i++) {
			if (d_countConquestPerPlayer.get(i) > 0) {
				Random random = new Random();
				int l_randNum = random.nextInt(4 - 1 + 1) + 1;
				String l_cardName;
				switch (l_randNum) {
				case 1:
					l_cardName = "Bomb";
					break;
				case 2:
					l_cardName = "Blockade";
					break;
				case 3:
					l_cardName = "Airlift";
					break;
				default:
					l_cardName = "Diplomacy";
					break;
				}
				l_players.get(i).increaseCardCount(l_cardName);
			}
		}
	}

	/**
	 * getter function for d_countConquestPerPlayer data variable
	 * 
	 * @return new list of number of conquers succeeded
	 */
	public List<Integer> getCountConquestPerPlayer() {
		return d_countConquestPerPlayer;
	}

	/**
	 * Update d_countConquestPerPlayer data variable used for ease of testing
	 * 
	 * @param p_countConquestPerPlayer a new list for d_countConquestPerPlayer
	 */
	public void setCountConquestPerPlayer(List<Integer> p_countConquestPerPlayer) {
		d_countConquestPerPlayer = p_countConquestPerPlayer;
	}

	@Override
	public void loadMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeContinent(String p_continentName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCountry(String p_countryName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinforce() {
		// TODO Auto-generated method stub
		this.printInvalidCommandMessage();

	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fortify() {
		// TODO Auto-generated method stub

	}
}