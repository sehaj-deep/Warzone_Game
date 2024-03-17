package phases;

import game.GameEngine;

public abstract class MainPlay extends Play {

	/**
	 * Constructor
	 * 
	 * @param p_gameEngine a context object for MainPlay phase
	 */
	public MainPlay(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	public void loadMap() {
		this.printInvalidCommandMessage();
	}

	public void setPlayers() {
		this.printInvalidCommandMessage();
	}

	public void assignCountries() {
		this.printInvalidCommandMessage();
	}

	public void addPlayers(String p_playerName) {
		printInvalidCommandMessage();
	}

	public void removePlayers(String p_playerName) {
		printInvalidCommandMessage();
	}
}
