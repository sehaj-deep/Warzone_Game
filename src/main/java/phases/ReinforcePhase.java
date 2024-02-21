package phases;

import game.GameState;
import game.Player;
import game.Reinforcements;
import map.MapEditor;

/**
 * Represents the reinforcement phase in the game.
 */
public class ReinforcePhase {
	/**
	 * Constructs a new ReinforcePhase object.
	 */
	public ReinforcePhase() {
	}

	/**
	 * Executes the reinforcement phase of the game.
	 * 
	 * @param p_state     The current state of the game.
	 * @param p_mapEditor The map editor representing the game map.
	 */
	public void execute(GameState p_state, MapEditor p_mapEditor) {
		for (Player p_player : p_state.getPlayers()) {
			Reinforcements l_reinforcements = new Reinforcements(p_mapEditor, p_player);
			l_reinforcements.calculateReinforcementArmies();
			int reinforcementArmies = l_reinforcements.getReinforcementArmies();
			p_state.getReinforcements().add(reinforcementArmies);
		}
	}

}
