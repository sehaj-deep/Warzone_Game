package phases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import game.GameEngineNew;
import game.Player;
import utils.ValidationException;

public class PlaySetup extends Play {
	public PlaySetup(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	private final List<String> d_playerNameList = new ArrayList<>();

	/**
	 * Adds a player to the player list.
	 *
	 * @param p_playerName The name of the player to add.
	 */
	@Override
	public void addPlayers(String p_playerName) {
		if (p_playerName == null || p_playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Player name cannot be empty");
		}

		if (!p_playerName.matches("[a-zA-Z0-9]+")) {
			throw new IllegalArgumentException("Invalid characters are not allowed");
		}

		if (d_playerNameList.contains(p_playerName)) {
			throw new IllegalArgumentException("Player " + p_playerName + " already exists");
		}

		d_playerNameList.add(p_playerName);

		Player l_player = new Player(p_playerName);
		d_gameEngine.getD_players().add(l_player);

		System.out.println("Player: " + p_playerName + " successfully added ");

	}

	/**
	 * Removes a player from the player list.
	 *
	 * @param p_playerName The name of the player to remove.
	 */
	@Override
	public void removePlayers(String p_playerName) {
		if (p_playerName == null || p_playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Player name cannot be empty");
		}

		if (!d_playerNameList.contains(p_playerName)) {
			System.out.println(d_playerNameList.size());
			for (String p : d_playerNameList) {
				System.out.println(p);
			}
			throw new IllegalArgumentException("Player " + p_playerName + " not found");
		}

		d_playerNameList.remove(p_playerName);

		d_playerNameList.add(p_playerName);

		Player l_player = new Player(p_playerName);
		d_gameEngine.getD_players().remove(l_player);

		System.out.println("Player: " + p_playerName + " successfully removed");
	}

	/**
	 * Assigns countries to the specified player.
	 */
	@Override
	public void assignCountries() {

		Set<String> p_countries = d_gameEngine.getD_countries().keySet();

		// check if countries is valid
		int l_minSize = d_gameEngine.getD_players().get(0).getOwnership().size(); // min of number of player's owned
																					// countries
		int l_maxSize = d_gameEngine.getD_players().get(0).getOwnership().size(); // max of number of player's owned
																					// countries

		for (int i = 1; i < d_gameEngine.getD_players().size(); i++) {
			Player l_player = d_gameEngine.getD_players().get(i);
			int l_numCountriesOwned = l_player.getOwnership().size();
			if (l_numCountriesOwned < l_minSize) {
				l_minSize = l_numCountriesOwned;
			}
			if (l_numCountriesOwned > l_maxSize) {
				l_maxSize = l_numCountriesOwned;
			}
		}

		boolean isValid = (l_maxSize - l_minSize) <= 1;

		List<String> l_countriesList = new ArrayList<>(p_countries);

		// Shuffle the list of available countries
		Collections.shuffle(l_countriesList);

		for (int i = 0; i < l_countriesList.size(); i++) {
			// Add assigned country to player's countries (d_ownership)
			int l_idx = i % (d_gameEngine.getD_players().size());
			Player l_player = d_gameEngine.getD_players().get(l_idx);
			l_player.conquerCountry(l_countriesList.get(i));
		}

		System.out.println("Assign Countries Completed");

		this.next();
	}

	@Override
	public void reinforce() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void attack() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void fortify() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void loadMap(String p_filename) {

		readMap(p_filename, true);
		try {
			boolean l_isValidated = validateMap();
			if (!l_isValidated) {
				throw new ValidationException("Unable to load map: The map is invalid.");
			}
		} catch (ValidationException e) {
			System.out.print(e.getMessage());

		}
		System.out.println("The map " + p_filename + " has been loaded into the game.");
	}

	@Override
	public void editMap(String p_filename) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(String p_countryName) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void saveMap(String p_filename) {
		this.printInvalidCommandMessage();
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new ReinforcePhase(d_gameEngine));
	}

}