package phases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.GameEngine;
import map.Continent;
import map.Country;
import players.Player;
import players.PlayerStrategy;

/**
 * Abstract class representing a phase in the game.
 */
public abstract class Phase implements Serializable {

	/**
	 * The game engine associated with this phase.
	 */
	protected GameEngine d_gameEngine;

	/**
	 * Constructor
	 * 
	 * @param p_gameEngine a context object for phase
	 */
	public Phase(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
	}

	/**
	 * Abstract method to load a map.
	 * 
	 * @param p_filename the filename of the map
	 */
	public abstract void loadMap(String p_filename);

	/**
	 * Abstract method to display the map.
	 */
	abstract public void showMap();

	/**
	 * Abstract method to edit a map.
	 * 
	 * @param p_filename the filename of the map
	 */
	abstract public void editMap(String p_filename);

	/**
	 * Abstract method to add a continent.
	 * 
	 * @param p_continentName the name of the continent
	 * @param p_bonusArmies   the bonus armies for owning the continent
	 */
	abstract public void addContinent(String p_continentName, int p_bonusArmies);

	/**
	 * Abstract method to remove a continent.
	 * 
	 * @param p_continentName the name of the continent to remove
	 */
	abstract public void removeContinent(String p_continentName);

	/**
	 * Abstract method to add a country.
	 * 
	 * @param p_countryName the name of the country
	 * @param p_continent   the continent to which the country belongs
	 */
	abstract public void addCountry(String p_countryName, String p_continent);

	/**
	 * Abstract method to remove a country.
	 * 
	 * @param p_countryName the name of the country to remove
	 */
	abstract public void removeCountry(String p_countryName);

	/**
	 * Abstract method to add a neighbor to a country.
	 * 
	 * @param p_country  the name of the country
	 * @param p_neighbor the name of the neighbor country
	 */
	abstract public void addNeighbor(String p_country, String p_neighbor);

	/**
	 * Abstract method to remove a neighbor from a country.
	 * 
	 * @param p_country  the name of the country
	 * @param p_neighbor the name of the neighbor country to remove
	 */
	abstract public void removeNeighbor(String p_country, String p_neighbor);

	/**
	 * Abstract method to save the current map to a file.
	 *
	 * @param p_filename The filename to save the map to.
	 */
	abstract public void saveMap(String p_filename);

	/**
	 * Abstract method to add players to the game.
	 * 
	 * @param p_playerName     the name of the player to add
	 * @param p_playerStrategy the type of the player to add
	 */
	abstract public void addPlayers(String p_playerName, PlayerStrategy p_playerStrategy);

	/**
	 * Abstract method to remove players from the game.
	 * 
	 * @param p_playerName the name of the player to remove
	 */
	abstract public void removePlayers(String p_playerName);

	/**
	 * Abstract method to assign countries to players.
	 */
	abstract public void assignCountries();

	/**
	 * Abstract method to start tournament mode.
	 * 
	 * @param p_playerStrategies player strategies provided by user
	 */
	abstract public void startTournament(List<String> p_mapFiles, List<String> p_playerStrategies,
			int p_gamesToBePlayed, int p_maxNumberOfTurns);

	/**
	 * Abstract method to end the game.
	 */
	abstract public void endGame();

	/**
	 * move to the next state(phase)
	 */
	abstract public void next();

	/**
	 * save current game state to a file
	 */
	abstract public void saveGame(String p_fileName, Player p_lastPlayer);

	/**
	 * load game state from a file
	 */
	abstract public void loadGame(String p_fileName);

	/**
	 * Prints an invalid command message.
	 */
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in " + this.getClass().getSimpleName() + " phase.");
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
	 * To clear the contents of the map when required.
	 */
	public void clearMap() {
		d_gameEngine.getD_continents().clear();
		d_gameEngine.getD_countries().clear();
	}
}
