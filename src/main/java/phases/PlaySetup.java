package phases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import game.GameEngine;
import map.ConquestMapReader;
import map.DominationMapReader;
import map.MapReaderAdapter;
import players.Player;
import players.PlayerStrategy;
import utils.ValidationException;

/**
 * Setup phase of the game where players are added and countries are assigned.
 */
public class PlaySetup extends Play {

	/**
	 * Constructs a PlaySetup object with the specified game engine.
	 *
	 * @param p_gameEngine the game engine
	 */
	public PlaySetup(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * Adds a player to the player list.
	 *
	 * @param p_playerName     The name of the player to add.
	 * @param p_playerStrategy the type of the player to add
	 */
	@Override
	public void addPlayers(String p_playerName, PlayerStrategy p_playerStrategy) {
		Player l_player = new Player(p_playerName, p_playerStrategy);

		// Check if the input player is not empty or null
		if (p_playerName == null || p_playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Player name cannot be empty");
		}

		// Check if input characters are valid or not
		if (!p_playerName.matches("[a-zA-Z0-9]+")) {
			throw new IllegalArgumentException("Invalid characters are not allowed");
		}

		// Check if the player already exist
		if (d_gameEngine.getPlayers().contains(l_player)) {
			throw new IllegalArgumentException("Player " + p_playerName + " already exists");
		}

		d_gameEngine.getPlayers().add(l_player);
		System.out.println(p_playerName + " successfully added as a player");
	}

	/**
	 * Removes a player from the player list.
	 *
	 * @param p_playerName The name of the player to remove.
	 */
	@Override
	public void removePlayers(String p_playerName) {

		// Check if the input player is not empty or null
		if (p_playerName == null || p_playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Player name cannot be empty");
		}

		// List of the players
		List<Player> players = d_gameEngine.getPlayers();
		boolean playerRemoved = false;

		// Finding the target player
		Iterator<Player> iterator = players.iterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player.getPlayerName().equals(p_playerName)) {
				iterator.remove();
				playerRemoved = true;
				break; // Assuming each player has a unique name, so we can break after finding the
				// player
			}
		}

		// Check if the player is found or not
		if (!playerRemoved) {
			throw new IllegalArgumentException(p_playerName + " not found in list of players");
		}

		// Removing the player
		Player l_player = new Player(p_playerName);
		d_gameEngine.getPlayers().remove(l_player);

		System.out.println(p_playerName + " successfully removed as a player");
	}

	/**
	 * Checks validity of assigned countries if a player has 2 or more countries
	 * assigned than another player has, then this is invalid
	 *
	 * @return true if valid. false if invalid
	 */
	public boolean isAssignCountriesValid() {
		int l_minSize = Integer.MAX_VALUE;// min of number of player's owned countries
		int l_maxSize = Integer.MIN_VALUE; // max of number of player's owned countries

		// Checking if assigning countries is valid or not
		for (int i = 1; i < d_gameEngine.getPlayers().size(); i++) {
			Player l_player = d_gameEngine.getPlayers().get(i);
			int l_numCountriesOwned = l_player.getOwnership().size();
			if (l_numCountriesOwned < l_minSize) {
				l_minSize = l_numCountriesOwned;
			}
			if (l_numCountriesOwned > l_maxSize) {
				l_maxSize = l_numCountriesOwned;
			}
		}
		return (l_maxSize - l_minSize) <= 1;
	}

	/**
	 * Assigns countries to the specified player.
	 */
	@Override
	public void assignCountries() {

		if (!isAssignCountriesValid()) {
			return;
		}

		// Countries name
		Set<String> l_countries = d_gameEngine.getD_countries().keySet();
		List<String> l_countriesList = new ArrayList<>(l_countries);

		// Shuffle the list of available countries
		Collections.shuffle(l_countriesList);

		for (int i = 0; i < l_countriesList.size(); i++) {
			// Add assigned country to player's countries (d_ownership)
			int l_idx = i % (d_gameEngine.getPlayers().size());
			Player l_player = d_gameEngine.getPlayers().get(l_idx);
			l_player.conquerCountry(l_countriesList.get(i));
		}

		for (Player l_player : d_gameEngine.getPlayers()) {
			System.out.println("Player --------");
			Set<String> temp = l_player.getOwnership();
			for (String s : temp) {
				System.out.println(s + ",");
			}
		}
		System.out.println("Assign Countries Completed");

		initalizeBoard();
		showMap();

		this.next();
	}

	/**
	 * Reinforce is invalid in playSetup
	 */
	@Override
	public void reinforce() {
		this.printInvalidCommandMessage();
	}

	/**
	 * Attack is invalid in playSetup
	 */
	@Override
	public void attack() {
		this.printInvalidCommandMessage();
	}

	/**
	 * fortify is invalid in playSetup
	 */
	@Override
	public void fortify() {
		this.printInvalidCommandMessage();
	}

	/**
	 * Loads map from a file.
	 *
	 * @param p_filename the name of the file
	 */
	@Override
	public void loadMap(String p_filename) {
		// open the file to decide if you need to use an adapter or not
		Scanner l_scanner = null;
		try {
			l_scanner = new Scanner(new FileInputStream(p_filename));
		} catch (FileNotFoundException e) {
			System.out.println("The map file " + p_filename + " is not found in resources folder.");
		}

		if (l_scanner != null) {
			if (l_scanner.nextLine().contains("[Map]")) {
				// call the adapter
				ConquestMapReader l_conquestReader = new ConquestMapReader(d_gameEngine);
				MapReaderAdapter l_mapAdapter = new MapReaderAdapter(l_conquestReader);
				l_mapAdapter.readDominationMap(p_filename, false);
			} else {
				// call the domination file
				DominationMapReader l_dominationReader = new DominationMapReader(d_gameEngine);
				l_dominationReader.readDominationMap(p_filename, false);
			}
		}

		try {
			boolean l_isValidated = validateMap();
			if (!l_isValidated) {
				throw new ValidationException("Unable to load map: The map is invalid.");
			}
		} catch (ValidationException e) {
			System.out.print(e.getMessage());
			clearMap();
			return;

		}
		System.out.println("The map " + p_filename + " has been loaded into the game.");
	}

	/**
	 * editMap is invalid in playSetup
	 */
	@Override
	public void editMap(String p_filename) {
		this.printInvalidCommandMessage();
	}

	/**
	 * addContinent is invalid in playSetup
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		this.printInvalidCommandMessage();
	}

	/**
	 * removeContinent is invalid in playSetup
	 */
	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();
	}

	/**
	 * addCountry is invalid in playSetup
	 */
	@Override
	public void addCountry(String p_countryName, String p_continent) {
		this.printInvalidCommandMessage();
	}

	/**
	 * removeCountry is invalid in playSetup
	 */
	@Override
	public void removeCountry(String p_countryName) {
		this.printInvalidCommandMessage();
	}

	/**
	 * addNeighbor is invalid in playSetup
	 */
	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();
	}

	/**
	 * removeNeighbor is invalid in playSetup
	 */
	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();
	}

	/**
	 * saveMap is invalid in playSetup
	 */
	@Override
	public void saveMap(String p_filename) {
		this.printInvalidCommandMessage();
	}

	/**
	 * Transition to the next phase of the game. In PlaySetup, transitions to the
	 * Reinforce phase.
	 */
	@Override
	public void next() {
		d_gameEngine.setPhase(new ReinforcePhase(d_gameEngine));
	}

}