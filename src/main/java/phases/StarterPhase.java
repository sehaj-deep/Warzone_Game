package phases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import game.GameState;
import game.Player;
import map.MapEditor;

/**
 * Represents the phase where players are added or removed.
 */
public class StarterPhase {

	/**
	 * Constructor for the StarterPhase class.
	 */
	public StarterPhase() {
	}

	// List to store player names
	private final List<String> d_playerNameList = new ArrayList<>();

	/**
	 * get this player's name
	 *
	 * @return d_playerNameList name
	 */

	public List<String> getPlayerNameList() {
		return d_playerNameList;
	}

	/**
	 * Adds a player to the player list and assigns countries.
	 *
	 * @param p_playerName The name of the player to add.
	 */
	public void addPlayer(String p_playerName, GameState p_state) {
		if (p_playerName == null || p_playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Player name cannot be empty");
		}

		if (!p_playerName.matches("[a-zA-Z0-9]+")) {
			throw new IllegalArgumentException("Invalid characters are not allowed");
		}

		if (d_playerNameList.contains(p_playerName)) {
			throw new IllegalArgumentException("Player " + p_playerName + " already exists");
		}

		this.getPlayerNameList().add(p_playerName);
//		d_playerNameList.add(p_playerName);

		Player l_player = new Player(p_playerName);
		p_state.getPlayers().add(l_player);

		System.out.println("Player: " + p_playerName + " successfully added ");
	}

	/**
	 * Removes a player from the player list.
	 *
	 * @param p_playerName The name of the player to remove.
	 */
	public void removePlayer(String p_playerName, GameState p_state) {
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
		int playerIdx = d_playerNameList.indexOf(p_playerName);
		d_playerNameList.remove(p_playerName);

		Player l_player = new Player(p_playerName);
		p_state.getPlayers().remove(playerIdx);

		System.out.println("Player: " + p_playerName + " successfully removed");
	}

	/**
	 * check validity of assigned countries if a player has 2 or more countries
	 * assigned than another player has, then this is invalid
	 *
	 * @param p_state current game state
	 * @return true if valid. false if invalid
	 */
	public boolean isAssignCountriesValid(GameState p_state) {
		int l_minSize = p_state.getPlayers().get(0).getOwnership().size(); // min of number of player's owned countries
		int l_maxSize = p_state.getPlayers().get(0).getOwnership().size(); // max of number of player's owned countries

		for (int i = 1; i < p_state.getPlayers().size(); i++) {
			Player l_player = p_state.getPlayers().get(i);
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
	 * Randomly shuffle countries and distribute one by one to a player
	 *
	 * @param p_state   the current game state
	 * @param countries all countries available to assign to the players
	 */
	public void shuffleAndDistributeCountries(GameState p_state, Set<String> p_countries) {
		List<String> l_countriesList = new ArrayList<>(p_countries); // cast set to list

		// Shuffle the list of available countries
		Collections.shuffle(l_countriesList);

		for (int i = 0; i < l_countriesList.size(); i++) {
			// Add assigned country to player's countries (d_ownership)
			int l_idx = i % (p_state.getPlayers().size());
			Player l_player = p_state.getPlayers().get(l_idx);
			l_player.conquerCountry(l_countriesList.get(i));
		}
	}

	/**
	 * Assigns countries to the specified player.
	 *
	 * @param p_state the current game state
	 * @param p_gMap  the map used in this game
	 */
	public void assignCountriesToPlayer(GameState p_state, MapEditor p_gMap) {
		Set<String> l_mapCountries = p_gMap.getD_countries().keySet();
		shuffleAndDistributeCountries(p_state, l_mapCountries);
	}
}