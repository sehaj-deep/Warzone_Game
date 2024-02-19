package iohandlers;

import java.util.regex.Pattern;

import game.GameState;
import map.MapEditor;
import phases.StarterPhase;
import utils.Common;

/**
 * InputHandler class is responsible for handling user input commands.
 */
public class InputHandler {

	private MapEditor d_mapEditor = null;
	private GameState d_gameState = null;
	StarterPhase d_startPhase = new StarterPhase();

	/**
	 * Parameterized Constructor For MapEditor Phase
	 * 
	 * @param p_mapEditor the object of MapEditor
	 */
	public InputHandler(MapEditor p_mapEditor) {
		d_mapEditor = p_mapEditor;
	}

	/**
	 * Parameterized Constructor For GameState Phase
	 * 
	 * @param p_mapEditor the object of MapEditor
	 * @param p_gameState the object of GameState
	 */
	public InputHandler(MapEditor p_mapEditor, GameState p_gameState) {
		d_mapEditor = p_mapEditor;
		d_gameState = p_gameState;
	}

	/**
	 * Default constructor
	 */
	public InputHandler() {

	}

	/**
	 * Parses user input commands.
	 */
	public void parseUserCommand(String p_userInput) {

		String[] l_tokens = p_userInput.split("\\s+");
		String l_command = l_tokens[0].toLowerCase();

		// FIXME Replace exception with custom exception
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
									d_mapEditor.addContinent(l_continentName, Integer.parseInt(l_continentValue));
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
								d_mapEditor.removeContinent(l_continentName);
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
									d_mapEditor.addCountry(l_countryId, l_continentId);
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
								d_mapEditor.removeCountry(l_countryId);
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
									d_mapEditor.addNeighbor(l_countryId, l_neighborCountryId);
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
									d_mapEditor.removeNeighbor(l_countryId, l_neighborCountryId);
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
		if (d_gameState == null) {
			d_mapEditor.showMap();
		} else {
			d_mapEditor.showmap(d_gameState);
		}
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
			// TODO: Implement logic to save the map with the specified filename
			// d_mapEditor.saveMap(filename);
			d_mapEditor.saveMap(d_mapEditor, l_filename);

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
			d_mapEditor.editMap(d_mapEditor, Common.getMapPath(l_filename));
		}

	}

	/**
	 * Parses the 'validatemap' command.
	 * 
	 * @param p_tokens Command tokens.
	 */
	private void parseValidateMapCommand(String[] p_tokens) {
		d_mapEditor.validateMap();
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
						d_startPhase.addPlayer(l_playerName, d_gameState);
					}
					break;
				case "-remove":
					if (!p_tokens[i].startsWith("-")) {
						l_playerName = p_tokens[i];
						d_startPhase.removePlayer(l_playerName, d_gameState);
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
		// TODO
		StarterPhase l_startPhase = new StarterPhase();
		d_startPhase.assignCountriesToPlayer(d_gameState, d_mapEditor);
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
			d_mapEditor.loadMap(d_mapEditor, l_filename);
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
		/*
		 * if (!d_gameMap.getCountries().containsKey(p_tokens[1])) {
		 * System.out.println("Invalid command for Deploy order." +
		 * "Country name must be the name of existing country in the map"); return; }
		 */
		d_gameState.setOrderInput(p_tokens);
	}
}