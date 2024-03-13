package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import iohandlers.InputHandler;
import map.MapEditor;
import phases.IssueOrdersPhase;
import utils.LogEntryBuffer;
import utils.LogFileWriter;

/**
 * GameEngine Class This Class runs a game by integrating all the functions and
 * classes needed for Warzone
 */
public class GameEngine {
	private static LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
	private static LogFileWriter d_logFileWriter = new LogFileWriter(d_logEntryBuffer);

	private static MapEditor d_mapEditor = new MapEditor();
	private static InputHandler d_inputHandler = new InputHandler(d_mapEditor);

	private static List<Player> d_players = new ArrayList<>(); // list of players playing the game
	private static HashMap<String, Integer> d_board = new HashMap<String, Integer>(); // key: country name. val: num
	// army in
	private static GameState d_state = new GameState(d_players); // the country

	/**
	 * Main method to start the game engine
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {

		Scanner l_scanner = new Scanner(System.in);

		boolean l_editMapPhase = false;
		boolean l_gamePhase = false;
		String l_userInput = "";

		while (!l_editMapPhase && !l_gamePhase) {
			System.out.println("Do you want to edit the map or start playing the game?");
			System.out.println("1. Edit Map");
			System.out.println("2. Start Game");
			System.out.print("Enter your choice: ");
			String choice = l_scanner.nextLine();

			switch (choice) {
			case "1":
				l_editMapPhase = true;
				d_logEntryBuffer.setD_currentPhase("Map Editing Phase");
				break;
			case "2":
				l_gamePhase = true;
				d_logEntryBuffer.setD_currentPhase("Game Phase");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}

		// Map editing phase
		if (l_editMapPhase) {
			System.out.println("You are now in the map editing phase.");
			System.out.println("To edit the map or create a new map, use the following command:");
			System.out.println("editmap filename");

			l_userInput = l_scanner.nextLine();

			while (!l_userInput.startsWith("editmap")) {
				System.out
						.println("Invalid Command entered. You need to give the name of file using \"editmap filename\" command.");
				l_userInput = l_scanner.nextLine();
			}

			d_inputHandler.parseUserCommand(l_userInput);

			System.out.println("Available commands: editcontinent, editcountry, editneighbor, showmap, savemap, validatemap");

			boolean l_editingMap = true;

			while (l_editingMap) {
				System.out.print("Enter map editing command (type 'done' to finish): ");
				l_userInput = l_scanner.nextLine().trim();

				if (l_userInput.equalsIgnoreCase("done")) {
					l_editingMap = false;
				}
				else {
					d_inputHandler.parseUserCommand(l_userInput);
				}
			}
		}

		// Game playing phase
		if (l_gamePhase) {
			InputHandler l_inputHandlerGame = new InputHandler(d_mapEditor, d_state);
			System.out.println("You are now in the game playing phase.");

			while (true) {
				// Loadmap. To ensure that the user loads the map before playing the game
				System.out.println("Please load a map where you want to play a game");
				l_userInput = l_scanner.nextLine().trim();
				l_inputHandlerGame.parseUserCommand(l_userInput);
				if (d_mapEditor.getD_countries().size() > 0) {
					break;
				}
				System.out.println("Map is not loaded. Please load a map before playing a game");
			}

			initalizeBoard(); // initialize the game board
			d_state.setGameBoard(d_board);

			System.out.println("Available commands: showmap, gameplayer, assigncountries, loadmap");

			boolean l_playingGame = true;

			// startup phase: add, remove, and assigncountries
			while (l_playingGame) {
				System.out.print("Enter setup command (type 'assigncountries' to play): ");
				l_userInput = l_scanner.nextLine().trim();
				if (l_userInput.equals("assigncountries")) {
					if (d_state.getPlayers().size() <= 1) {
						System.out.println("Require At least 2 players to play a game");
						continue;
					}
					System.out.println("end start phase");
					l_playingGame = false;
				}

				l_inputHandlerGame.parseUserCommand(l_userInput);

			}
			System.out.println("Game Started");
			l_playingGame = true;
			// game playing phases: reinforcement, issue order, execute order phases

			while (l_playingGame) {
				System.out.print("Type 'done' to finish, showmap, or enter anything to proceed to issue order: ");
				l_userInput = l_scanner.nextLine().trim();

				if (l_userInput.equalsIgnoreCase("done")) {
					l_playingGame = false;
					System.out.println("Game Ended");
					d_logEntryBuffer.setD_currentPhase("The game has ended.");
					break;
				}

				if (l_userInput.equals("showmap")) {
					l_inputHandlerGame.parseUserCommand(l_userInput);
				}

				d_logEntryBuffer.setD_currentPhase("Reinforcement Phase");
				// ReinforcePhase l_reinforcementPhase = new ReinforcePhase();
				d_state.setReinforcements(new ArrayList<>());
				// l_reinforcementPhase.execute(d_state, d_mapEditor);

				d_logEntryBuffer.setD_currentPhase("Issue Order Phase");
				IssueOrdersPhase l_issueOrderPhase = new IssueOrdersPhase();
				l_issueOrderPhase.run(d_state, d_mapEditor);

				d_logEntryBuffer.setD_currentPhase("Execute Order Phase");
				// ExecuteOrdersPhase l_executeOrdersPhase = new ExecuteOrdersPhase();
				// l_executeOrdersPhase.run(d_state, d_mapEditor);
			}
		}

		l_scanner.close();
	}

	/**
	 * Initialize the hash map representation of game board based on the map used in
	 * the current game update d_board where key: country name, and value: number of
	 * army positioned in the country
	 *
	 */
	public static void initalizeBoard() {
		Set<String> l_mapCountries = d_mapEditor.getD_countries().keySet();
		for (String countryName : l_mapCountries)
			d_board.put(countryName, 0);
	}
}
