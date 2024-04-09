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
 * Class to load/save the state of the game
 */
public class GameSave implements Serializable {

	private GameEngine d_gameEngine;

	public GameSave() {
		this.d_gameEngine = new GameEngine();
	}

	public String savePlayers() {

		String l_playerFileName = GameConstants.PLAYERS_FILE;

		// to retrieve the Hashmap of players
		List<Player> l_players = d_gameEngine.getPlayers();

		// create the printWriter to write to the file
		ObjectOutputStream l_outputStream = null;
		// to print player Hashmap into file
		try {
			l_outputStream = new ObjectOutputStream(new FileOutputStream(l_playerFileName));
//			for (Player p : l_players) {
//				l_outputStream.writeObject(p);
//			}
			l_outputStream.writeObject(l_players);
			l_outputStream.close();
		} catch (IOException e) {
			System.out.println("Input - Output Exception in creating the new file for player objects.");
		}

		return l_playerFileName;

	}

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

	public void saveLastPlayer() {

	}

	public void saveGame(String p_fileName) {
		GameConstants.d_saveId++;

		String l_playersFileName = savePlayers();
		String l_continentsFileName = saveContinents();
		String l_countriesFileName = saveCountries();

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

		// close the writer if it was not initialized properly
		if (l_stateWriter != null) {
			l_stateWriter.close();
		}
	}

}
