package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import iohandlers.InputHandler;
import map.MapEditor;

/**
 * GameEngine Class This Class runs a game by integrating all the functions and
 * classes needed for Warzone
 */
public class GameEngine {
	private static MapEditor d_mapEditor = new MapEditor();
	private static InputHandler d_inputHandler = new InputHandler(d_mapEditor);

	private List<Player> d_players = new ArrayList<>(); // list of players playing the game
	private HashMap<String, Integer> d_board = new HashMap<String, Integer>(); // key: country name. val: num army in
	GameState d_state = new GameState(d_players); // the country

	// IssueOrdersPhase issueOrderPhase;
	// ExecuteOrdersPhase executeOrdersPhase;

	/**
	 * Unparameterized Constructor
	 *
	 */
//	public GameEngine() {
//		d_mapEditor = new MapEditor();
//		d_inputHandler = new InputHandler(d_mapEditor);
//		d_players = new ArrayList<>();
//		d_board = new HashMap<String, Integer>();
//		// FIXME: delete below sysout
//		System.out.println("Inside constructor of gameEngine");
//	}

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
				break;
			case "2":
				l_gamePhase = true;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
//		l_scanner.nextLine();

		// Map editing phase
		if (l_editMapPhase) {
			System.out.println("You are now in the map editing phase.");
			System.out.println("To edit the map or create a new map, use the following command:");
			System.out.println("editmap filename");

			l_userInput = l_scanner.nextLine();

			while (!l_userInput.startsWith("editmap")) {
				System.out.println(
						"Invalid Command entered. You need to give the name of file using \"editmap filename\" command.");
				l_userInput = l_scanner.nextLine();
			}

			d_inputHandler.parseUserCommand(l_userInput);

			System.out.println(
					"Available commands: editcontinent, editcountry, editneighbor, showmap, savemap, validatemap");

			boolean l_editingMap = true;

			while (l_editingMap) {
				System.out.print("Enter map editing command (type 'done' to finish): ");
				l_userInput = l_scanner.nextLine().trim();

				if (l_userInput.equalsIgnoreCase("done")) {
					l_editingMap = false;
				} else {
					d_inputHandler.parseUserCommand(l_userInput);
				}
			}
		}

		// Game playing phase
//		if (l_gamePhase) {
//			System.out.println("You are now in the game playing phase.");
//			System.out.println("Available commands: showmap, gameplayer, assigncountries, loadmap");
//
//			boolean l_playingGame = true;
//
//			// startup phase here
//
//			// game playing: reinforcmeent, issue order, execute order phases
//			while (l_playingGame) {
//				System.out.print("Enter game command (type 'done' to finish): ");
//				l_userInput = l_scanner.nextLine().trim();
//
//				if (l_userInput.equalsIgnoreCase("done")) {
//					l_playingGame = false;
//				} else {
//					InputHandler.parseUserCommand(l_userInput);
//				}
//				// put reinforcement phase here
//				issueOrdersPhase.run(d_state, d_mapEditor);
//				executeOrdersPhase.run(d_state, d_mapEditor);
//			}
//		}

		l_scanner.close();
	}
}
