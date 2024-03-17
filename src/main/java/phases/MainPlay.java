package phases;

import game.GameEngine;

/**
 * Represents the main play phase in the game.
 */
public abstract class MainPlay extends Play {

	/**
	 * Constructor for MainPlay.
	 * 
	 * @param p_gameEngine a context object for MainPlay phase
	 */
	public MainPlay(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
     * Loads the map.
     */
	public void loadMap() {
		this.printInvalidCommandMessage();
	}

    /**
     * Sets players.
     */
	public void setPlayers() {
		this.printInvalidCommandMessage();
	}

    /**
     * Assigns countries.
     */
	public void assignCountries() {
		this.printInvalidCommandMessage();
	}

    /**
     * Adds a player.
     * 
     * @param p_playerName Name of the player to be added.
     */
	public void addPlayers(String p_playerName) {
		printInvalidCommandMessage();
	}
	
	/**
     * Removes a player.
     * 
     * @param p_playerName The name of the player to be removed.
     */
	public void removePlayers(String p_playerName) {
		printInvalidCommandMessage();
	}
}
