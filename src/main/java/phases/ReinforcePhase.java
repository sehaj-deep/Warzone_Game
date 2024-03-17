package phases;

import java.util.HashMap;
import java.util.Set;

import game.GameEngineNew;
import game.Player;
import models.Continent;
import models.Country;

/**
 * Represents the reinforcement phase in the game.
 */
public class ReinforcePhase extends MainPlay {
	/**
	 * Constructs a new ReinforcePhase object.
	 * 
	 * @param p_gameEngine a context object for ExecuteOrders phase
	 */
	public ReinforcePhase(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * move to the next state(phase)
	 */
	public void next() {
		d_gameEngine.setPhase(new IssueOrdersPhaseNew(d_gameEngine));
	}

	/**
	 * Executes the reinforcement phase of the game.
	 * 
	 * @param p_state     The current state of the game.
	 * @param p_mapEditor The map editor representing the game map.
	 */
	public void calculateReinforcements() {
		for (Player p_player : d_gameEngine.getGameState().getPlayers()) {
			int l_reinforcements = calculateReinforcementArmies(p_player);
			d_gameEngine.getGameState().getReinforcements().add(l_reinforcements);
		}

		this.next();
	}

	public int calculateReinforcementArmies(Player p_player) {
		int d_reinforcementArmies = p_player.getOwnership().size() / 3; // Basic calculation based on territories owned

		// Add bonus armies from continents owned
		for (HashMap.Entry<String, Continent> l_continent : d_gameEngine.getD_continents().entrySet()) {

			Set<Country> l_countries = l_continent.getValue().getD_countries();
			boolean l_OwnsAllCountries = false;
			// FIXME
			for (Country country : l_countries) {
				if (!p_player.getOwnership().contains(country.getD_name())) {
					l_OwnsAllCountries = true;
					break;
				}
			}
			if (l_OwnsAllCountries) {
				// Get the bonus armies for the continent from the map editor
				int bonusArmies = l_continent.getValue().getD_continentBonusArmies();
				d_reinforcementArmies += bonusArmies;
			}
		}
		return d_reinforcementArmies;
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
