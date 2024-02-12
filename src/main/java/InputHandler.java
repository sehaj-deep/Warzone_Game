import java.util.Scanner;

/**
 * InputHandler class responsible for handling user input commands.
 */
public class InputHandler {

	/**
	 * Parses user input commands.
	 */
	public void parseUserCommand() {
		System.out.print("Enter command: ");

		Scanner scanner = new Scanner(System.in);

		try {
			String command = scanner.nextLine().trim();
			String[] tokens = command.split("\\s+");

			String cmd = tokens[0].toLowerCase();

			switch (cmd) {
			case "editcontinent":
				parseEditContinentCommand(tokens);
				break;
			case "editcountry":
				parseEditCountryCommand(tokens);
				break;
			case "editneighbor":
				parseEditNeighborCommand(tokens);
				break;
			case "showmap":
				parseShowMapCommand(tokens);
				break;
			case "savemap":
				parseSaveMapCommand(tokens);
				break;
			case "editmap":
				parseEditMapCommand(tokens);
				break;
			case "validatemap":
				parseValidateMapCommand(tokens);
				break;
			case "gameplayer":
				parseGamePlayerCommand(tokens);
				break;
			case "assigncountries":
				parseAssignCountriesCommand(tokens);
				break;
			case "loadmap":
				parseLoadMapCommand(tokens);
				break;
			default:
				System.out.println("Invalid command. Please try again.");
			}
		} finally {
			scanner.close(); // Ensure the Scanner is closed
		}
	}

	/**
	 * Parses the 'editcontinent' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseEditContinentCommand(String[] tokens) {
		if (tokens.length < 2) {
			System.out.println(
					"Invalid command. Syntax: editcontinent -add continentID continentvalue -remove continentID");
			return;
		}

		String option = "";
		String continentID = "";
		String continentValue = "";

		for (int i = 1; i < tokens.length; i++) {
			if (tokens[i].startsWith("-")) {
				option = tokens[i].toLowerCase();
			} else {
				switch (option) {
				case "-add":
					continentID = tokens[i];
					continentValue = tokens[++i];
					// TODO: Implement logic to handle adding continent with ID and value
					// MapEditor.AddContinent(continentId, continentValue);
					break;
				case "-remove":
					continentID = tokens[i];
					// TODO: Implement logic to handle removing continent by ID
					// MapEditor.RemoveContinent(continentId);
					break;
				default:
					System.out.println("Invalid option for editcontinent command. Use -add or -remove.");
				}
			}
		}
	}

	/**
	 * Parses the 'editcountry' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseEditCountryCommand(String[] tokens) {
		if (tokens.length < 2) {
			System.out.println("Invalid command. Syntax: editcountry -add countryID continentID -remove countryID");
			return;
		}

		String option = "";
		String countryID = "";
		String continentID = "";

		for (int i = 1; i < tokens.length; i++) {
			if (tokens[i].startsWith("-")) {
				option = tokens[i].toLowerCase();
			} else {
				switch (option) {
				case "-add":
					countryID = tokens[i];
					continentID = tokens[++i];
					// TODO: Implement logic to handle adding country with ID and continent ID
					// MapEditor.addCountry(countryId, continentId);
					break;
				case "-remove":
					countryID = tokens[i];
					// TODO: Implement logic to handle removing country by ID
					// MapEditor.RemoveCountry(countryId);
					break;
				default:
					System.out.println("Invalid option for editcountry command. Use -add or -remove.");
				}
			}
		}
	}

	/**
	 * Parses the 'editneighbor' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseEditNeighborCommand(String[] tokens) {
		if (tokens.length < 2) {
			System.out.println(
					"Invalid command. Syntax: editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
			return;
		}

		String option = "";
		String countryID = "";
		String neighborCountryID = "";

		for (int i = 1; i < tokens.length; i++) {
			if (tokens[i].startsWith("-")) {
				option = tokens[i].toLowerCase();
			} else {
				switch (option) {
				case "-add":
					countryID = tokens[i];
					neighborCountryID = tokens[++i];
					// TODO: Implement logic to handle adding neighbor country
					// MapEditor.AddNeighbor(countryId, neighborCountryId);
					break;
				case "-remove":
					countryID = tokens[i];
					neighborCountryID = tokens[++i];
					// TODO: Implement logic to handle removing neighbor country
					// MapEditor.RemoveNeighbor(countryId, neighborCOuntryId);
					break;
				default:
					System.out.println("Invalid option for editneighbor command. Use -add or -remove.");
				}
			}
		}
	}

	/**
	 * Parses the 'showmap' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseShowMapCommand(String[] tokens) {
		// TODO
		// if(currentPhase == MAP_EDITING_PHASE){
		// MapEditor.showMap();
		// } else {
		// GameEngine.showMap();
		// }
	}

	/**
	 * Parses the 'savemap' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseSaveMapCommand(String[] tokens) {
		if (tokens.length != 2) {
			System.out.println("Invalid command. Syntax: savemap filename");
		} else {
			String filename = tokens[1];
			// TODO: Implement logic to save the map with the specified filename
			// MapEditor.saveMap(filename);
		}
	}

	/**
	 * Parses the 'editmap' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseEditMapCommand(String[] tokens) {
		if (tokens.length != 2) {
			System.out.println("Invalid command. Syntax: editmap filename");
		} else {
			String filename = tokens[1];
			// TODO: Implement logic to edit the map with the specified filename
			// MapEditor.editMap(filename);
		}
	}

	/**
	 * Parses the 'validatemap' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseValidateMapCommand(String[] tokens) {
		// TODO
		// MapEditor.validateMap();
	}

	/**
	 * Parses the 'gameplayer' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseGamePlayerCommand(String[] tokens) {
		if (tokens.length < 3) {
			System.out.println("Invalid command. Syntax: gameplayer -add playername -remove playername");

			return;
		}
		String option = "";
		String playerName = "";

		for (int i = 1; i < tokens.length; i++) {
			if (tokens[i].startsWith("-")) {
				option = tokens[i].toLowerCase();
			} else {
				switch (option) {
				case "-add":
					playerName = tokens[i];
					// TODO
					// GameEngine.addPlayer(playerName);
					break;
				case "-remove":
					playerName = tokens[i];
					// TODO
					// GameEngine.removePlayer(playerName);
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
	 * @param tokens Command tokens.
	 */
	private static void parseAssignCountriesCommand(String[] tokens) {
		// TODO
		// MapEditor.assignCountries();
	}

	/**
	 * Parses the 'loadmap' command.
	 * 
	 * @param tokens Command tokens.
	 */
	private static void parseLoadMapCommand(String[] tokens) {
		if (tokens.length != 2) {
			System.out.println("Invalid command. Syntax: loadmap filename");
		} else {
			String filename = tokens[1];
			// TODO
			// MapEditor.loadMap(filename);
		}
	}
}
