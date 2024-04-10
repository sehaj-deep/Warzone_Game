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

public class GameLoad implements Serializable {

	private GameEngine d_gameEngine;

	private String d_playersFileName;
	private String d_continentFileName;
	private String d_countriesFileName;
	private String d_lastPlayerFileName;
	private String d_gameBoardFileName;

	public GameLoad(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

//	 load game state to populate the variables in gameEngine
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

			// TODO populate last player and set it in the issueordersphase here
			// l_issueOrdersPhase.setD_lastPlayer();

			Player l_lastPlayer = getLastPlayer(d_lastPlayerFileName);
			l_issueOrdersPhase.setD_lastPlayer(l_lastPlayer);

		}
	}

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

//	public GameEngine loadGame(String p_fileName) {
//		p_fileName = GameConstants.SRC_MAIN_RESOURCES + p_fileName;
//		GameEngine l_gameEngine = null;
//		ObjectInputStream l_inputStream = null;
//
//		try {
//			l_inputStream = new ObjectInputStream(new FileInputStream(p_fileName));
//			l_gameEngine = (GameEngine) l_inputStream.readObject();
//			l_inputStream.close();
//		} catch (IOException e) {
//			System.out.println("Input Output Exception occurred while reading Gamw Engine objects.");
//
//		} catch (ClassNotFoundException c) {
//			System.out.println("The class of the GameEngine object did not match.");
//		}
//
//		return l_gameEngine;
//
//	}

	public void populatePlayer(String p_playersFileName) {
		ObjectInputStream l_inputStream = null;

		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(p_playersFileName));

			// read the players hashmap
//			List<Player> l_allPlayers = (List<Player>) l_inputStream.readObject();

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
