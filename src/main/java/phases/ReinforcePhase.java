package phases;

import game.GameEngine;
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
	public ReinforcePhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
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

	/**
	 * move to the next state(phase)
	 */
	@Override
	public void next() {
		// TODO: issue order phase
	}

}
