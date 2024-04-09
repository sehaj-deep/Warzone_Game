package phases;

import game.GameEngine;
import game.GameSave;
import players.Player;
import players.PlayerStrategy;

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
	 * @param p_playerName     Name of the player to be added.
	 * @param p_playerStrategy the type of the player to add
	 */
	public void addPlayers(String p_playerName, PlayerStrategy p_playerStrategy) {
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

	/**
	 * save current game state to a file
	 */
	public void saveGame(String p_fileName, Player p_lastPlayer) {
		GameSave l_saveGame = new GameSave(d_gameEngine);
		l_saveGame.saveGame(p_fileName, p_lastPlayer);
	}

	/**
	 * load game state from a file
	 */
	public void loadGame(String p_fileName) {
		printInvalidCommandMessage();
	}
}
