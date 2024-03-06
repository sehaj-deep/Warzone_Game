package phases;

import game.GameEngine;

public abstract class Phase {
	public GameEngine d_gameEngine;

	/**
	 * Parameterized Constructor
	 * 
	 * @param p_gameEngine a context object for phase of the Game and Map editing
	 */
	public Phase(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	abstract public void next();
}