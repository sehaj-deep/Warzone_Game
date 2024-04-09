package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import logger.LogEntryBuffer;
import logger.LogFileWriter;
import map.Continent;
import map.Country;
import phases.EndPhase;
import phases.ExecuteOrdersPhase;
import phases.IssueOrdersPhase;
import phases.Phase;
import phases.PlaySetupSingleMode;
import phases.PlaySetupTournamentMode;
import phases.Preload;
import phases.ReinforcePhase;
import players.AggressivePlayerStrategy;
import players.BenevolentPlayerStrategy;
import players.CheaterPlayerStrategy;
import players.HumanPlayerStrategy;
import players.Player;
import players.PlayerStrategy;
import utils.Common;

/**
 * GameEngine Class This Class runs the game by integrating all the functions
 * and classes needed for Warzone
 */
public class GameEngine {
	/**
	 * Scanner to take user input
	 */
	private Scanner d_scanner = new Scanner(System.in);

	/**
	 * Buffer to store log entries.
	 */
	private LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

	/**
	 * Object to write log entries to a file.
	 */
	private LogFileWriter d_logFileWriter = new LogFileWriter(d_logEntryBuffer);

	/**
	 * The current game number
	 */
	private static int d_gameNumber = 1;

	/**
	 * The current round
	 */
	private int d_roundNumber = 1;

	/**
	 * The current phase of the game.
	 */
	protected Phase d_gamePhase;

	/**
	 * A hashmap to store the continents
	 */
	protected HashMap<String, Continent> d_continents = new HashMap<>();

	/**
	 * To map continent id with continent name
	 */
	protected HashMap<Integer, Continent> d_continentId = new HashMap<>();

	/**
	 * A hashmap to store the countries
	 */
	protected HashMap<String, Country> d_countries = new HashMap<>();

	/**
	 * To map countries id with country name
	 */
	protected HashMap<Integer, Country> d_countriesId = new HashMap<>();

	/**
	 * The name of the map.
	 */
	protected String d_mapName;

	/**
	 * list of all players in the game
	 */
	private List<Player> d_players = new ArrayList<>();

	/**
	 * list of available reinforcements for all players. Order same as d_players
	 */
	private List<Integer> d_reinforcements = new ArrayList<>();

	/**
	 * key: country name. value: number of army in the country
	 */
	private Map<String, Integer> d_board = new HashMap<String, Integer>();

	/**
	 * To change the game number and reset the round number
	 * 
	 * @param p_phase The game number to be set
	 */
	public void setGameNumber(int p_gameNumber) {
		d_gameNumber = p_gameNumber;
		d_roundNumber = 1;

		d_continents.clear();
		d_continentId.clear();
		d_countries.clear();
		d_countriesId.clear();
	}

	/**
	 * To get the game number
	 * 
	 * @return The current game number
	 */
	public int getGameNumber() {
		return d_gameNumber;
	}

	/**
	 * To change the round number
	 * 
	 * @param p_phase The round number to be set
	 */
	public void setRoundNumber(int p_roundNumber) {
		d_roundNumber = p_roundNumber;
	}

	/**
	 * To get the round number
	 * 
	 * @return The current round number
	 */
	public int getRoundNumber() {
		return d_roundNumber;
	}

	/**
	 * Increment game number by 1
	 */
	public void incrementGameNumber() {
		setGameNumber(getGameNumber() + 1);
	}

	/**
	 * Increment game round by 1
	 */
	public void incrementRoundNumber() {
		setRoundNumber(getRoundNumber() + 1);
	}

	/**
	 * To change the phase
	 * 
	 * @param p_phase The phase to be set
	 */
	public void setPhase(Phase p_phase) {
		d_gamePhase = p_phase;
		d_logEntryBuffer.setD_currentPhase("Current phase is " + p_phase.getClass());
	}

	/**
	 * To get the current phase
	 * 
	 * @return The current Phase
	 */
	public Phase getPhase() {
		return d_gamePhase;
	}

