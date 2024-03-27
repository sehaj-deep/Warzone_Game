package game;

import java.util.HashMap;
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
import phases.PlaySetup;
import phases.Preload;
import phases.ReinforcePhase;
import players.Player;
import utils.Common;

/**
 * GameEngine Class This Class runs the game by integrating all the functions
 * and classes needed for Warzone
 */
public class GameEngine {

	/**
	 * Buffer to store log entries.
	 */
	private LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

	/**
	 * Object to write log entries to a file.
	 */
	private LogFileWriter d_logFileWriter = new LogFileWriter(d_logEntryBuffer);

	/**
	 * Current state of the game.
	 */
	private GameState d_gameState = new GameState();

	/**
	 * Map to store the validity of orders for each player.
	 */
	private Map<Player, Boolean> d_validOrder = new HashMap<>();

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
	public HashMap<String, Continent> getD_continents() {
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
	 * Gets the current game state.
	 * 
	 * @return The current game state.
	 */
	public GameState getGameState() {
		return d_gameState;
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
	 * Starts the game engine and manages the game phases.
	 */
	public void start() {
		try (Scanner l_scanner = new Scanner(System.in)) {
			System.out.println("1. Edit Map");
			System.out.println("2. Start Game");
			System.out.print("Enter your choice: ");
			String choice = l_scanner.nextLine();

			switch (choice) {
			case "1":
				setPhase(new Preload(this));
				break;
			case "2":
				setPhase(new PlaySetup(this));
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}

			String l_command = "";

			while (this.getPhase().getClass().equals(new PlaySetup(this).getClass())) {
				System.out.print("\n> Enter a command: ");
				l_command = l_scanner.nextLine();
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

					l_issueOrdersPhase.issueOrders(l_scanner);
					continue;
				}

				if (this.getPhase().getClass().equals(new ExecuteOrdersPhase(this).getClass())) {
					ExecuteOrdersPhase l_executeOrdersPhase = (ExecuteOrdersPhase) this.getPhase();

					l_executeOrdersPhase.executeAllOrders();
					continue;
				}
				if (this.getPhase().getClass().equals(new EndPhase(this).getClass())) {
					EndPhase l_endPhase = (EndPhase) this.getPhase();
					l_endPhase.end();
					if (l_endPhase.getAnyWinner()) {
						break;
					}

					continue;
				}
				System.out.println("Enter a command: ");
				l_command = l_scanner.nextLine();

				parseUserCommand(l_command);
			} while (l_command.toLowerCase() != "done");
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
			parseShowMapCommand(l_tokens);
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
		default:
			System.out.println("Invalid command. Please try again.");
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
						// Validation - there should be at least 2 tokens after add
						if (i + 1 < p_tokens.length) {

							// Validation - parameter should not start with "-"
							if (!p_tokens[i].startsWith("-") && !p_tokens[i + 1].startsWith("-")) {
								l_continentName = p_tokens[i];
								l_continentValue = p_tokens[++i];

								try {
									d_gamePhase.addContinent(l_continentName, Integer.parseInt(l_continentValue));
									d_logEntryBuffer
											.setD_effectOfAction("Continent " + l_continentName + " was added.");
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
							}
						}
						break;
					case "-remove":
						if (!p_tokens[i].startsWith("-")) {
							l_continentName = p_tokens[i];

							try {
								d_gamePhase.removeContinent(l_continentName);
								d_logEntryBuffer.setD_effectOfAction("Continent " + l_continentName + " was removed.");
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
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
						// Validation - there should be at least 2 tokens after add
						if (i + 1 < p_tokens.length) {

							// Validation - parameter should not start with "-"
							if (!p_tokens[i].startsWith("-") && !p_tokens[i + 1].startsWith("-")) {
								l_countryId = p_tokens[i];
								l_continentId = p_tokens[++i];

								try {
									d_gamePhase.addCountry(l_countryId, l_continentId);
									d_logEntryBuffer.setD_effectOfAction("Country " + l_countryId + " was added.");
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
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
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseShowMapCommand(String[] p_tokens) {
		// TODO: Check two diff phases that use this
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

		for (int i = 1; i < p_tokens.length; i++) {
			if (p_tokens[i].startsWith("-")) {
				l_option = p_tokens[i].toLowerCase();
			} else {
				switch (l_option) {
				case "-add":
					if (!p_tokens[i].startsWith("-")) {
						l_playerName = p_tokens[i];
						d_gamePhase.addPlayers(l_playerName);
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
	 * @param p_player The attacking player.
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
