package game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import constants.GameConstants;
import map.Continent;
import map.Country;
import players.Player;

/**
 * Class to save the state of the game
 */
public class GameSave implements Serializable {

	/**
	 * Engine of the game
	 */
	private GameEngine d_gameEngine;

	/**
	 * Constructor for GameSave.
	 * @param p_gameEngine The game engine associated with the game save.
	 */
	public GameSave(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
	}

	/**
	 * Saves the player data to a file.
	 * @return The file name containing the saved player data.
	 */
	public String savePlayers() {
		String l_playerFileName = GameConstants.PLAYERS_FILE;

		// to retrieve the Hashmap of players
		List<Player> l_players = d_gameEngine.getPlayers();

		// create the printWriter to write to the file
		ObjectOutputStream l_outputStream = null;
		// to print player Hashmap into file
		try {
			l_outputStream = new ObjectOutputStream(new FileOutputStream(l_playerFileName));
			for (Player p : l_players) {
				l_outputStream.writeObject(p);
			}
			l_outputStream.close();
		} catch (IOException e) {
			System.out.println("Input - Output Exception in creating the new file for player objects.");
		}
		return l_playerFileName;
	}

	/**
	 * Saves the continents data to a file.
	 * @return The file name containing the saved continents data.
	 */
	public String saveContinents() {
		String l_continentsFileName = GameConstants.CONTINENTS_FILE;

		Map<String, Continent> l_allContinents = d_gameEngine.getD_continents();

		// create the printWriter to write to the file
		ObjectOutputStream l_outputStream = null;
		// to print continent Hashmap into the file
		try {
			l_outputStream = new ObjectOutputStream(new FileOutputStream(l_continentsFileName));
			l_outputStream.writeObject(l_allContinents);
			l_outputStream.close();
		} catch (IOException e) {
			System.out.println("Input - Output Exception in creating the new file for Continent Objects.");
		}

		return l_continentsFileName;
	}

	/**
	 * Saves the countries data to a file.
	 * @return The file name containing the saved countries data.
	 */
	public String saveCountries() {
		String l_countriesFileName = GameConstants.COUNTRIES_FILE;

		Map<String, Country> l_allCountries = d_gameEngine.getD_countries();

		// create the printWriter to write to the file
		ObjectOutputStream l_outputStream = null;
		// to print country HashMap into the file
		try {
			l_outputStream = new ObjectOutputStream(new FileOutputStream(l_countriesFileName));
			l_outputStream.writeObject(l_allCountries);
			l_outputStream.close();
		} catch (IOException e) {
			System.out.println("Input - Output Exception in creating the new file for Country Objects.");
		}
		return l_countriesFileName;
	}

	/**
	 * Saves the last player to a file.
	 * @param p_lastPlayer The last player to be saved.
	 * @return The file name containing the saved last player data.
	 */
	public String saveLastPlayer(Player p_lastPlayer) {
		String l_lastPlayerFileName = GameConstants.LAST_PLAYER;

		ObjectOutputStream l_outputStream = null;

		try {
			l_outputStream = new ObjectOutputStream(new FileOutputStream(l_lastPlayerFileName));
			l_outputStream.writeObject(p_lastPlayer);
			l_outputStream.close();

		} catch (IOException e) {
			System.out.println("Input - Output Exception in creating the new file for Last Player Objects.");
		}

		return l_lastPlayerFileName;
	}

	/**
	 * Saves the game board data to a file.
	 * @return The file name containing the saved game board data.
	 */
	public String saveGameBoard() {
		String l_gameBoardFileName = GameConstants.GAME_BOARD;

		Map<String, Integer> l_gameBoard = d_gameEngine.getGameBoard();

		// create the printWriter to write to the file
		ObjectOutputStream l_outputStream = null;
		// to print country HashMap into the file
		try {
			l_outputStream = new ObjectOutputStream(new FileOutputStream(l_gameBoardFileName));
			l_outputStream.writeObject(l_gameBoard);
			l_outputStream.close();
		} catch (IOException e) {
			System.out.println("Input - Output Exception in creating the new file for Game Board Objects.");
		}
		return l_gameBoardFileName;
	}

	/**
	 * Saves the entire game state.
	 * @param p_fileName The name of the file to save the game state to.
	 * @param p_lastPlayer The last player in the game.
	 */
	public void saveGame(String p_fileName, Player p_lastPlayer) {
		GameConstants.d_saveId++;
		p_fileName = GameConstants.SRC_MAIN_RESOURCES + p_fileName;

		String l_playersFileName = savePlayers();
		String l_continentsFileName = saveContinents();
		String l_countriesFileName = saveCountries();
		String l_lastPlayerFileName = saveLastPlayer(p_lastPlayer);
		String l_gameBoardFileName = saveGameBoard();

		// create a text file using the same name passed
		// This is going to contain the names of the object files

		PrintWriter l_stateWriter = null;
		try {
			l_stateWriter = new PrintWriter(new FileOutputStream(p_fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Error in opening the file " + p_fileName);
			return;
		}

		// write the names of the files
		l_stateWriter.println(l_playersFileName);
		l_stateWriter.println(l_continentsFileName);
		l_stateWriter.println(l_countriesFileName);
		l_stateWriter.println(l_lastPlayerFileName);
		l_stateWriter.println(l_gameBoardFileName);

		// close the writer if it was not initialized properly
		if (l_stateWriter != null) {
			l_stateWriter.close();
		}

		System.out.println("Game saved.");
		System.out.println("Exiting...");
		System.exit(0);
	}
}
