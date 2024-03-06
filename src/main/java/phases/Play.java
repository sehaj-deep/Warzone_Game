package phases;

import game.GameEngine;

public abstract class Play extends Phase {

	/**
	 * Constructor
	 * 
	 * @param p_gameEngine a context object for Play phase
	 */
	public Play(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	public void endGame() {
		// TODO: end game
	}
}