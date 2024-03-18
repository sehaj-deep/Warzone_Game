package phases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.GameEngine;
import game.Player;
import models.Continent;
import models.Country;

/**
 * Represents the phase where the game is played.
 */
public abstract class Play extends Phase {

	/**
	 * Constructs a new Play phase with the given game engine context.
	 * 
	 * @param p_gameEngine a context object for Play phase
	 */
	public Play(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * Displays the map in text format, showing countries and their neighbors.
	 */
	@Override
	public void showMap() {
		System.out.println("The following is the text format of the map");
		System.out.println("----------------------------------------------------------------------");

		HashMap<String, Continent> l_continents = d_gameEngine.getD_continents();
		for (HashMap.Entry<String, Continent> l_cont : l_continents.entrySet()) {
			Continent l_currContinent = l_cont.getValue();
			String l_nameOfCont = l_currContinent.getD_continentName();
			System.out.println("For the continent: " + l_nameOfCont);
			System.out.println("[Country: Neighbors]");

			Set<Country> l_corresCountries = l_currContinent.getD_countries();
			for (Country l_country : l_corresCountries) {
				String l_countryName = l_country.getD_name();
				System.out.print(l_countryName + ": ");

				Set<Country> l_countryNeighbors = l_country.getNeighbors();
				for (Country neighbor : l_countryNeighbors) {
					String l_neighborName = neighbor.getD_name();
					System.out.print(l_neighborName + " ");
				}
				System.out.println();
			}
			System.out.println("----------------------------------------------------------------------");
		}

		// to get the list of players
		List<Player> l_allPlayers = d_gameEngine.getGameState().getPlayers();
		if (l_allPlayers.size() == 0) {
			return;
		}

		Map<String, Integer> l_countriesArmies = d_gameEngine.getGameState().getGameBoard();
		for (Player l_currPlayer : l_allPlayers) {
			System.out.print(l_currPlayer.getPlayerName() + ": ");

			Set<String> l_countriesOwned = l_currPlayer.getOwnership();

			for (String l_singleCountry : l_countriesOwned) {
				int l_numOfArmies = l_countriesArmies.get(l_singleCountry);
				System.out.print("[" + l_singleCountry + ", " + l_numOfArmies + "] ");
			}
			System.out.println();
		}
	}

	/**
	 * Initialize the hash map representation of game board based on the map used in
	 * the current game update d_board where key: country name, and value: number of
	 * army positioned in the country
	 *
	 */
	public void initalizeBoard() {
		Set<String> l_mapCountries = d_gameEngine.getD_countries().keySet();
		for (String countryName : l_mapCountries)
			d_gameEngine.getGameState().getGameBoard().put(countryName, 0);
	}

	/**
	 * Placeholder method for editing a country.
	 */
	public void editCountry() {
		printInvalidCommandMessage();
	}

	/**
	 * Placeholder method for saving the map.
	 */
	public void saveMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Placeholder method for ending the game.
	 */
	public void endGame() {
		printInvalidCommandMessage();
	}
}
