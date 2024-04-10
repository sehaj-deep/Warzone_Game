package game;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

import constants.GameConstants;
import map.Continent;
import map.Country;
import phases.IssueOrdersPhase;
import players.Player;

/**
 * This class is responsible for loading the state of the game from files.
 */
public class GameLoad implements Serializable {

	/**
	 * Engine of the game
	 */
	private GameEngine d_gameEngine;

	/**
	 * The file name containing player data.
	 */
	private String d_playersFileName;

	/**
	 * The file name containing continent data.
	 */
	private String d_continentFileName;

	/**
	 * The file name containing country data.
	 */
	private String d_countriesFileName;

	/**
	 * The file name containing the last player data.
	 */
	private String d_lastPlayerFileName;

	/**
	 * The file name containing the game board data.
	 */
	private String d_gameBoardFileName;

	/**
	 * Constructor for GameLoad.
	 * @param p_gameEngine The game engine associated with the game load.
	 */
	public GameLoad(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * Loads the game state from files.
	 * @param p_fileName The name of the file containing the game state.
	 */
	public void loadGame(String p_fileName) {
		p_fileName = GameConstants.SRC_MAIN_RESOURCES + p_fileName;
		Scanner l_stateReader = null;
		try {
			l_stateReader = new Scanner(new FileInputStream(p_fileName));
		} catch (FileNotFoundException e) {
			System.out.println("The file " + p_fileName + " is not found. Hence file not opened.");
		}

		d_playersFileName = l_stateReader.nextLine();
		d_continentFileName = l_stateReader.nextLine();
		d_countriesFileName = l_stateReader.nextLine();
		d_lastPlayerFileName = l_stateReader.nextLine();
		d_gameBoardFileName = l_stateReader.nextLine();

		populatePlayer(d_playersFileName);
		populateContinents(d_continentFileName);
		populateCountries(d_countriesFileName);
		populateGameBoard(d_gameBoardFileName);

		d_gameEngine.setPhase(new IssueOrdersPhase(d_gameEngine));
		if (d_gameEngine.getPhase().getClass().equals(new IssueOrdersPhase(d_gameEngine).getClass())) {
			IssueOrdersPhase l_issueOrdersPhase = (IssueOrdersPhase) d_gameEngine.getPhase();

			Player l_lastPlayer = getLastPlayer(d_lastPlayerFileName);
			l_issueOrdersPhase.setD_lastPlayer(l_lastPlayer);

		}
	}

	/**
	 * Populates the game board with data from a file.
	 * @param p_gameBoardFileName The name of the file containing the game board data.
	 */
	public void populateGameBoard(String p_gameBoardFileName) {
		ObjectInputStream l_inputStream = null;
		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(p_gameBoardFileName));

			// read the Hashmap from the file
			HashMap<String, Integer> l_gameBoard = (HashMap<String, Integer>) l_inputStream.readObject();
			d_gameEngine.setGameBoard(l_gameBoard);
			l_inputStream.close();

		} catch (IOException e) {
			System.out.println("Input Output Exception occurred while reading Game Board objects.");

		} catch (ClassNotFoundException c) {
			System.out.println("The class of the Game Board object did not match.");
		}

	}

	/**
	 * Populates the player data from a file.
	 * @param p_playersFileName The name of the file containing player data.
	 */
	public void populatePlayer(String p_playersFileName) {
		ObjectInputStream l_inputStream = null;

		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(p_playersFileName));

			while (true) {
				Player l_player = (Player) l_inputStream.readObject();
				d_gameEngine.getPlayers().add(l_player);
			}

		} catch (EOFException eof) {
			try {
				l_inputStream.close();
			} catch (IOException ex) {
				System.out.println("Exception while closing the file.");
			}

		} catch (IOException e) {
			System.out.println("Input Output Exception occurred while reading players objects.");

		} catch (ClassNotFoundException c) {
			System.out.println("The class of the player object did not match.");
		}
	}

	/**
	 * Populates the continents data from a file.
	 * @param p_continentFileName The name of the file containing continent data.
	 */
	public void populateContinents(String p_continentFileName) {
		ObjectInputStream l_inputStream = null;
		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(p_continentFileName));

			// read the Hashmap from the file
			HashMap<String, Continent> l_allContinents = (HashMap<String, Continent>) l_inputStream.readObject();
			d_gameEngine.setD_continents(l_allContinents);

			l_inputStream.close();
		} catch (IOException e) {
			System.out.println("Input Output Exception occurred while reading continent objects.");

		} catch (ClassNotFoundException c) {
			System.out.println("The class of the continent object did not match.");
		}
	}

	/**
	 * Populates the countries data from a file.
	 * @param p_countriesFileName The name of the file containing country data.
	 */
	public void populateCountries(String p_countriesFileName) {
		ObjectInputStream l_inputStream = null;
		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(p_countriesFileName));

			// read the Hashmap from the file
			HashMap<String, Country> l_allCountries = (HashMap<String, Country>) l_inputStream.readObject();
			d_gameEngine.setD_countries(l_allCountries);
			l_inputStream.close();

		} catch (IOException e) {
			System.out.println("Input Output Exception occurred while reading country objects.");

		} catch (ClassNotFoundException c) {
			System.out.println("The class of the country object did not match.");
		}
	}

	/**
	 * Retrieves the last player from a file.
	 * @param p_lastPlayerFile The name of the file containing the last player data.
	 * @return The last player.
	 */
	public Player getLastPlayer(String p_lastPlayerFile) {
		ObjectInputStream l_inputStream = null;

		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(p_lastPlayerFile));

			// read the Player Object
			Player l_lastPlayer = (Player) l_inputStream.readObject();

			l_inputStream.close();
			return l_lastPlayer;

		} catch (IOException e) {
			System.out.println("Input Output Exception occurred while reading country objects.");

		} catch (ClassNotFoundException c) {
			System.out.println("The class of the country object did not match.");
		}
		return null;
	}

}
