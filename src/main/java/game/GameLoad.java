package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import map.Continent;
import map.Country;
import players.Player;

public class GameLoad implements Serializable {

	private GameEngine d_gameEngine;

	private String d_playersFileName;
	private String d_continentFileName;
	private String d_coutriesFileName;

	public GameLoad() {
		d_gameEngine = new GameEngine();
	}

	// load game state to populate the variables in gameEngine
	public void loadGame(String p_fileName) {
		Scanner l_stateReader = null;
		try {
			l_stateReader = new Scanner(new FileInputStream(p_fileName));
		} catch (FileNotFoundException e) {
			System.out.println("The file " + p_fileName + " is not found. Hence file not opened.");
		}

		d_playersFileName = l_stateReader.nextLine();
		d_continentFileName = l_stateReader.nextLine();
		d_coutriesFileName = l_stateReader.nextLine();
	}

	public void populatePlayer() {
		ObjectInputStream l_inputStream = null;

		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(d_playersFileName));

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

	public void populateContinents() {
		ObjectInputStream l_inputStream = null;
		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(d_continentFileName));

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

	public void populateCountries() {
		ObjectInputStream l_inputStream = null;
		try {
			l_inputStream = new ObjectInputStream(new FileInputStream(d_coutriesFileName));

			// read the Hashmap from the file
			HashMap<String, Country> l_allCountries = (HashMap<String, Country>) l_inputStream.readObject();
			d_gameEngine.setD_countries(l_allCountries);
			l_inputStream.close();

			l_inputStream.close();
		} catch (IOException e) {
			System.out.println("Input Output Exception occurred while reading country objects.");

		} catch (ClassNotFoundException c) {
			System.out.println("The class of the country object did not match.");
		}
	}

}
