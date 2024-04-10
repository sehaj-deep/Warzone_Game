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

	private void setupTournament(String p_mapFile, List<String> p_playerStrategies) {
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

	/**
	 * Starts tournament mode by setting up maps and players, then goes through the
	 * main gameplay loop
	 * 
	 * @param p_mapFiles         list of map files given by user
	 * @param p_playerStrategies list of player strategies given by user
	 * @param p_gamesToBePlayed  number of games to be played on each map given by
	 *                           user
	 */
	@Override
	public void startTournament(List<String> p_mapFiles, List<String> p_playerStrategies, int p_gamesToBePlayed,
			int p_maxNumberOfTurns) {
		String[][] l_results = new String[p_mapFiles.size()][p_gamesToBePlayed];

		int l_fileIdx = 0;
		for (String l_mapFile : p_mapFiles) {
			d_gameEngine.setGameNumber(1); // reset Game Number as map file changes

			while (d_gameEngine.getGameNumber() <= p_gamesToBePlayed) {
				// reset map related objects and players list as new game begins
				d_gameEngine.getD_continents().clear();
				d_gameEngine.getD_continentId().clear();
				d_gameEngine.getD_countries().clear();
				d_gameEngine.getD_countriesId().clear();
				d_gameEngine.getGameBoard().clear();
				d_gameEngine.getPlayers().clear();

				setupTournament(l_mapFile, p_playerStrategies);
				// setPhase(new PlaySetupTournamentMode(this));

				System.out.println("\nGame " + d_gameEngine.getGameNumber() + " on " + l_mapFile);
				System.out.println("------------------");

				boolean l_isWinner = false;
				do {
					System.out.println("\nRound " + d_gameEngine.getRoundNumber());
					System.out.println("--------");

					if (d_gameEngine.getPhase().getClass().equals(new ReinforcePhase(d_gameEngine).getClass())) {
						ReinforcePhase l_reinforcePhase = (ReinforcePhase) d_gameEngine.getPhase();

						l_reinforcePhase.calculateReinforcements();
					}

					if (d_gameEngine.getPhase().getClass().equals(new IssueOrdersPhase(d_gameEngine).getClass())) {
						IssueOrdersPhase l_issueOrdersPhase = (IssueOrdersPhase) d_gameEngine.getPhase();

						l_issueOrdersPhase.issueOrders(d_gameEngine.getD_scanner());
					}

					if (d_gameEngine.getPhase().getClass().equals(new ExecuteOrdersPhase(d_gameEngine).getClass())) {
						ExecuteOrdersPhase l_executeOrdersPhase = (ExecuteOrdersPhase) d_gameEngine.getPhase();

						l_executeOrdersPhase.executeAllOrders();
					}

					if (d_gameEngine.getPhase().getClass().equals(new EndPhase(d_gameEngine).getClass())) {
						EndPhase l_endPhase = (EndPhase) d_gameEngine.getPhase();

						l_endPhase.checkForWinner();
						l_isWinner = l_endPhase.getAnyWinner();
						if (l_isWinner) {
							break;
						}
					}
					d_gameEngine.incrementRoundNumber();
				} while (d_gameEngine.getRoundNumber() <= p_maxNumberOfTurns);

				if (l_isWinner) {
					l_results[l_fileIdx][d_gameEngine.getGameNumber() - 1] = d_gameEngine.findWinner().getPlayerName();
				} else {
					l_results[l_fileIdx][d_gameEngine.getGameNumber() - 1] = "Draw";
				}
				d_gameEngine.incrementGameNumber();
			}
			l_fileIdx++;
		}
		System.out.println("\nResults report:\n" + reportTournamentResult(l_results, p_mapFiles));
		System.exit(1);
	}

	private String reportTournamentResult(String[][] p_results, List<String> p_mapFiles) {
		// Initialize the report with column titles
		String l_report = "\t\t|\t"; // Empty space for row titles
		for (int j = 0; j < p_results[0].length; j++) {
			l_report += "Game " + (j + 1) + "\t\t|\t"; // Add column titles
		}
		l_report += "\n";

		// Add data rows with map titles
		for (int i = 0; i < p_results.length; i++) {
			l_report += p_mapFiles.get(i) + "\t|\t"; // Add row title (map)
			for (int j = 0; j < p_results[i].length; j++) {
				l_report += p_results[i][j] + "\t|\t"; // Add data
			}
			l_report += "\n";
		}

		return l_report;
	}
}