	/**
	 * 
	 * @return the list of continents
	 */
	public Map<String, Continent> getD_continents() {
		return d_continents;
	}

	/**
	 * 
	 * @param p_continentName Unique name of the continent
	 * @return the required continent mapped to continent ID
	 */
	public Continent getD_continents(String p_continentName) {
		return d_continents.get(p_continentName);
	}

	/**
	 * Removes a continent based on the provided continent name.
	 *
	 * @param p_continentName The name of the continent to be removed.
	 * @return The removed continent object, or null if the continent with the
	 *         specified name doesn't exist.
	 */
	public Continent removeD_continent(String p_continentName) {
		return d_continents.remove(p_continentName);
	}

	/**
	 * 
	 * @return the list of continents mapped to corresponding ID
	 */
	public HashMap<Integer, Continent> getD_continentId() {
		return d_continentId;
	}

	/**
	 * 
	 * @param p_continentId The Id of the continent
	 * @return return the Object of the continent corresponding to an Id.
	 */
	public Continent getD_continentId(int p_continentId) {
		return d_continentId.get(p_continentId);
	}

	/**
	 * Removes a continent based on the provided continent ID.
	 *
	 * @param p_continentId The ID of the continent to be removed.
	 * @return The removed continent object, or null if the continent with the
	 *         specified ID doesn't exist.
	 */
	public Continent removeD_continentID(int p_continentId) {
		return d_continentId.remove(p_continentId);
	}

	/**
	 * Retrieves the hashmap containing the countries.
	 *
	 * @return The hashmap containing the countries.
	 */
	public HashMap<String, Country> getD_countries() {
		return d_countries;
	}

	/**
	 * Retrieves the country object with the specified country name.
	 *
	 * @param p_countryName The name of the country to retrieve.
	 * @return The country object corresponding to the given country name.
	 */
	public Country getD_countries(String p_countryName) {
		return d_countries.get(p_countryName);
	}

	/**
	 * Mapping of country ids to corresponding Country objects.
	 * 
	 * @return the name of the country corresponding to the id.
	 */
	public HashMap<Integer, Country> getD_countriesId() {
		return d_countriesId;
	}

	/**
	 * Gets the name of the map.
	 * 
	 * @return name of the map
	 */
	public String getD_mapName() {
		return d_mapName;
	}

	/**
	 * Set the name of the map
	 * 
	 * @param d_mapName Name of the map file
	 */
	public void setD_mapName(String d_mapName) {
		this.d_mapName = d_mapName;
	}

	/**
	 * getter function for a list of players playing the game
	 *
	 * @return d_players a list of players playing the game
	 */
	public List<Player> getPlayers() {
		return d_players;
	}

	/**
	 * Set the players in the players list
	 * 
	 * @param p_players List of Players
	 */
	public void setPlayers(List<Player> p_players) {
		d_players = p_players;
	}

	/**
	 * getter function to get the list of available reinforcements for all players
	 *
	 * @return d_reinforcements a list of available reinforcements for all players
	 */
	public List<Integer> getReinforcements() {
		return d_reinforcements;
	}

	/**
	 * setter function to set d_reinforcements to the provided list may handy for
	 * unit testing
	 *
	 * @param p_reinforcements a new list of available reinforcements for all
	 *                         players
	 */
	public void setReinforcements(List<Integer> p_reinforcements) {
		d_reinforcements = p_reinforcements;
	}

	/**
	 * getter function to access the game board for the current game
	 *
	 * @return current game board as hash map (key: country id, value: number of
	 *         armies)
	 */
	public Map<String, Integer> getGameBoard() {
		return d_board;
	}

	/**
	 * setter function to assign new hash map for the game board capturing the
	 * current state of the game
	 *
	 * @param p_board a new game board that captures the current state of the game
	 */
	public void setGameBoard(Map<String, Integer> p_board) {
		d_board = p_board;
	}

