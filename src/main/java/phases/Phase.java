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
import models.Continent;
import models.Country;

public abstract class Phase {
	protected GameEngine d_gameEngine;

	/**
	 * Constructor
	 * 
	 * @param p_gameEngine a context object for phase
	 */
	public Phase(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
	}

	// general behavior
	abstract public void loadMap(String p_filename);

	abstract public void showMap();

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

	/**
	 * move to the next state(phase)
	 */
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

	// FIXME: User should not be able to do this in preload
	public void addContinent(int p_continentId, String p_continentName, int p_bonusArmies) {
		if (d_gameEngine.getD_continents().containsKey(p_continentName)) {
			// System.out.println("The continent " + p_continentName + " has already been
			// added");
			return;
		}
		Continent l_continent = new Continent(p_continentId, p_continentName, p_bonusArmies);
		d_gameEngine.getD_continents().put(p_continentName, l_continent);

		d_gameEngine.getD_continentId().put(p_continentId, l_continent);
	}

	// FIXME: User should not be able to do this in preload
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

	// FIXME: User should not be able to do this in preload
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

}
