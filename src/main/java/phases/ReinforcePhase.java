package phases;

import java.util.HashMap;
import java.util.Set;

import game.GameEngine;
import map.Continent;
import map.Country;
import players.Player;

/**
 * Represents the reinforcement phase in the game.
 */
public class ReinforcePhase extends MainPlay {
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

		d_gameEngine.getGameState().getReinforcements().clear();

		for (Player p_player : d_gameEngine.getGameState().getPlayers()) {
			int l_reinforcements = calculateReinforcementArmies(p_player);

			int l_indexOfPlayer = d_gameEngine.getGameState().getPlayers().indexOf(p_player);

			d_gameEngine.getGameState().getReinforcements().add(l_reinforcements);
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

	@Override
	public void loadMap(String p_filename) {
		printInvalidCommandMessage();
	}

	@Override
	public void editMap(String p_filename) {
		printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		printInvalidCommandMessage();
	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(String p_countryName) {
		printInvalidCommandMessage();
	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	@Override
	public void saveMap(String p_filename) {
		printInvalidCommandMessage();
	}

	@Override
	public void reinforce() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void attack() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void fortify() {
		this.printInvalidCommandMessage();
	}
}