	/**
	 * Starts the game engine and manages the game phases.
	 */
	public void start() {
		try {
			System.out.println("1. Edit Map");
			System.out.println("2. Start Game");
			System.out.print("Enter your choice: ");
			String choice = d_scanner.nextLine();

			switch (choice) {
			case "1":
				setPhase(new Preload(this));
				break;
			case "2":
				chooseGameMode();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}

			String l_command = "";

			while (this.getPhase() instanceof PlaySetupSingleMode) {
				System.out.print("\n> Enter a command: ");
				l_command = d_scanner.nextLine();
				parseUserCommand(l_command);
			}

			l_command = "";
			do {
				if (this.getPhase().getClass().equals(new ReinforcePhase(this).getClass())) {
					ReinforcePhase l_reinforcePhase = (ReinforcePhase) this.getPhase();

					l_reinforcePhase.calculateReinforcements();
					continue;
				}

				if (this.getPhase().getClass().equals(new IssueOrdersPhase(this).getClass())) {
					IssueOrdersPhase l_issueOrdersPhase = (IssueOrdersPhase) this.getPhase();

					l_issueOrdersPhase.issueOrders(d_scanner);
					continue;
				}

				if (this.getPhase().getClass().equals(new ExecuteOrdersPhase(this).getClass())) {
					ExecuteOrdersPhase l_executeOrdersPhase = (ExecuteOrdersPhase) this.getPhase();

					l_executeOrdersPhase.executeAllOrders();
					continue;
				}

				if (this.getPhase().getClass().equals(new EndPhase(this).getClass())) {
					EndPhase l_endPhase = (EndPhase) this.getPhase();

					l_endPhase.checkForWinner();
					if (l_endPhase.getAnyWinner()) {
						break;
					}
					continue;
				}

				System.out.println("\n> Enter a command: ");
				l_command = d_scanner.nextLine();

				parseUserCommand(l_command);
			} while (!l_command.toLowerCase().equals("done"));
		} finally {
			if (d_scanner != null) {
				d_scanner.close();
			}
		}
	}

	private void chooseGameMode() {
		System.out.println("\n1. Single Game Mode");
		System.out.println("2. Tournament Mode");
		System.out.print("Enter game mode: ");
		String choice = d_scanner.nextLine();

		switch (choice) {
		case "1":
			setPhase(new PlaySetupSingleMode(this));
			break;
		case "2":
			setPhase(new PlaySetupTournamentMode(this));
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
			break;
		}
	}

	/**
	 * Parses the user command input and use it to appropriate methods based on the
	 * command type.
	 *
	 * @param p_userInput The user input command to be parsed.
	 */
	public void parseUserCommand(String p_userInput) {

		String[] l_tokens = p_userInput.split("\\s+");
		String l_command = l_tokens[0].toLowerCase();

		switch (l_command) {
		case "editcontinent":
			parseEditContinentCommand(l_tokens);
			break;
		case "editcountry":
			parseEditCountryCommand(l_tokens);
			break;
		case "editneighbor":
			parseEditNeighborCommand(l_tokens);
			break;
		case "showmap":
			parseShowMapCommand();
			break;
		case "savemap":
			parseSaveMapCommand(l_tokens);
			break;
		case "editmap":
			parseEditMapCommand(l_tokens);
			break;
		case "validatemap":
			parseValidateMapCommand(l_tokens);
			break;
		case "gameplayer":
			parseGamePlayerCommand(l_tokens);
			break;
		case "assigncountries":
			parseAssignCountriesCommand(l_tokens);
			break;
		case "loadmap":
			parseLoadMapCommand(l_tokens);
			break;
		case "tournament":
			parseTournamentCommand(l_tokens);
			break;
		default:
			System.out.println("Invalid command. Please try again.");
		}
	}

