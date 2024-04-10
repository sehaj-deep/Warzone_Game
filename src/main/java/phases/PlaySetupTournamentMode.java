package phases;

import java.util.List;

import game.GameEngine;
import players.AggressivePlayerStrategy;
import players.BenevolentPlayerStrategy;
import players.CheaterPlayerStrategy;
import players.PlayerStrategy;
import players.RandomPlayerStrategy;
import utils.Common;

public class PlaySetupTournamentMode extends PlaySetupSingleMode {

	/**
	 * Constructs a PlaySetupTournament object with the specified game engine.
	 *
	 * @param p_gameEngine the game engine
	 */
	public PlaySetupTournamentMode(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void loadMap(String p_filename) {
		// loadmap is invalid user input for Tournament mode, however we need to call
		// the PlaySetupSingleMode.loadmap() internally when the tournament starts
		this.printInvalidCommandMessage();
	}

	@Override
	public void editMap(String p_filename) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(String p_countryName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void saveMap(String p_filename) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addPlayers(String p_playerName, PlayerStrategy p_playerStrategy) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removePlayers(String p_playerName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void assignCountries() {
		this.printInvalidCommandMessage();
	}

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
				l_playerStrategy = new RandomPlayerStrategy();
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
