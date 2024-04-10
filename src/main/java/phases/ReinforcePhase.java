package phases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import game.GameEngine;
import map.Continent;
import map.Country;
import players.Player;

/**
 * Represents the reinforcement phase in the game.
 */
public class ReinforcePhase extends MainPlay implements Serializable {
	/**
	 * Constructs a new ReinforcePhase object.
	 * 
	 * @param p_gameEngine a context object for ExecuteOrders phase
	 */
	public ReinforcePhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * move to the next state(phase)
	 */
	public void next() {
		d_gameEngine.setPhase(new IssueOrdersPhase(d_gameEngine));
	}

	/**
	 * Executes the reinforcement phase of the game.
	 */
	public void calculateReinforcements() {

		d_gameEngine.getReinforcements().clear();

		for (Player p_player : d_gameEngine.getPlayers()) {
			int l_reinforcements = calculateReinforcementArmies(p_player);

			int l_indexOfPlayer = d_gameEngine.getPlayers().indexOf(p_player);

			d_gameEngine.getReinforcements().add(l_reinforcements);
			System.out.println(p_player.getPlayerName() + " has " + l_reinforcements + " army units");

		}
		this.next();
	}

	/**
	 * Calculates the number of reinforcement armies for a player.
	 * 
	 * @param p_player The player for whom reinforcement armies are calculated.
	 * @return The number of reinforcement armies.
	 */
	public int calculateReinforcementArmies(Player p_player) {
		int d_reinforcementArmies = p_player.getOwnership().size() / 3; // Basic calculation based on territories owned

		// Add bonus armies from continents owned
		for (HashMap.Entry<String, Continent> l_continent : d_gameEngine.getD_continents().entrySet()) {

			Set<Country> l_countries = l_continent.getValue().getD_countries();
			boolean l_OwnsAllCountries = true;
			for (Country country : l_countries) {
				if (!p_player.getOwnership().contains(country.getD_name())) {
					l_OwnsAllCountries = false;
					break;
				}
			}
			if (l_OwnsAllCountries) {
				// Get the bonus armies for the continent from the map editor
				int bonusArmies = l_continent.getValue().getD_continentBonusArmies();
				d_reinforcementArmies += bonusArmies;
			}
		}

		// Ensure minimum reinforcement armies
		if (d_reinforcementArmies < 3) {
			d_reinforcementArmies = 3;
		}
		return d_reinforcementArmies;
	}

	/**
	 * Load map while playing
	 *
	 * @param p_filename the filename of the map
	 */
	@Override
	public void loadMap(String p_filename) {
		printInvalidCommandMessage();
	}

	/**
	 * Edit the map of the countries
	 *
	 * @param p_filename the filename of the map
	 */
	@Override
	public void editMap(String p_filename) {
		printInvalidCommandMessage();
	}

	/**
	 * Add the new continent taking by command from player
	 *
	 * @param p_continentName the name of the continent
	 * @param p_bonusArmies   the bonus armies for owning the continent
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		printInvalidCommandMessage();
	}

	/**
	 * Remove continent form game map
	 *
	 * @param p_continentName the name of the continent to remove
	 */
	@Override
	public void removeContinent(String p_continentName) {
		printInvalidCommandMessage();
	}

	/**
	 * Add country in map
	 *
	 * @param p_countryName the name of the country
	 * @param p_continent   the continent to which the country belongs
	 */
	@Override
	public void addCountry(String p_countryName, String p_continent) {
		printInvalidCommandMessage();
	}

	/**
	 * Remove the country from country map
	 *
	 * @param p_countryName the name of the country to remove
	 */
	@Override
	public void removeCountry(String p_countryName) {
		printInvalidCommandMessage();
	}

	/**
	 * Add neighbor country in map
	 *
	 * @param p_country  the name of the country
	 * @param p_neighbor the name of the neighbor country
	 */
	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	/**
	 * Remove neighbor country from map
	 *
	 * @param p_country  the name of the country
	 * @param p_neighbor the name of the neighbor country to remove
	 */
	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	/**
	 * Save the map in game
	 *
	 * @param p_filename The filename to save the map to.
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
