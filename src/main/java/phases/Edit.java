package phases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.GameEngine;
import map.Continent;
import map.Country;
import players.Player;
import players.PlayerStrategy;

/**
 * Represents the edit phase of the game map.
 */
public abstract class Edit extends Phase implements Serializable {

    /** 
	 * Flag indicating whether the map is a Conquest map. 
	 */
	protected boolean d_isConquestMap = false;

	/**
	 * Constructs a new Edit object.
	 * 
	 * @param p_gameEngine a context object for the edit phase
	 */
	public Edit(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * Sets the players for the edit phase.
	 */
	public void setPlayers() {
		printInvalidCommandMessage();
	}

	/**
	 * Assign the countries for the edit phase.
	 */
	public void assignCountries() {
		printInvalidCommandMessage();
	}

	/**
	 * Reinforce method for the edit phase.
	 */
	public void reinforce() {
		printInvalidCommandMessage();
	}

	/**
	 * Attack method for the edit phase.
	 */
	public void attack() {
		printInvalidCommandMessage();
	}

	/**
	 * Fortify method for the edit phase.
	 */
	public void fortify() {
		printInvalidCommandMessage();
	}

	/**
	 * End the game in the edit phase.
	 */
	public void endGame() {
		printInvalidCommandMessage();
	}

	/**
	 * To setup tournament mode
	 */
	public void setupTournament(String p_mapFile, List<String> p_playerStrategies) {
		printInvalidCommandMessage();
	}

	/**
	 * save current game state to a file
	 */
	public void saveGame(String p_fileName, Player p_lastPlayer) {
		printInvalidCommandMessage();
	}

	/**
	 * load game state from a file
	 */
	public void loadGame(String p_fileName) {
		printInvalidCommandMessage();
	}

	/**
	 * Adds players to the edit phase.
	 *
	 * @param p_playerName the name of the player to add
	 */
	public void addPlayers(String p_playerName, PlayerStrategy p_playerStrategy) {
		printInvalidCommandMessage();
	}

	/**
	 * Removes players from the edit phase.
	 *
	 * @param p_playerName the name of the player to remove
	 */
	public void removePlayers(String p_playerName) {
		printInvalidCommandMessage();
	}

	/**
	 * Display the map in text format. The map consists of continents and their
	 * corresponding countries with neighbors.
	 */
	public void showMap() {
		System.out.println("The following is the text format of the map");
		System.out.println("----------------------------------------------------------------------");

		Map<String, Continent> l_continents = d_gameEngine.getD_continents();
		for (HashMap.Entry<String, Continent> l_cont : l_continents.entrySet()) {
			Continent l_currContinent = l_cont.getValue();
			String l_nameOfCont = l_currContinent.getD_continentName();
			System.out.println("For the continent: " + l_nameOfCont);
			System.out.println("[Country: Neighbors]");

			Set<Country> l_corresCountries = l_currContinent.getD_countries();
			for (Country l_country : l_corresCountries) {
				String l_countryName = l_country.getD_name();
				System.out.print(l_countryName + ": ");

				Set<Country> l_countryNeighbors = l_country.getNeighbors();
				for (Country neighbor : l_countryNeighbors) {
					String l_neighborName = neighbor.getD_name();
					System.out.print(l_neighborName + "; ");
				}
				System.out.println();
			}
			System.out.println("----------------------------------------------------------------------");
		}
	}

}
