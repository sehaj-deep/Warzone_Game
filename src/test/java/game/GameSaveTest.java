package game;

import static org.junit.Assert.assertEquals;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import constants.GameConstants;
import map.Continent;
import map.Country;
import players.Player;

public class GameSaveTest {

	private GameEngine gameEngine;
	private GameSave gameSave;

	@Before
	public void setUp() {
		gameEngine = new GameEngine();
		gameSave = new GameSave(gameEngine);

		// Create test players
		List<Player> players = new ArrayList<>();
		Player player1 = new Player("Player 1");
		Player player2 = new Player("Player 2");
		players.add(player1);
		players.add(player2);
		gameEngine.setPlayers(players);

		// Create test countries
		HashMap<String, Country> countries = new HashMap<>();
		Country country1 = new Country("Country 1");
		Country country2 = new Country("Country 2");
		countries.put(country1.getD_name(), country1);
		countries.put(country2.getD_name(), country2);
		gameEngine.setD_countries(countries);

		// Create test continents
		HashMap<String, Continent> continents = new HashMap<>();
		Continent continent1 = new Continent("Continent 1", 5);
		Continent continent2 = new Continent("Continent 2", 3);
		continents.put(continent1.getD_continentName(), continent1);
		continents.put(continent2.getD_continentName(), continent2);
		gameEngine.setD_continents(continents);
	}

	@Test
	public void testSavePlayers() {
		String fileName = gameSave.savePlayers();

		// Assert that the filename is correct
		assertEquals(GameConstants.PLAYERS_FILE, fileName);

		// Assert that players are saved correctly
		List<Player> savedPlayers = readPlayersFromFile(GameConstants.PLAYERS_FILE);
		assertEquals(gameEngine.getPlayers().size(), savedPlayers.size());
	}

	@Test
	public void testSaveCountries() {
		String fileName = gameSave.saveCountries();

		// Assert that the filename is correct
		assertEquals(GameConstants.COUNTRIES_FILE, fileName);

		// Assert that countries are saved correctly
		Map<String, Country> savedCountries = readObjectFromFile(GameConstants.COUNTRIES_FILE);
		assertEquals(gameEngine.getD_countries().size(), savedCountries.size());
	}

	@Test
	public void testSaveContinents() {
		String fileName = gameSave.saveContinents();

		// Assert that the filename is correct
		assertEquals(GameConstants.CONTINENTS_FILE, fileName);

		// Assert that continents are saved correctly
		Map<String, Continent> savedContinents = readObjectFromFile(GameConstants.CONTINENTS_FILE);
		assertEquals(gameEngine.getD_continents().size(), savedContinents.size());
	}

	// Utility method to read object from file
	private <T> T readObjectFromFile(String fileName) {
		T object = null;
		try (FileInputStream fileInputStream = new FileInputStream(new File(fileName));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
			object = (T) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	private List<Player> readPlayersFromFile(String fileName) {
		Player object = null;
		List<Player> l_playerList = new ArrayList<Player>();
		try (FileInputStream fileInputStream = new FileInputStream(new File(fileName));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
			while (true) {
				object = (Player) objectInputStream.readObject();
				l_playerList.add(object);
			}
		} catch (EOFException eof) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return l_playerList;
	}
}
