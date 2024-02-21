package map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.GameState;
import game.Player;
import models.Continent;
import models.Country;
import utils.ValidationException;

/**
 * The MapEditor class manages editing of the game map.
 */
public class MapEditor {

	/**
	 * A hashmap to store the continents
	 */
	private HashMap<String, Continent> d_continents = new HashMap<>();

	/**
	 * To map continent id with continent name
	 */
	private HashMap<Integer, Continent> d_continentId = new HashMap<>();

	/**
	 * A hashmap to store the countries
	 */
	private HashMap<String, Country> d_countries = new HashMap<>();

	/**
	 * To map countries id with country name
	 */
	private HashMap<Integer, Country> d_countriesId = new HashMap<>();

	/**
	 * The name of the map.
	 */
	private String d_mapName;

	/**
	 * Default constructor of the MapEditor class
	 */
	public MapEditor() {

	}

	/**
	 * 
	 * @return the list of continents
	 */
	public HashMap<String, Continent> getD_continents() {
		return d_continents;
	}

	/**
	 * 
	 * @param p_continentName Unique name of the continent
	 * @return the required continent mapped to continent ID
	 */
	public Continent getD_Continents(String p_continentName) {
		return d_continents.get(p_continentName);
	}

	/**
	 * 
	 * @return the list of continents mapped to corresponding ID
	 */
	public HashMap<Integer, Continent> getD_continentId() {
		return d_continentId;
	}

	/**
	 * 
	 * @param p_continentId The Id of the continent
	 * @return return the Object of the continent corresponding to an Id.
	 */
	public Continent getD_continentId(int p_continentId) {
		return d_continentId.get(p_continentId);
	}

	/**
	 * Retrieves the hashmap containing the countries.
	 *
	 * @return The hashmap containing the countries.
	 */
	public HashMap<String, Country> getD_countries() {
		return d_countries;
	}

	/**
	 * Retrieves the country object with the specified country name.
	 *
	 * @param p_countryName The name of the country to retrieve.
	 * @return The country object corresponding to the given country name.
	 */
	public Country getD_countries(String p_countryName) {
		return d_countries.get(p_countryName);
	}

	/**
	 * 
	 * @return the name of the country corresponding to the id.
	 */
	public HashMap<Integer, Country> getD_countriesId() {
		return d_countriesId;
	}

	/**
	 * 
	 * @return name of the map
	 */
	public String getD_mapName() {
		return d_mapName;
	}

	/**
	 * set the name of the map
	 * 
	 * @param d_mapName Name of the map file
	 */
	public void setD_mapName(String d_mapName) {
		this.d_mapName = d_mapName;
	}

	/**
	 * Track each continent.
	 * 
	 * @param p_continentId   The ID of given continent
	 * @param p_continentName The name of the given continent
	 * @param p_bonusArmies   Number of bonus armies earned by capturing a
	 *                        particular continent
	 */
	public void addContinent(int p_continentId, String p_continentName, int p_bonusArmies) {
		if (this.getD_continents().containsKey(p_continentName)) {
			System.out.println("The continent " + p_continentName + " has already been added");
			return;
		}
		Continent l_continent = new Continent(p_continentId, p_continentName, p_bonusArmies);
		this.getD_continents().put(p_continentName, l_continent);

		this.getD_continentId().put(p_continentId, l_continent);
	}

	/**
	 * To add a new continent from user command
	 * 
	 * @param p_continentName The name of the given continent
	 * @param p_bonusArmies   Number of bonus armies earned by capturing a
	 *                        particular continent
	 */
	public void addContinent(String p_continentName, int p_bonusArmies) {

		int l_newContinentId = this.getD_continentId().size() + 1;

		if (this.getD_continents().containsKey(p_continentName)) {
			System.out.println("The continent " + p_continentName + " has already been added.");
			return;
		}
		Continent l_continent = new Continent(l_newContinentId, p_continentName, p_bonusArmies);
		this.getD_continents().put(p_continentName, l_continent);

		this.getD_continentId().put(l_newContinentId, l_continent);
		System.out.println("The continent " + this.getD_continents().get(p_continentName).getD_continentName()
				+ " has been added successfully.");
	}

	/**
	 * To remove a continent based on the commands.
	 * 
	 * @param p_continentName The name of the given continent
	 */
	public void removeContinent(String p_continentName) {
		if (this.getD_continents().containsKey(p_continentName)) {
			Continent l_remContinent = this.getD_continents().get(p_continentName);

			// remove it from the list of <int, Continent>
			int l_remContinetId = l_remContinent.getD_continentID();
			d_continentId.remove(l_remContinetId);

			// remove it from the list of <String, Continent>
			String l_remContinentName = l_remContinent.getD_continentName();
			d_continents.remove(l_remContinentName);

			System.out.println("The continent " + p_continentName + " has been removed successfully.");
		} else {
			System.out.println("The continent " + p_continentName + " does not exist.");
		}

	}

