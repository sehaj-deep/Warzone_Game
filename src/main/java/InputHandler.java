import java.util.Scanner;

/**
 * InputHandler class responsible for handling user input commands.
 */
import Phases.StarterPhase;

public class InputHandler {

    // TODO
    // private MapEditor mapEditor;
    // private GameEngine gameEngine;
    //
    public InputHandler(/* MapEditor mapEditor, GameEngine gameEngine */) {
        // this.mapEditor = mapEditor;
        // this.gameEngine = gameEngine;
    }

    /**
     * Parses user input commands.
     */
    public void parseUserCommand() {
        System.out.print("Enter command: ");

        Scanner l_scanner = new Scanner(System.in);

        try {
            String l_userInput = l_scanner.nextLine().trim();
            String[] l_tokens = l_userInput.split("\\s+");

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
        } finally {
            l_scanner.close();
        }
    }

    /**
     * Parses the 'editcontinent' command.
     *
     * @param tokens Command tokens.
     */
    private static void parseEditContinentCommand(String[] p_tokens) {
        if (p_tokens.length < 2) {
            System.out.println(
                    "Invalid command. Syntax: editcontinent -add continentId continentvalue -remove continentId");
            return;
        }

        String l_option = "";
        String l_continentId = "";
        String l_continentValue = "";

        for (int i = 1; i < p_tokens.length; i++) {
            if (p_tokens[i].startsWith("-")) {
                l_option = p_tokens[i].toLowerCase();
            } else {
                switch (l_option) {
                    case "-add":
                        l_continentId = p_tokens[i];
                        l_continentValue = p_tokens[++i];
                        // TODO: Implement logic to handle adding continent with ID and value
                        // MapEditor.AddContinent(continentId, continentValue);
                        break;
                    case "-remove":
                        l_continentId = p_tokens[i];
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
    private static void parseEditCountryCommand(String[] p_tokens) {
        if (p_tokens.length < 2) {
            System.out.println("Invalid command. Syntax: editcountry -add countryId continentId -remove countryId");
            return;
        }

        String l_option = "";
        String l_countryId = "";
        String l_continentId = "";

        for (int i = 1; i < p_tokens.length; i++) {
            if (p_tokens[i].startsWith("-")) {
                l_option = p_tokens[i].toLowerCase();
            } else {
                switch (l_option) {
                    case "-add":
                        l_countryId = p_tokens[i];
                        l_continentId = p_tokens[++i];
                        // TODO: Implement logic to handle adding country with ID and continent ID
                        // MapEditor.addCountry(countryId, continentId);
                        break;
                    case "-remove":
                        l_countryId = p_tokens[i];
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
    private static void parseEditNeighborCommand(String[] p_tokens) {
        if (p_tokens.length < 2) {
            System.out.println(
                    "Invalid command. Syntax: editneighbor -add countryID neighborcountryId -remove countryId neighborcountryId");
            return;
        }

        String l_option = "";
        String l_countryId = "";
        String l_neighborCountryId = "";

        for (int i = 1; i < p_tokens.length; i++) {
            if (p_tokens[i].startsWith("-")) {
                l_option = p_tokens[i].toLowerCase();
            } else {
                switch (l_option) {
                    case "-add":
                        l_countryId = p_tokens[i];
                        l_neighborCountryId = p_tokens[++i];
                        // TODO: Implement logic to handle adding neighbor country
                        // MapEditor.AddNeighbor(countryId, neighborCountryId);
                        break;
                    case "-remove":
                        l_countryId = p_tokens[i];
                        l_neighborCountryId = p_tokens[++i];
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
    private static void parseShowMapCommand(String[] p_tokens) {
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
    private static void parseSaveMapCommand(String[] p_tokens) {
        if (p_tokens.length != 2) {
            System.out.println("Invalid command. Syntax: savemap filename");
        } else {
            String l_filename = p_tokens[1];
            // TODO: Implement logic to save the map with the specified filename
            // MapEditor.saveMap(filename);
        }
    }

    /**
     * Parses the 'editmap' command.
     *
     * @param tokens Command tokens.
     */
    private static void parseEditMapCommand(String[] p_tokens) {
        if (p_tokens.length != 2) {
            System.out.println("Invalid command. Syntax: editmap filename");
        } else {
            String l_filename = p_tokens[1];
            // TODO: Implement logic to edit the map with the specified filename
            // MapEditor.editMap(filename);
        }
    }

    /**
     * Parses the 'validatemap' command.
     *
     * @param tokens Command tokens.
     */
    private static void parseValidateMapCommand(String[] p_tokens) {
        // TODO
        // MapEditor.validateMap();
    }

    /**
     * Parses the 'gameplayer' command.
     *
     * @param tokens Command tokens.
     */
    private static void parseGamePlayerCommand(String[] p_tokens) {
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
                        l_playerName = p_tokens[i];
                        StarterPhase.addPlayer(l_playerName);
                        break;
                    case "-remove":
                        l_playerName = p_tokens[i];
                        StarterPhase.removePlayer(l_playerName);
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
    private static void parseAssignCountriesCommand(String[] p_tokens) {
        // TODO
        // MapEditor.assignCountries();
    }

    /**
     * Parses the 'loadmap' command.
     *
     * @param tokens Command tokens.
     */
    private static void parseLoadMapCommand(String[] p_tokens) {
        if (p_tokens.length != 2) {
            System.out.println("Invalid command. Syntax: loadmap filename");
        } else {
            String l_filename = p_tokens[1];
            // TODO
            // MapEditor.loadMap(filename);
        }
    }
}