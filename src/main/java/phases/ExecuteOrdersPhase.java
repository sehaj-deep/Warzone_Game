package phases;

import java.util.ArrayList;
import java.util.List;
import game.GameEngineNew;
import game.GameState;
import game.Order;
import game.Player;
import map.MapEditor;

/**
 * Executes orders of players in the game in a round-robin fashion for one time.
 */
public class ExecuteOrdersPhase extends MainPlay {

	/**
	 * constructor of Execute Orders Phase
	 * 
	 * @param p_gameEngine a context object for ExecuteOrders phase
	 */
	public ExecuteOrdersPhase(GameEngineNew p_gameEngine) {
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
	public List<Integer> roundRobinExecution(GameState p_state, MapEditor p_gMap) {
		List<Integer> l_errorLog = new ArrayList<>();
		for (int i = 0; i < p_state.getPlayers().size(); i++) {
			Player l_player = p_state.getPlayers().get(i);
			if (l_player.getListOrders().size() == 0) {
				System.out.println("Player " + l_player.getPlayerName() + " has no more orders to execute");
				continue;
			}
			Order l_order = l_player.next_order(); // get the next order from player's orders list
			if (l_order.isValidExecute(p_state, i)) {
				// order is valid in the current state, so execute it
				l_order.execute(p_state, i);
			}
			else { // order can't be executed, so update error log and return it
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
	 * run issue orders phase for the current round in the game
	 * 
	 * @param p_state current state of the game
	 * @param p_gMap  data representing the map used in the game
	 */
	public void run(GameState p_state, MapEditor p_gMap) {
		int l_totNumOrders = getNumAllOrders(p_state);
		while (l_totNumOrders > 0) {
			List<Integer> l_errorLog = roundRobinExecution(p_state, p_gMap);
			if (l_errorLog.size() > 0) { // couldn't execute all orders because of an invalid order
				// TODO: handle this invalid order and following orders in build2
				System.out.println("Invalid Order Exists. Can't execute such order");
				return;
			}
			l_totNumOrders = getNumAllOrders(p_state);
		}
		System.out.println("All Orders Executed in the Execute Orders Phase");
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
	public void addPlayers(String p_playerName) {

	}

	@Override
	public void removePlayers(String p_playerName) {

	}

	@Override
	public void reinforce() {
		// TODO Auto-generated method stub

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