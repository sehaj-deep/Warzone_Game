package phases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import game.GameEngine;
import map.Continent;
import map.Country;

/**
 * Abstract class representing a phase in the game.
 */
public abstract class Phase {

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
	 * @param p_playerName the name of the player to add
	 */
	abstract public void addPlayers(String p_playerName);

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
	 * Abstract method to reinforce armies during the reinforcement phase.
	 */
	abstract public void reinforce();

	/**
	 * Abstract method to perform attacks during the attack phase.
	 */
	abstract public void attack();

	/**
	 * Abstract method to fortify armies during the fortification phase.
	 */
	abstract public void fortify();

	/**
	 * Abstract method to end the game.
	 */
	abstract public void endGame();

	/**
	 * move to the next state(phase)
	 */
	abstract public void next();

	/**
	 * Prints an invalid command message.
	 */
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
	 * Reads the map data from the specified file.
	 * 
	 * @param p_filename  The filename of the map file.
	 * @param p_createNew Flag indicating whether to create a new file if not found.
	 */
	public void readMap(String p_filename, boolean p_createNew) {
		Scanner l_scanner = null;

		try {
			File l_file = new File(p_filename);
			if (!l_file.exists() && p_createNew == false) {
				return;
			}
			if (!l_file.exists() && p_createNew) {
				try {
					boolean l_status = l_file.createNewFile();
					if (l_status) {
						System.out.println("New file created.");
					} else {
						throw new FileNotFoundException("File creation failed");
					}

				} catch (IOException e) {
					System.out.println(e.getMessage());
					return;
				}
			}
			l_scanner = new Scanner(new FileInputStream(l_file));
		} catch (FileNotFoundException e) {
			System.err.println("Error opening the file");
			return;
		}

		String l_singleLine = l_scanner.nextLine();

		// to skip names of files
		while (!l_singleLine.equals("[continents]") && l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
		}

		int l_continentId = 0;
		// to fetch continents
		while (!l_singleLine.equals("[countries]") && l_scanner.hasNextLine()) {
			l_continentId++;
			l_singleLine = l_scanner.nextLine();
			this.readContinents(l_singleLine, l_continentId);
		}

		// To fetch
		while (!l_singleLine.equals("[borders]") && l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			this.readCountries(l_singleLine);
		}

		// To map country neighbors
		while (l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			this.readCountryNeighbors(l_singleLine);
		}

		l_scanner.close();
	}

	/**
	 * Reads continent data from a line under [continents] in the map file.
	 *
	 * @param p_singleLine  The line containing continent data.
	 * @param p_continentId The identifier for the continent being read.
	 */
	public void readContinents(String p_singleLine, int p_continentId) {
		String[] l_continentArr = p_singleLine.split("\\s");
		if (l_continentArr.length >= 2)
			this.addContinent(p_continentId, l_continentArr[0], Integer.parseInt(l_continentArr[1]));
	}

	/**
	 * Reads country data from a line under [countries] in the map file.
	 *
	 * @param p_singleLine The line containing country data.
	 */
	public void readCountries(String p_singleLine) {
		String[] l_countriesArr = p_singleLine.split("\\s");
		if (l_countriesArr.length >= 3)
			this.addCountry(Integer.parseInt(l_countriesArr[0]), l_countriesArr[1],
					Integer.parseInt(l_countriesArr[2]));
	}

	/**
	 * Reads country neighbors from a line under [borders] in the map file.
	 *
	 * @param p_singleLine The line containing country neighbor data.
	 */
	public void readCountryNeighbors(String p_singleLine) {

		String[] l_neighborsArr = p_singleLine.split("\\s+");

		for (int i = 1; i < l_neighborsArr.length; i++) {
			this.addNeighbor(Integer.parseInt(l_neighborsArr[0]), Integer.parseInt(l_neighborsArr[i]));
		}
	}

	/**
	 * Adds a continent to the game map.
	 * 
	 * @param p_continentId   The ID of the continent to be added.
	 * @param p_continentName The name of the continent to be added.
	 * @param p_bonusArmies   The bonus armies provided by the continent.
	 */
	public void addContinent(int p_continentId, String p_continentName, int p_bonusArmies) {
		if (d_gameEngine.getD_continents().containsKey(p_continentName)) {
			return;
		}
		Continent l_continent = new Continent(p_continentId, p_continentName, p_bonusArmies);
		d_gameEngine.getD_continents().put(p_continentName, l_continent);

		d_gameEngine.getD_continentId().put(p_continentId, l_continent);
	}

	/**
	 * Adds a country to the game map.
	 * 
	 * @param p_countryId   The ID of the country to be added.
	 * @param p_countryName The name of the country to be added.
	 * @param p_continentId The ID of the continent to which the country belongs.
	 */
	public void addCountry(int p_countryId, String p_countryName, int p_continentId) {

		if (d_gameEngine.getD_countries().containsKey(p_countryName)) {
			System.out.println("The country " + p_countryName + " was already present.");
			return;
		}
		Country l_country = new Country(p_countryId, p_countryName);
		d_gameEngine.getD_countriesId().put(p_countryId, l_country);
		d_gameEngine.getD_countries().put(p_countryName, l_country);

		// To add a country to its continent
		Continent l_continent = d_gameEngine.getD_continentId(p_continentId);
		l_continent.getD_countries().add(l_country);
	}

	/**
	 * Adds a neighbor country to an existing country in the game map.
	 * 
	 * @param p_country  Country to which the neighbor will be added.
	 * @param p_neighbor Neighboring country.
	 */
	public void addNeighbor(int p_country, int p_neighbor) {
		if (d_gameEngine.getD_countriesId().containsKey(p_country)
				&& d_gameEngine.getD_countriesId().containsKey(p_neighbor)) {
			Country l_country = d_gameEngine.getD_countriesId().get(p_country);
			Country l_neighbor = d_gameEngine.getD_countriesId().get(p_neighbor);

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
	 * To clear the contents of the map when required.
	 */
	public void clearMap() {
		d_gameEngine.getD_continents().clear();
		d_gameEngine.getD_countries().clear();
	}

}