	/**
	 * Adds a continent and logs the action.
	 * 
	 * @param l_continentName  The name of the continent to add.
	 * @param l_continentValue The value of the continent to add.
	 */
	private void addContinentAndLog(String l_continentName, String l_continentValue) {
		try {
			d_gamePhase.addContinent(l_continentName, Integer.parseInt(l_continentValue));
			d_logEntryBuffer.setD_effectOfAction("Continent " + l_continentName + " was added.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Removes a continent and logs the action.
	 * 
	 * @param l_continentName The name of the continent to remove.
	 */
	private void removeContinentAndLog(String l_continentName) {
		try {
			d_gamePhase.removeContinent(l_continentName);
			d_logEntryBuffer.setD_effectOfAction("Continent " + l_continentName + " was removed.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Parses the 'tournament' command.
	 * 
	 * @param p_tokens command tokens.
	 */
	private void parseTournamentCommand(String[] p_tokens) {
		if (p_tokens.length < 10) {
			System.out.println(
					"Invalid command. Syntax: tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");
			return;
		}

		String l_option = "";
		List<String> l_mapFiles = new ArrayList<String>();
		List<String> l_playerStrategies = new ArrayList<String>();
		int l_numberOfGames = 0;
		int l_maxNumberOfTurns = 0;

		try {
			for (int i = 1; i < p_tokens.length; i++) {

				if (p_tokens[i].startsWith("-")) {
					l_option = p_tokens[i].toUpperCase();
				} else {
					switch (l_option) {
					case "-M":
						if (p_tokens[i].endsWith(".map")) {
							l_mapFiles.add(p_tokens[i]);
						} else {
							throw new IllegalArgumentException(
									"Invalid input: Map files must end with .map extension.");
						}
						break;
					case "-P":
						l_playerStrategies.add(p_tokens[i]);
						break;
					case "-G":
						l_numberOfGames = Integer.parseInt(p_tokens[i]);
						break;
					case "-D":
						l_maxNumberOfTurns = Integer.parseInt(p_tokens[i]);
						break;
					default:
						throw new IllegalArgumentException(
								"Invalid option for tournament command. Use -M, -P, -G or -D.");
					}
				}
			}
			validateArgumentsForTournmanetCommand(l_mapFiles, l_playerStrategies, l_numberOfGames, l_maxNumberOfTurns);
			startTournament(l_mapFiles, l_playerStrategies, l_numberOfGames, l_maxNumberOfTurns);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
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
	private void startTournament(List<String> p_mapFiles, List<String> p_playerStrategies, int p_gamesToBePlayed,
			int p_maxNumberOfTurns) {
		String[][] l_results = new String[p_mapFiles.size()][p_gamesToBePlayed];

		for (String l_mapFile : p_mapFiles) {
			for (int l_currentGameNumber = getGameNumber(); l_currentGameNumber < p_gamesToBePlayed; incrementGameNumber()) {
				System.out.println("Game " + getGameNumber());
				System.out.println("-------");

				d_gamePhase.setupTournament(l_mapFile, p_playerStrategies);
				boolean l_isWinner = false;

				do {
					System.out.println("Round " + getRoundNumber());
					System.out.println("--------");
					if (this.getPhase().getClass().equals(new ReinforcePhase(this).getClass())) {
						ReinforcePhase l_reinforcePhase = (ReinforcePhase) this.getPhase();

						l_reinforcePhase.calculateReinforcements();
						continue;
					}

					if (this.getPhase().getClass().equals(new IssueOrdersPhase(this).getClass())) {
						IssueOrdersPhase l_issueOrdersPhase = (IssueOrdersPhase) this.getPhase();

						l_issueOrdersPhase.issueOrders(d_scanner);
						continue;
					}

					if (this.getPhase().getClass().equals(new ExecuteOrdersPhase(this).getClass())) {
						ExecuteOrdersPhase l_executeOrdersPhase = (ExecuteOrdersPhase) this.getPhase();

						l_executeOrdersPhase.executeAllOrders();
						continue;
					}

					if (this.getPhase().getClass().equals(new EndPhase(this).getClass())) {
						EndPhase l_endPhase = (EndPhase) this.getPhase();

						l_endPhase.checkForWinner();
						l_isWinner = l_endPhase.getAnyWinner();
						if (l_isWinner) {
							break;
						}
					}
					incrementRoundNumber();
				} while (getRoundNumber() <= p_maxNumberOfTurns);

				if (l_isWinner) {
					l_results[p_mapFiles.indexOf(l_mapFile)][getGameNumber()] = findWinner().getPlayerName();
				} else {
					l_results[p_mapFiles.indexOf(l_mapFile)][getGameNumber()] = "Draw";
				}
			}
		}
	}

	public Player findWinner() {
		return this.getPlayers().get(0); // only one player left when there is a winner
	}

	/**
	 * Validates tournament command arguments and throws exceptions accordingly
	 * 
	 * @param l_mapFiles         list of map files provided by the user
	 * @param l_playerStrategies list of player strategies provided by the user
	 * @param l_numberOfGames    number of games to be played per map provided by
	 *                           the user
	 * @param l_maxNumberOfTurns max number of turns provided by the user
	 */
	private void validateArgumentsForTournmanetCommand(List<String> l_mapFiles, List<String> l_playerStrategies,
			int l_numberOfGames, int l_maxNumberOfTurns) {
		if (l_mapFiles.size() < 1 || l_mapFiles.size() > 5) {

			throw new IllegalArgumentException("Invalid input: Please provide between 1 and 5 maps.");

		} else if (l_playerStrategies.size() < 2 || l_playerStrategies.size() > 4) {

			throw new IllegalArgumentException("Invalid input: Please provide between 2 and 4 player strategies.");

		} else if (l_numberOfGames < 1 || l_numberOfGames > 5) {

			throw new IllegalArgumentException(
					"Invalid input: Please input a number between 1 and 5 for the number of games you can play on each map.");

		} else if (l_maxNumberOfTurns < 10 || l_maxNumberOfTurns > 50) {

			throw new IllegalArgumentException(
					"Invalid input: Please input a number between 10 and 50 for the maximum number of turns.");
		}
	}

	/**
	 * Parses the 'editcontinent' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseEditContinentCommand(String[] p_tokens) {
		if (p_tokens.length < 3) {
			System.out.println(
					"Invalid command. Syntax: editcontinent -add continentId continentvalue -remove continentId");
			return;
		}

		String l_option = "";
		String l_continentName = "";
		String l_continentValue = "";

		for (int i = 1; i < p_tokens.length; i++) {
			try {
				if (p_tokens[i].startsWith("-")) {
					l_option = p_tokens[i].toLowerCase();
				} else {
					switch (l_option) {
					case "-add":
						// Validation - there should be at least 2 tokens after add and Validation -
						// parameter should not start with "-"
						if (i + 1 < p_tokens.length && !p_tokens[i].startsWith("-")
								&& !p_tokens[i + 1].startsWith("-")) {
							l_continentName = p_tokens[i];
							l_continentValue = p_tokens[++i];
							addContinentAndLog(l_continentName, l_continentValue);
						}
						break;
					case "-remove":
						if (!p_tokens[i].startsWith("-")) {
							l_continentName = p_tokens[i];
							removeContinentAndLog(l_continentName);
						}
						break;
					default:
						throw new IllegalArgumentException(
								"Invalid option for editcontinent command. Use -add or -remove.");
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	/**
	 * Parses the 'editcountry' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseEditCountryCommand(String[] p_tokens) {
		if (p_tokens.length < 2) {
			System.out.println("Invalid command. Syntax: editcountry -add countryId continentId -remove countryId");
			return;
		}

		String l_option = "";
		String l_countryId = "";
		String l_continentId = "";

		for (int i = 1; i < p_tokens.length; i++) {
			try {
				if (p_tokens[i].startsWith("-")) {
					l_option = p_tokens[i].toLowerCase();
				} else {
					switch (l_option) {
					case "-add":
						// Validation - there should be at least 2 tokens after add and Validation -
						// parameter should not start with "-"
						if (i + 1 < p_tokens.length && !p_tokens[i].startsWith("-")
								&& !p_tokens[i + 1].startsWith("-")) {
							l_countryId = p_tokens[i];
							l_continentId = p_tokens[++i];

							try {
								d_gamePhase.addCountry(l_countryId, l_continentId);
								d_logEntryBuffer.setD_effectOfAction("Country " + l_countryId + " was added.");
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
						break;
					case "-remove":
						if (!p_tokens[i].startsWith("-")) {
							l_countryId = p_tokens[i];

							try {
								d_gamePhase.removeCountry(l_countryId);
								d_logEntryBuffer.setD_effectOfAction("Country " + l_countryId + " was removed.");
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
						break;
					default:
						throw new IllegalArgumentException(
								"Invalid option for editcountry command. Use -add or -remove.");
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Parses the 'editneighbor' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseEditNeighborCommand(String[] p_tokens) {
		if (p_tokens.length < 2) {
			System.out.println(
					"Invalid command. Syntax: editneighbor -add countryID neighborcountryId -remove countryId neighborcountryId");
			return;
		}

		String l_option = "";
		String l_countryId = "";
		String l_neighborCountryId = "";

		for (int i = 1; i < p_tokens.length; i++) {
			try {
				if (p_tokens[i].startsWith("-")) {
					l_option = p_tokens[i].toLowerCase();
				} else {
					switch (l_option) {
					case "-add":
						// Validation - there should be at least 2 tokens after add
						if (i + 1 < p_tokens.length) {

							// Validation - parameter should not start with "-"
							if (!p_tokens[i].startsWith("-") && !p_tokens[i + 1].startsWith("-")) {
								l_countryId = p_tokens[i];
								l_neighborCountryId = p_tokens[++i];

								try {
									d_gamePhase.addNeighbor(l_countryId, l_neighborCountryId);
									d_logEntryBuffer.setD_effectOfAction(
											l_neighborCountryId + " was added as a neighbor to " + l_countryId);
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
							}
						}
						break;
					case "-remove":
						// Validation - there should be at least 2 tokens after add
						if (i + 1 < p_tokens.length) {

							// Validation - parameter should not start with "-"
							if (!p_tokens[i].startsWith("-") && !p_tokens[i + 1].startsWith("-")) {
								l_countryId = p_tokens[i];
								l_neighborCountryId = p_tokens[++i];

								try {
									d_gamePhase.removeNeighbor(l_countryId, l_neighborCountryId);
									d_logEntryBuffer.setD_effectOfAction(
											l_neighborCountryId + " was removed as a neighbor of " + l_countryId);
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
							}
						}
						break;
					default:
						throw new IllegalArgumentException(
								"Invalid option for editneighbor command. Use -add or -remove.");
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Parses the 'showmap' command.
	 */
	private void parseShowMapCommand() {
		d_gamePhase.showMap();
		d_logEntryBuffer.setD_effectOfAction("The map was shown.");
	}

	/**
	 * Parses the 'savemap' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseSaveMapCommand(String[] p_tokens) {
		if (p_tokens.length != 2) {
			System.out.println("Invalid command. Syntax: savemap filename");
		} else {
			String l_filename = Common.getMapPath(p_tokens[1]);

			d_gamePhase.saveMap(l_filename);
			d_logEntryBuffer.setD_effectOfAction(l_filename + " file was saved.");
		}
	}

	/**
	 * Parses the 'editmap' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseEditMapCommand(String[] p_tokens) {
		if (p_tokens.length != 2) {
			System.out.println("Invalid command. Syntax: editmap filename");
		} else {
			String l_filename = p_tokens[1];

			d_gamePhase.editMap(Common.getMapPath(l_filename));
			d_logEntryBuffer.setD_effectOfAction(l_filename + " file was edited.");
		}
	}

	/**
	 * Parses the 'validatemap' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseValidateMapCommand(String[] p_tokens) {
		d_gamePhase.validateMap();
		d_logEntryBuffer.setD_effectOfAction("The map was validated.");
	}

	/**
	 * Parses the 'gameplayer' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseGamePlayerCommand(String[] p_tokens) {
		if (p_tokens.length < 3) {
			System.out.println("Invalid command. Syntax: gameplayer -add playername -remove playername");
			return;
		}

		String l_option = "";
		String l_playerName = "";
		PlayerStrategy l_playerStrategy = null;

		for (int i = 1; i < p_tokens.length; i++) {
			if (p_tokens[i].startsWith("-")) {
				l_option = p_tokens[i].toLowerCase();
			} else {
				switch (l_option) {
				case "-add":
					if (!p_tokens[i].startsWith("-")) {
						l_playerName = p_tokens[i];
						l_playerStrategy = choosePlayerBehavior(l_playerName);

						d_gamePhase.addPlayers(l_playerName, l_playerStrategy);
						d_logEntryBuffer.setD_effectOfAction(l_playerName + " was added as a player.");
					}
					break;
				case "-remove":
					if (!p_tokens[i].startsWith("-")) {
						l_playerName = p_tokens[i];
						d_gamePhase.removePlayers(l_playerName);
						d_logEntryBuffer.setD_effectOfAction(l_playerName + " was removed from players.");
					}
					break;
				default:
					System.out.println("Invalid option for gameplayer command. Use -add or -remove.");
				}
			}
		}
	}

	private PlayerStrategy choosePlayerBehavior(String l_playerName) {

		System.out.println("a. Human");
		System.out.println("b. Aggressive");
		System.out.println("c. Benevolent");
		System.out.println("d. Random");
		System.out.println("e. Cheater");

		PlayerStrategy l_playerStrategy = null;
		boolean l_isInvalidChoice;

		do {
			System.out.print("Choose player type for " + l_playerName + ": ");
			String choice = d_scanner.nextLine();
			l_isInvalidChoice = false;

			switch (choice) {
			case "a":
				l_playerStrategy = new HumanPlayerStrategy();
				break;
			case "b":
				l_playerStrategy = new AggressivePlayerStrategy();
				break;
			case "c":
				l_playerStrategy = new BenevolentPlayerStrategy();
				break;
			case "d":
				// TODO:
				// l_playerStrategy = new RandomPlayerStrategy();
				break;
			case "e":
				l_playerStrategy = new CheaterPlayerStrategy();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				l_isInvalidChoice = true;
				break;
			}
		} while (l_isInvalidChoice);

		return l_playerStrategy;
	}

	/**
	 * Parses the 'assigncountries' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseAssignCountriesCommand(String[] p_tokens) {
		d_gamePhase.assignCountries();
		d_logEntryBuffer.setD_effectOfAction("Countries have been assigned to players.");
	}

	/**
	 * Parses the 'loadmap' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseLoadMapCommand(String[] p_tokens) {
		if (p_tokens.length != 2) {
			System.out.println("Invalid command. Syntax: loadmap filename");
		} else {
			String l_filename = Common.getMapPath(p_tokens[1]);
			d_gamePhase.loadMap(l_filename);
			d_logEntryBuffer.setD_effectOfAction(l_filename + " map file was loaded.");
		}
	}

	/**
	 * This class check if a player made a deal with another player then the attack
	 * is not allowed
	 *
	 * @param p_player            The attacking player.
	 * @param p_targetCountryName accept the country name which player want to
	 *                            attack
	 * @return the boolean value true if attack is allowed
	 */
	public boolean checkAttackAllowed(Player p_player, String p_targetCountryName) {
		for (Player p : p_player.getD_negotiatedWith()) {
			if (p.getOwnership().contains(p_targetCountryName)) {
				System.err.println("The negotiated player cannot be attacked for this turn");
				return false;
			}
		}
		return true;
	}
}
