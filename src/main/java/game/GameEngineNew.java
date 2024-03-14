package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import map.Preload;
import models.Continent;
import models.Country;
import phases.Phase;
import phases.PlaySetup;
import utils.Common;
import utils.LogEntryBuffer;
import utils.LogFileWriter;

public class GameEngineNew {

	private LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
	private LogFileWriter d_logFileWriter = new LogFileWriter(d_logEntryBuffer);

	private static List<Player> d_players = new ArrayList<>();

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

	protected List<Player> d_players; // list of all players in the game

	/**
	 * getter function for a list of players playing the game
	 *
	 * @return d_players a list of players playing the game
	 */
	public List<Player> getD_players() {
		return d_players;
	}

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
		d_logEntryBuffer.setD_currentPhase("Continent " + p_phase + " was added.");
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
	 * 
	 * @return the name of the country corresponding to the id.
	 */
	public HashMap<Integer, Country> getD_countriesId() {
		return d_countriesId;
	}

	/**
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

			String command;
			// remove this
			int i = 0;
			while (true) { // to iterate over main game - find end condition
				// FIXME
				if (i == 5) {
					break;
				}

				// TODO:create a boolean List to indicate that you are finished - initialise as
				// well
				// orders.
				// d_currentPhase.reinforce() - before issue orders
//				d_curentPhase.next();
				do { // for issue orders phase
						// TODO: If time permits, every time we set a new phase
						// we should sysout the valid commands to the user
						// (do this in every Phases' constructor?)

					// TODO: loop over each player leaving those who do not have valid orders

					for (Player p : d_players) {
						String userInput = l_scanner.nextLine();
						parseUserCommand(userInput);

						// continue if not valid - keep a boolean list to maintain this

					}
					System.out.println("Enter a command: ");
					command = l_scanner.nextLine();
					parseUserCommand(command);
				} while (command.toLowerCase() != "done");
//				d_curentPhase.next();	//to reach execute phase
//				d_curentPhase.execute();
//				d_curentPhase.next();	// for reinforcement

			}

		}
	}

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
		case "deploy":
			parseDeployCommand(l_tokens);
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
						// TODO Add/Modify this method in the appropriate phase classes
						// d_gamePhase.addPlayer(l_playerName, d_gameState);
						d_logEntryBuffer.setD_effectOfAction(l_playerName + " was added as a player.");
					}
					break;
				case "-remove":
					if (!p_tokens[i].startsWith("-")) {
						l_playerName = p_tokens[i];
						// TODO Add/Modify this method in the appropriate phase classes
						// d_gamePhase.removePlayer(l_playerName, d_gameState);
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
		// TODO Add/Modify this method in the appropriate phase classes
		// d_gamePhase.assignCountriesToPlayer(d_gameState, d_mapEditor);
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
			// TODO Add/Modify this method in the appropriate phase classes
			// d_gamePhase.loadMap(d_mapEditor, l_filename);
			d_logEntryBuffer.setD_effectOfAction(l_filename + " map file was loaded.");
		}
	}

	/**
	 * Parse the Deploy command from the user command in terminal Store the user
	 * input tokens in the game state so that Phase classes can access the inputs
	 * 
	 * @param p_tokens an array of tokens given in the user input command
	 */
	private void parseDeployCommand(String[] p_tokens) {
		Pattern numericRegex = Pattern.compile("\\d+");
		if (p_tokens.length != 3) {
			System.out.println("Invalid command for Deploy order. Syntax must be: deploy countryName numberArmy");
			return;
		}
		if (!numericRegex.matcher(p_tokens[2]).matches()) {
			System.out.println("Invalid command for Deploy order. Number of army must be positive integer");
			return;
		}

		// TODO Add/Modify this method in the appropriate phase classes
		// NOTE: This method might need to be refactored
//		d_gamePhase.setOrderInput(p_tokens);	//pass it to issue orders phase
		d_logEntryBuffer.setD_effectOfAction("");
	}
}