	/**
	 * Adds a new country to the map.
	 *
	 * @param p_countryId   The ID of the country to be added.
	 * @param p_countryName The name of the country to be added.
	 * @param p_continentId The ID of the continent to which the country belongs.
	 */
	public void addCountry(int p_countryId, String p_countryName, int p_continentId) {

		if (this.getD_countries().containsKey(p_countryName)) {
			System.out.println("The country " + p_countryName + " was already present.");
			return;
		}
		Country l_country = new Country(p_countryId, p_countryName);
		this.getD_countriesId().put(p_countryId, l_country);
		this.getD_countries().put(p_countryName, l_country);

		// To add a country to its continent
		Continent l_continent = this.getD_continentId(p_continentId);
		l_continent.getD_countries().add(l_country);
	}

	/**
	 * Adds a new country to the map.
	 *
	 * @param p_countryName The name of the country to be added.
	 * @param p_continent   The name of the continent to which the country belongs.
	 */
	public void addCountry(String p_countryName, String p_continent) {

		if (!this.getD_countries().containsKey(p_countryName)) {
			int l_newCountryId = this.getD_countries().size() + 1;
			Country l_country = new Country(l_newCountryId, p_countryName);

			this.getD_countriesId().put(l_newCountryId, l_country);
			this.getD_countries().put(p_countryName, l_country);

			// To add a country to its continent
			Continent l_continent = this.getD_Continents(p_continent);
			l_continent.getD_countries().add(l_country);
			System.out.println("The country " + this.getD_countries().get(p_countryName).getD_name()
					+ " has been added into the continent " + this.getD_Continents(p_continent).getD_continentName());
		} else {
			System.out.println("Country " + this.getD_countries().get(p_countryName).getD_name() + " already exists");
		}
	}

	/**
	 * Removes the given country from the map.
	 * 
	 * @param p_countryName The name of the country
	 */
	public void removeCountry(String p_countryName) {
		if (this.getD_countries().containsKey(p_countryName)) {

			// remove from continent
			Country l_countryToRemove = this.getD_countries().get(p_countryName);
			HashMap<String, Continent> l_listOfContinents = this.getD_continents();
			for (HashMap.Entry<String, Continent> l_continent : l_listOfContinents.entrySet()) {
				Set<Country> l_listOfCountries = l_continent.getValue().getD_countries();
				if (l_listOfCountries.contains(l_countryToRemove)) {
					l_continent.getValue().getD_countries().remove(l_countryToRemove);
					break;
				}
			}

			// to remove from each Countries' neighbors
			HashMap<String, Country> l_listOfCountries = this.getD_countries();
			for (HashMap.Entry<String, Country> l_country : l_listOfCountries.entrySet()) {
				HashSet<Country> l_neighbors = l_country.getValue().getNeighbors();
				for (Country l_countryNeighbor : l_neighbors) {
					if (l_countryNeighbor.equals(l_countryToRemove)) {
						l_neighbors.remove(l_countryToRemove);
						break;
					}
				}
			}

			// get the Integer id of the continent and remove from the Integer HashMap
			int l_countryId = this.getD_countries().get(p_countryName).getD_id();
			this.getD_countriesId().remove(l_countryId);

			// remove from the String HashMap
			this.getD_countries().remove(p_countryName);
			System.out.println("The country " + p_countryName + " has been removed successfully.");

		} else {
			System.out.println("The country " + p_countryName + " does not exist.");
		}
	}

	/**
	 * Adds a neighbor to the specified country
	 * 
	 * @param p_country  The country to which we need to add the neighbor
	 * @param p_neighbor The country which needs to be added as the neighbor
	 */
	public void addNeighbor(String p_country, String p_neighbor) {

		if (!p_country.equalsIgnoreCase(p_neighbor)) {
			if (this.getD_countries().containsKey(p_country) && this.getD_countries().containsKey(p_neighbor)) {

				Country l_country = this.getD_countries().get(p_country);
				Country l_neighbor = this.getD_countries().get(p_neighbor);

				if (!this.getD_countries().get(p_country).getNeighbors().contains(l_neighbor)) {
					l_country.addNeighbors(l_neighbor);
					System.out.println("The country " + p_neighbor + " has been added as the neighbor of " + p_country);
				} else {
					System.err.println("The neighbor " + l_neighbor.getD_name()
							+ " was already added as the neighbor of " + l_country.getD_name());
				}
			} else {
				System.out.println("Either the country or the neighbor does not exist.");
			}
		} else {
			System.out.println("The country " + p_country + " cannot be it's own neighbor.");
		}

	}

