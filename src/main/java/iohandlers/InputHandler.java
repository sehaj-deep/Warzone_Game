package iohandlers;

import game.GameState;
import map.MapEditor;
import utils.Common;

/**
 * InputHandler class is responsible for handling user input commands.
 */
public class InputHandler {

	private MapEditor d_mapEditor = null;
	private static GameState d_gameState = null;

	/**
	 * Parameterized Constructor For MapEditor Phase
	 * 
	 * @param p_mapEditor the object of MapEditor
	 */
	public InputHandler(MapEditor p_mapEditor) {
		d_mapEditor = p_mapEditor;
	}

	public InputHandler(MapEditor p_mapEditor, GameState p_gameState) {
		d_mapEditor = p_mapEditor;
		d_gameState = p_gameState;
	}

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
		// TODO
		// if(currentPhase == MAP_EDITING_PHASE){
		d_mapEditor.showMap();
		// } else {
		// GameEngine.showMap();
		// }
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
			String l_filename = p_tokens[1];
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
					if (i + 1 < p_tokens.length) {
						if (!p_tokens[i + 1].startsWith("-")) {
							l_playerName = p_tokens[++i];
							// TODO
							// GameEngine.addPlayer(playerName);
						}
					}
					break;
				case "-remove":
					if (i + 1 < p_tokens.length) {
						if (!p_tokens[i + 1].startsWith("-")) {
							l_playerName = p_tokens[++i];
							// TODO
							// GameEngine.removePlayer(playerName);
						}
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
		// MapEditor.assignCountries();
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
			String l_filename = p_tokens[1];
			// TODO
			// MapEditor.loadMap(filename);
		}
	}
}