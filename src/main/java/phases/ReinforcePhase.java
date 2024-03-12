package phases;

import game.GameEngineNew;
import game.GameState;
import game.Player;
import game.Reinforcements;
import map.MapEditor;

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
		// TODO: issue orders phase
		// d_gameEngine.setPhase(new IssueOrdersPhase(d_gameEngine));
	}

	/**
	 * Executes the reinforcement phase of the game.
	 * 
	 * @param p_state     The current state of the game.
	 * @param p_mapEditor The map editor representing the game map.
	 */
	public void run(GameState p_state, MapEditor p_mapEditor) {
		for (Player p_player : p_state.getPlayers()) {
			Reinforcements l_reinforcements = new Reinforcements(p_mapEditor, p_player);
			l_reinforcements.calculateReinforcementArmies();
			int reinforcementArmies = l_reinforcements.getReinforcementArmies();
			p_state.getReinforcements().add(reinforcementArmies);
		}
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