	/**
	 * Adds a neighbor to the specified country
	 * 
	 * @param p_country  The id of the country to which we need to add the neighbor
	 * @param p_neighbor The id of the country which needs to be added as the
	 *                   neighbor
	 */
	public void addNeighbor(int p_country, int p_neighbor) {
		if (this.getD_countriesId().containsKey(p_country) && this.getD_countriesId().containsKey(p_neighbor)) {
			Country l_country = this.getD_countriesId().get(p_country);
			Country l_neighbor = this.getD_countriesId().get(p_neighbor);

			if (!l_country.getNeighbors().contains(l_neighbor)) {
				l_country.addNeighbors(l_neighbor);
			} else {
				System.err.println("The neighbor " + l_neighbor.getD_name() + " was already added as the neighbor of "
						+ l_country.getD_name());
			}
		} else {
			System.out.println("The country " + p_country + " already has " + p_neighbor + " as its neighbor.");
		}
	}

	/**
	 * Removes a neighbor from the specified country.
	 *
	 * @param p_country  The name of the country from which the neighbor will be
	 *                   removed.
	 * @param p_neighbor The name of the neighbor country to be removed.
	 */
	public void removeNeighbor(String p_country, String p_neighbor) {
		if (this.getD_countries().containsKey(p_country) && this.getD_countries().containsKey(p_neighbor)) {
			Country l_country = this.getD_countries().get(p_country);
			Country l_neighbor = this.getD_countries().get(p_neighbor);

			if (l_country.getNeighbors().contains(l_neighbor)) {
				l_country.getNeighbors().remove(l_neighbor);
				System.out.println("The country: " + p_neighbor + " has been removed as the neighbor of " + p_country);
			} else {
				System.err.println("The neighbor " + l_neighbor.getD_name() + " was not present as the neighbor of "
						+ l_country.getD_name());
			}
		} else {
			System.out.println("Either/Both " + p_country + " or " + p_neighbor + " does not exist.");
		}
	}

	/**
	 * Displays the map as text during the Map Editor Phase.
	 */
	public void showMap() {
		// for all continents
		System.out.println("The following is the text format of the map");
		System.out.println("----------------------------------------------------------------------");

		HashMap<String, Continent> l_continents = this.getD_continents();
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
	}

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
		for (HashMap.Entry<String, Continent> continentEntry : d_continents.entrySet()) {
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
		Country l_startCountry = getD_countries().values().iterator().next();

		depthFirstSearchForMap(l_startCountry, l_visitedCountries);

		return l_visitedCountries.size() == getD_countries().size();
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
	 * Loads a map from an existing map file, or creates a new map from scratch if
	 * the file does not exist.
	 * 
	 * @param p_mapEditor The object of the map Editor currently in use
	 * @param p_filename  The name of the file containing the map data.
	 * 
	 */
	public void editMap(MapEditor p_mapEditor, String p_filename) {
		MapReader.readMap(p_mapEditor, p_filename, true);
		String[] l_path = p_filename.split("/");
		System.out.println("You are now editing " + l_path[l_path.length - 1]);
	}

	/**
	 * Loads a map from an existing map file
	 * 
	 * @param p_mapEditor The object of the map Editor currently in use
	 * @param p_filename  The name of the file containing the map data.
	 */
	public void loadMap(MapEditor p_mapEditor, String p_filename) {

		MapReader.readMap(p_mapEditor, p_filename, false);
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

	/**
	 * Saves the current map to a file.
	 *
	 * @param p_mapEditor The object of the map Editor currently in use.
	 * @param p_filename  The name of the file to save the map data to.
	 */
	public void saveMap(MapEditor p_mapEditor, String p_filename) {
		try {
			boolean l_isValidated = validateMap();
			if (!l_isValidated) {
				throw new ValidationException("Unable to save map: The map is invalid.");
			}
		} catch (ValidationException e) {
			System.out.print(e.getMessage());
		}

		MapWriter l_mapWriter = new MapWriter(p_mapEditor);
		l_mapWriter.fileWrite(p_filename);
		System.out.println("The map has been saved successfully into the file: " + p_filename);
	}

	/**
	 * Displays the map and player information during the game.
	 *
	 * @param p_gameState The current state of the game.
	 */
	public void showmap(GameState p_gameState) {

		System.out.println("The following is the text format of the map");
		System.out.println("----------------------------------------------------------------------");

		HashMap<String, Continent> l_continents = this.getD_continents();
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
		List<Player> l_allPlayers = p_gameState.getPlayers();
		if (l_allPlayers.size() == 0) {
			return;
		}

		HashMap<String, Integer> l_countriesArmies = p_gameState.getGameBoard();

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
}
