package phases;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import game.GameEngineNew;
import models.Continent;
import models.Country;

import java.util.ArrayList;
import java.util.List;

public abstract class Phase {
	protected GameEngineNew d_gameEngine;

	public Phase(GameEngineNew p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
	}

	public static final List<String> d_playerNameList = new ArrayList<>();

	// general behavior
	abstract public void loadMap(String p_filename);

//	abstract public void showMap();

	// edit map state behavior
	abstract public void editMap(String p_filename);

	abstract public void addContinent(String p_continentName, int p_bonusArmies);

	abstract public void removeContinent(String p_continentName);

	abstract public void addCountry(String p_countryName, String p_continent);

	abstract public void removeCountry(String p_countryName);

	abstract public void addNeighbor(String p_country, String p_neighbor);

	abstract public void removeNeighbor(String p_country, String p_neighbor);

//	abstract public boolean validateMap();

	abstract public void saveMap(String p_filename);

	// play state behavior
	// game setup state behavior

	abstract public void addPlayers(String p_playerName);

	abstract public void removePlayers(String p_playerName);

	abstract public void assignCountries();

	// reinforcement state behavior
	abstract public void reinforce();

	// attack state behavior
	abstract public void attack();

	// fortify state behavior
	abstract public void fortify();

	// end state behavior
	abstract public void endGame();

	// go to next phase
	abstract public void next();

	// methods common to all states
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName());
	}

	// Common functionalities of all phases
	/**
	 * Validates if the map's continents form a connected graph.
	 * 
	 * @return true if the map's continents form a connected graph, otherwise false
	 */
	public boolean validateMap() {
		// Check if the map is a connected graph
		boolean isMapConnectedGraph = traverseMapGraph();
		if (!isMapConnectedGraph) {
			System.out.println("Map is not a connected graph.");
			return false;
		}

		// Validate each continent
		// FIXME: either fix this or remove the commented Line
//		for (HashMap.Entry<String, Continent> continentEntry : d_continents.entrySet()) {
		for (HashMap.Entry<String, Continent> continentEntry : d_gameEngine.getD_continents().entrySet()) {
			Continent continent = continentEntry.getValue();

			// Check if the continent has countries
			if (continent.getD_countries().isEmpty()) {
				System.out.println(continent.getD_continentName() + " is invalid: It does not have any countries.");
				return false;
			}

			// Check if the continent is a connected graph
			boolean isContinentConnectedGraph = traverseContinentGraph(continent);
			if (!isContinentConnectedGraph) {
				System.out.println(continent.getD_continentName() + " is not a connected graph.");
				return false;
			}

			// Check if countries belong to only one continent
			if (!validateCountriesInContinent(continent)) {
				return false;
			}
		}

		// If all checks pass, the map is valid
		System.out.println("The map is valid.");
		return true;
	}

	/**
	 * Validates that each country in the provided continent is only present in that
	 * continent.
	 * 
	 * @param continent The continent whose countries need to be validated.
	 * @return {@code true} if all countries in the continent belong to only that
	 *         continent, {@code false} otherwise.
	 */
	private boolean validateCountriesInContinent(Continent continent) {
		HashMap<String, String> countryContinentMap = new HashMap<>();

		for (Country country : continent.getD_countries()) {
			String countryName = country.getD_name();
			if (countryContinentMap.containsKey(countryName)) {
				System.out.println("Country " + countryName + " is already present in continent "
						+ countryContinentMap.get(countryName) + ", can't be in two continents.");
				return false;
			} else {
				countryContinentMap.put(countryName, continent.getD_continentName());
			}
		}
		return true;
	}

	/**
	 * Traverses the countries of the current map to determine if it's a connected
	 * graph
	 * 
	 * @return true if all countries have been visited, false otherwise.
	 */
	public boolean traverseMapGraph() {
		Set<Country> l_visitedCountries = new HashSet<>();
		Country l_startCountry = d_gameEngine.getD_countries().values().iterator().next();

		depthFirstSearchForMap(l_startCountry, l_visitedCountries);

		return l_visitedCountries.size() == d_gameEngine.getD_countries().size();
	}

	/**
	 * Traverses the countries of the specified continent to determine if it's a
	 * connected graph
	 * 
	 * @param p_continent The continent whose countries are to be traversed.
	 * @return true if all countries within the continent have been visited, false
	 *         otherwise.
	 */
	public boolean traverseContinentGraph(Continent p_continent) {
		Set<Country> l_countriesInContinent = p_continent.getD_countries();
		Set<Country> l_visitedCountries = new HashSet<>();
		Country l_startCountry = l_countriesInContinent.iterator().next();

		depthFirstSearchForContinent(l_startCountry, p_continent, l_visitedCountries);

		return l_visitedCountries.size() == l_countriesInContinent.size();
	}

	/**
	 * Performs depth-first search traversal starting from the specified country.
	 * 
	 * @param p_country          The starting country for the traversal.
	 * @param p_visitedCountries Set to store visited countries during traversal.
	 */
	private void depthFirstSearchForMap(Country p_country, Set<Country> p_visitedCountries) {
		p_visitedCountries.add(p_country);
		Set<Country> l_neighboringCountries = p_country.getNeighbors();

		for (Country l_neighbor : l_neighboringCountries) {
			if (!p_visitedCountries.contains(l_neighbor)) {
				depthFirstSearchForMap(l_neighbor, p_visitedCountries);
			}
		}
	}

	/**
	 * Performs depth-first search traversal starting from the specified country.
	 * 
	 * @param p_country          The starting country for the traversal.
	 * @param p_visitedCountries Set to store visited countries during traversal.
	 */
	private void depthFirstSearchForContinent(Country p_country, Continent p_continent,
			Set<Country> p_visitedCountries) {
		p_visitedCountries.add(p_country);

		Set<Country> l_neighboringCountries = p_country.getNeighbors();

		for (Country l_neighbor : l_neighboringCountries) {
			if (p_continent.getD_countries().contains(l_neighbor) && !p_visitedCountries.contains(l_neighbor)) {
				depthFirstSearchForContinent(l_neighbor, p_continent, p_visitedCountries);
			}
		}
	}

	/**
	 * Displays the map along with Player and armies to ease the playing of game.
	 */
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

////		if (d_gameEngine.getPhase() == Preload || d_gameEngine == PostOrder) {
////
////		}
//		// to get the list of players
//		// FIXME: Find how to get list of players
//		List<Player> l_allPlayers = p_gameState.getPlayers();
//		if (l_allPlayers.size() == 0) {
//			return;
//		}
//
//		// FIXME: Find how to get list of armies for each country
//		Map<String, Integer> l_countriesArmies = p_gameState.getGameBoard();
//
//		for (Player l_currPlayer : l_allPlayers) {
//			System.out.print(l_currPlayer.getPlayerName() + ": ");
//
//			Set<String> l_countriesOwned = l_currPlayer.getOwnership();
//
//			for (String l_singleCountry : l_countriesOwned) {
//				int l_numOfArmies = l_countriesArmies.get(l_singleCountry);
//				System.out.print("[" + l_singleCountry + ", " + l_numOfArmies + "] ");
//			}
//			System.out.println();
//		}
	}
}
