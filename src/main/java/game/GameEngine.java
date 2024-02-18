package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import iohandlers.InputHandler;

/**
 * GameEngine Class This Class runs a game by integrating all the functions and
 * classes needed for Warzone
 */
public class GameEngine {
	private List<Player> d_players; // list of players playing the game
	private HashMap<String, Integer> d_board; // key: country name. val: num army in the country
	// private GameMap d_gMap;

	/**
	 * Unparameterized Constructor
	 *
	 */
	GameEngine() {
		d_players = new ArrayList<>();
		d_board = new HashMap<String, Integer>();
	}

	public static void main(String[] args) {

		Scanner l_scanner = new Scanner(System.in);

		boolean l_editMapPhase = false;
		boolean l_gamePhase = false;

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

		// Map editing phase
		if (l_editMapPhase) {
			System.out.println("You are now in the map editing phase.");
			System.out.println(
					"Available commands: editcontinent, editcountry, editneighbor, showmap, savemap, editmap, validatemap");

			boolean l_editingMap = true;

			while (l_editingMap) {
				System.out.print("Enter map editing command (type 'done' to finish): ");
				String l_userInput = l_scanner.nextLine().trim();

				if (l_userInput.equalsIgnoreCase("done")) {
					l_editingMap = false;
				} else {

					InputHandler.parseUserCommand(l_userInput);
				}
			}
		}

		// Game playing phase
		if (l_gamePhase) {
			System.out.println("You are now in the game playing phase.");
			System.out.println("Available commands: showmap, gameplayer, assigncountries, loadmap");

			boolean l_playingGame = true;

			while (l_playingGame) {
				System.out.print("Enter game command (type 'done' to finish): ");
				String l_userInput = l_scanner.nextLine().trim();

				if (l_userInput.equalsIgnoreCase("done")) {
					l_playingGame = false;
				} else {
					InputHandler.parseUserCommand(l_userInput);
				}
			}
		}

		l_scanner.close();
	}
}
