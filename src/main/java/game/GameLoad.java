package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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

	public GameLoad(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	// load game state to populate the variables in gameEngine
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

		populatePlayer(d_playersFileName);
		populateContinents(d_continentFileName);
		populateCountries(d_countriesFileName);

		d_gameEngine.setPhase(new IssueOrdersPhase(d_gameEngine));
		if (d_gameEngine.getPhase().getClass().equals(new IssueOrdersPhase(d_gameEngine).getClass())) {
			IssueOrdersPhase l_issueOrdersPhase = (IssueOrdersPhase) d_gameEngine.getPhase();

			// TODO populate last player and set it in the issueordersphase here
			// l_issueOrdersPhase.setD_lastPlayer();
		}
	}

	public void populatePlayer(String p_playersFileName) {
		ObjectInputStream l_inputStream = null;

		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(p_playersFileName));

			// read the players hashmap
			List<Player> l_allPlayers = (List<Player>) l_inputStream.readObject();

			// put it into the list of players
			d_gameEngine.setPlayers(l_allPlayers);

			l_inputStream.close();

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

}
