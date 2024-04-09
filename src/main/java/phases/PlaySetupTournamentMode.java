package phases;

import java.util.List;

import game.GameEngine;
import players.AggressivePlayerStrategy;
import players.BenevolentPlayerStrategy;
import players.CheaterPlayerStrategy;
import players.PlayerStrategy;
import utils.Common;

/**
 * Represents the phase where setup for tournament mode takes place.
 * Extends PlaySetupSingleMode.
 */
public class PlaySetupTournamentMode extends PlaySetupSingleMode {

	/**
	 * Constructs a PlaySetupTournament object with the specified game engine.
	 *
	 * @param p_gameEngine the game engine
	 */
	public PlaySetupTournamentMode(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

    /**
     * Loads the map from a file.
	 * 
     * @param p_filename The filename of the map to load.
     */
	@Override
	public void loadMap(String p_filename) {
		// loadmap is invalid user input for Tournament mode, however we need to call
		// the PlaySetupSingleMode.loadmap() internally when the tournament starts
		this.printInvalidCommandMessage();
	}

	/**
	 * Edits the loaded map.
	 *
	 * @param p_filename The filename of the map to edit.
	 */
	@Override
	public void editMap(String p_filename) {
		this.printInvalidCommandMessage();
	}

	/**
	 * Adds a continent to the map.
	 *
	 * @param p_continentName The name of the continent to add.
	 * @param p_bonusArmies   The bonus armies awarded for controlling the continent.
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		this.printInvalidCommandMessage();
	}

	/**
	 * Removes a continent with the specified name.
	 *
	 * @param p_continentName The name of the continent to remove.
	 */
	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();
	}

	/**
     * Adds a country to the map.
     *
     * @param p_countryName The name of the country to add.
     * @param p_continent   The continent to which the country belongs.
     */
	@Override
	public void addCountry(String p_countryName, String p_continent) {
		this.printInvalidCommandMessage();
	}

    /**
     * Removes a country from the map.
     *
     * @param p_countryName The name of the country to remove.
     */
	@Override
	public void removeCountry(String p_countryName) {
		this.printInvalidCommandMessage();
	}

	/**
     * Adds a neighbor country to an existing country.
     *
     * @param p_country  The name of the country to add a neighbor to.
     * @param p_neighbor The name of the neighboring country.
     */
	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();
	}

    /**
     * Removes a neighbor country from an existing country.
     *
     * @param p_country  The name of the country to remove a neighbor from.
     * @param p_neighbor The name of the neighboring country to remove.
     */
	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();
	}

    /**
     * Saves the current state of the map to a file.
     *
     * @param p_filename The filename to save the map to.
     */
	@Override
	public void saveMap(String p_filename) {
		this.printInvalidCommandMessage();
	}

    /**
     * Adds a player with the specified name and strategy.
     *
     * @param p_playerName    The name of the player to add.
     * @param p_playerStrategy The strategy of the player.
     */
	@Override
	public void addPlayers(String p_playerName, PlayerStrategy p_playerStrategy) {
		this.printInvalidCommandMessage();
	}

    /**
     * Removes a player with the specified name.
     *
     * @param p_playerName The name of the player to remove.
     */
	@Override
	public void removePlayers(String p_playerName) {
		this.printInvalidCommandMessage();
	}

    /**
     * Assigns countries to players.
     */
	@Override
	public void assignCountries() {
		this.printInvalidCommandMessage();
	}

    /**
     * Sets up the tournament by loading the map, creating players, and assigning countries.
     *
     * @param p_mapFile The filename of the map for the tournament.
     * @param p_playerStrategies The strategies chosen for the players.
     */
	@Override
	public void setupTournament(String p_mapFile, List<String> p_playerStrategies) {
		// 1 load map
		String l_filename = Common.getMapPath(p_mapFile);
		super.loadMap(l_filename);

		// 2 create players
		for (String l_playerStrategyInput : p_playerStrategies) {

			PlayerStrategy l_playerStrategy = null;

			switch (l_playerStrategyInput.toLowerCase()) {
			case "aggressive":
				l_playerStrategy = new AggressivePlayerStrategy();
				break;
			case "benevolent":
				l_playerStrategy = new BenevolentPlayerStrategy();
				break;
			case "random":
				// TODO
				// l_playerStrategy = new RandomPlayerStrategy();
				break;
			case "cheater":
				l_playerStrategy = new CheaterPlayerStrategy();
				break;
			default:
				throw new IllegalArgumentException(l_playerStrategyInput + " is not a valid player strategy.");
			}
			super.addPlayers(l_playerStrategyInput + "Player", l_playerStrategy);
		}
		// 3 assign countries
		super.assignCountries();
	}
}
