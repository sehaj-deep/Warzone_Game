package phases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import game.GameEngine;
import orders.Order;
import players.Player;

/**
 * Executes orders of players in the game in a round-robin fashion for one time.
 */
public class ExecuteOrdersPhase extends MainPlay implements Serializable {

	/**
	 * List to store the number of successful conquests per player during the
	 * execution phase.
	 */
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
		d_gameEngine.setPhase(new EndPhase(d_gameEngine));
	}

	/**
	 * execute orders of players in the game in round-robin fashion for one time
	 *
	 * @return a list of two elements(if order can't be executed due to being
	 *         invalid) or none(no invalid order) if invalid order exist in current
	 *         loop of round-robin, then index0: player index, index1: 0(false)
	 */
	public List<Integer> roundRobinExecution() {
		List<Integer> l_errorLog = new ArrayList<>();
		for (int i = 0; i < d_gameEngine.getPlayers().size(); i++) {
			Player l_player = d_gameEngine.getPlayers().get(i);
			if (l_player.getListOrders().isEmpty()) {
				System.out.println(l_player.getPlayerName() + " has executed all their orders.");
				continue;
			}
			Order l_order = l_player.next_order(); // get the next order from player's orders list
			if (l_order.isValidExecute(i)) {
				// order is valid in the current state, so execute it
				if (l_order.getIsAttack()) {
					attack(i, l_order);
				}
				else {
					l_order.execute(i);
					System.out
							.println(l_player.getPlayerName() + " executed a " + l_order.getClass().getSimpleName() + " order.");
				}
			}
			else { // order can't be executed, so update error log and return it
				l_errorLog.add(i);
				l_errorLog.add(0);
			}
		}
		return l_errorLog;
	}

	/**
	 * get number of all outstanding orders for all players in the game
	 *
	 * @return the total number of outstanding orders for all players
	 */
	public int getNumAllOrders() {
		int l_totNumber = 0;
		for (int i = 0; i < d_gameEngine.getPlayers().size(); i++) {
			Player l_player = d_gameEngine.getPlayers().get(i);
			l_totNumber = l_totNumber + l_player.getListOrders().size();
		}
		return l_totNumber;
	}

	/**
	 * run execute orders phase for the current round in the game
	 */
	public void executeAllOrders() {
		d_countConquestPerPlayer = new ArrayList<>(); // count how many successful conquer by player
		for (int i = 0; i < d_gameEngine.getPlayers().size(); i++) {
			d_countConquestPerPlayer.add(0);
		}
		int l_totNumOrders = getNumAllOrders();
		while (l_totNumOrders > 0) {
			List<Integer> l_errorLog = roundRobinExecution();
			if (l_errorLog.size() > 0) { // couldn't execute all orders because of an invalid order
				System.out.println("Invalid Order Exists. Can't execute such order");
				return;
			}
			l_totNumOrders = getNumAllOrders();
		}
		giveCard(); // give cards to players
		System.out.println("All Orders Executed in the Execute Orders Phase");

		for (Player p_singlePlayer : d_gameEngine.getPlayers()) {
			p_singlePlayer.resetTheNegotiation();
		}
		this.next();
	}

	/**
	 * Check whether attack has been successful and conquered the target country
	 * 
	 * @param p_playerId the index of the player given the order in players' list
	 * @param p_order    the player's attack order
	 */
	public void attack(int p_playerId, Order p_order) {
		List<Player> l_players = d_gameEngine.getPlayers();
		String l_targetCountry = p_order.getTargetCountry();
		Player l_negotiatedPlayer = null;
		for (Player l_singlePlayer : l_players) {
			if (l_singlePlayer.getOwnership().contains(l_targetCountry)) {
				l_negotiatedPlayer = l_singlePlayer;
			}
		}
		if (!l_players.get(p_playerId).getD_negotiatedWith().contains(l_negotiatedPlayer)) {
			p_order.execute(p_playerId);
			if (l_players.get(p_playerId).getOwnership().contains(l_targetCountry)) {
				int l_count = d_countConquestPerPlayer.get(p_playerId);
				d_countConquestPerPlayer.set(p_playerId, l_count + 1);
			}
		}

	}

	/**
	 * Give a player a card if conquered at least one country in this execution
	 * phase and do it for all players
	 */
	public void giveCard() {
		List<Player> l_players = d_gameEngine.getPlayers();
		for (int i = 0; i < d_gameEngine.getPlayers().size(); i++) {
			if (d_countConquestPerPlayer.get(i) > 0) {
				Random random = new Random();
				int l_randNum = random.nextInt(4 - 1 + 1) + 1;
				String l_cardName;
				switch (l_randNum) {
				case 1:
					l_cardName = "Bomb";
					System.out.println(l_players.get(i).getPlayerName() + " have been assigned Bomb Card.");
					break;
				case 2:
					l_cardName = "Blockade";
					System.out.println(l_players.get(i).getPlayerName() + " have been assigned Blockade Card.");
					break;
				case 3:
					l_cardName = "Airlift";
					System.out.println(l_players.get(i).getPlayerName() + " have been assigned Airlift Card.");
					break;
				default:
					l_cardName = "Diplomacy";
					System.out.println(l_players.get(i).getPlayerName() + " have been assigned Diplomacy Card.");
					break;
				}
				l_players.get(i).increaseCardCount(l_cardName);
				l_players.get(i).displayCards();
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

	/**
	 * Loads a map from a file.
	 *
	 * @param p_filename The filename of the map to be loaded.
	 */
	@Override
	public void loadMap(String p_filename) {
		printInvalidCommandMessage();
	}

	/**
	 * Edits the loaded map.
	 *
	 * @param p_filename The filename of the map to be edited.
	 */
	@Override
	public void editMap(String p_filename) {
		printInvalidCommandMessage();
	}

	/**
	 * Adds a continent to the map.
	 *
	 * @param p_continentName The name of the continent to be added.
	 * @param p_bonusArmies   The bonus armies for the continent.
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		printInvalidCommandMessage();
	}

	/**
	 * Removes a continent with the specified name.
	 *
	 * @param p_continentName The name of the continent to be removed.
	 */
	@Override
	public void removeContinent(String p_continentName) {
		printInvalidCommandMessage();

	}

	/**
	 * Adds a country with the specified name and continent.
	 *
	 * @param p_countryName The name of the country to be added.
	 * @param p_continent   The continent to which the country belongs.
	 */
	@Override
	public void addCountry(String p_countryName, String p_continent) {
		printInvalidCommandMessage();

	}

	/**
	 * Removes a country with the specified name.
	 *
	 * @param p_countryName The name of the country to be removed.
	 */
	@Override
	public void removeCountry(String p_countryName) {
		printInvalidCommandMessage();

	}

	/**
	 * Adds a neighbor country to an existing country.
	 *
	 * @param p_country  The country to which the neighbor will be added.
	 * @param p_neighbor The name of the neighbor country to be added.
	 */
	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();

	}

	/**
	 * Removes a neighbor from the specified country.
	 *
	 * @param p_country  The country from which the neighbor will be removed.
	 * @param p_neighbor The name of the neighbor country to be removed.
	 */
	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();

	}

	/**
	 * Saves the current state of the map to a file.
	 *
	 * @param p_filename The filename to which the map will be saved.
	 */
	@Override
	public void saveMap(String p_filename) {
		printInvalidCommandMessage();

	}

	/**
	 * Starts a tournament with the specified parameters.
	 *
	 * @param p_mapFiles          A list of map files to be used in the tournament.
	 * @param p_playerStrategies  A list of player strategies to participate in the tournament.
	 * @param p_gamesToBePlayed   The number of games to be played in the tournament.
	 * @param p_maxNumberOfTurns  The maximum number of turns allowed per game in the tournament.
	 */
	@Override
	public void startTournament(List<String> p_mapFiles, List<String> p_playerStrategies, int p_gamesToBePlayed,
			int p_maxNumberOfTurns) {
		this.printInvalidCommandMessage();
	}
}