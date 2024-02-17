package map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import models.Continent;
import models.Country;

public class GameMap {

	/**
	 * A hashmap to store the continents
	 */
	private HashMap<String, Continent> d_continents = new HashMap<>();

	// TODO: Discuss if we need this
	/**
	 * To map continent id with continent name
	 */
	private HashMap<Integer, Continent> d_continentId = new HashMap<>();

	/**
	 * A hashmap to store the countries
	 */
	private HashMap<String, Country> d_countries = new HashMap<>();

	// TODO: Discuss if we need this
	/**
	 * To map countries id with country name
	 */
	private HashMap<Integer, Country> d_countriesId = new HashMap<>();

	/**
	 * To store the name
	 */
	private String d_mapName;

	/**
	 * Default constructor of the GameMap class
	 */
	public GameMap() {

	}

	/**
	 * 
	 * @return the list of continents
	 */
	private HashMap<String, Continent> getD_Continents() {
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

	public HashMap<String, Country> getD_countries() {
		return d_countries;
	}

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
		if (this.getD_Continents().containsKey(p_continentName)) {
			// TODO: validate that the continent has not been already added.
			// TODO: throw an exception here
		}
		Continent l_continent = new Continent(p_continentId, p_continentName, p_bonusArmies);
		this.getD_Continents().put(p_continentName, l_continent);

		// TODO: Delete this if not required.
		this.getD_continentId().put(p_continentId, l_continent);
		// TODO: replace the below statement with either the logger or by deleting it
		// System.out.println("Successfully added the continent: " + p_continentName);
	}

	/**
	 * To add a new continent from user command
	 * 
	 * @param p_continentName The name of the given continent
	 * @param p_bonusArmies   Number of bonus armies earned by capturing a
	 *                        particular continent
	 */
	public void addContinent(String p_continentName, int p_bonusArmies) {
		// handling the case where the continent is already present.

		int l_newContinentId = this.getD_continentId().size() + 1;

		if (this.getD_Continents().containsKey(p_continentName)) {
			// TODO: validate that the continent has not been already added.
			// TODO: throw an exception here
		}
		Continent l_continent = new Continent(l_newContinentId, p_continentName, p_bonusArmies);
		this.getD_Continents().put(p_continentName, l_continent);

		// TODO: Delete this if not required.
		this.getD_continentId().put(l_newContinentId, l_continent);
		// TODO: replace the below statement with either the logger or by deleting it
		// System.out.println("Successfully added the continent: " + p_continentName);
	}

	/**
	 * To remove a continent based on the commands.
	 * 
	 * @param p_continentName The name of the given continent
	 */
	public void removeContinent(String p_continentName) {
		// check if this continent is present, if not throw an exception
		if (!this.getD_Continents().containsKey(p_continentName)) {
			Continent l_remContinent = this.getD_Continents().get(p_continentName);

			// remove it from the list of <int, Continent>
			int l_remContinentId = l_remContinent.getD_continentID();
			d_continentId.remove(l_remContinent);

			// remove it from the list of <String, Continent>
			String l_remContinentName = l_remContinent.getD_continentName();
			d_continents.remove(l_remContinentName);
		} else {
			// TODO: throw an exception and catch it in calling function.
		}

	}

	public void addCountry(int p_countryId, String p_countryName, int p_continentId) {
		// TODO: validate if this country has already been added
		// TODO: throw an exception here

		Country l_country = new Country(p_countryId, p_countryName);
		this.getD_countriesId().put(p_countryId, l_country);
		this.getD_countries().put(p_countryName, l_country);

		// To add a country to its continent
		Continent l_continent = this.getD_continentId(p_continentId);
		l_continent.getD_countries().add(l_country);
		// TODO: handle the case that the country should not be already present.
	}

	/**
	 * To add a country to a continent
	 * 
	 * @param p_countryName The name of the country
	 * @param p_continent   The name of the continent in which this country is
	 *                      present
	 */
	public void addCountry(String p_countryName, String p_continent) {
		// TODO: validate if this country has already been added
		// TODO: throw an exception here

		if (!this.getD_countries().containsKey(p_countryName)) {
			int l_newCountryId = this.getD_countries().size() + 1;
			Country l_country = new Country(l_newCountryId, p_countryName);

			this.getD_countriesId().put(l_newCountryId, l_country);
			this.getD_countries().put(p_countryName, l_country);

			// To add a country to its continent
			Continent l_continent = this.getD_Continents(p_continent);
			l_continent.getD_countries().add(l_country);
		} else {
			// TODO: throw an exception
		}
	}

	/**
	 * To remove the given country from the map.
	 * 
	 * @param p_countryName The name of the country
	 */
	public void removeCountry(String p_countryName) {
		if (!this.getD_countries().containsKey(p_countryName)) {
			// get the Integer id of the continent and remove from the Integer HashMap
			int l_countryId = this.getD_countries().get(p_countryName).getD_id();
			this.getD_countriesId().remove(l_countryId);

			// remove from the String HashMap
			this.getD_countries().remove(p_countryName);
		} else {
			// TODO: throw an exception
		}
	}

	/**
	 * To add the neighbor to a country using commands
	 * 
	 * @param p_country  The country to which we need to add the neighbor
	 * @param p_neighbor The country which needs to be added as the neighbor
	 */
	public void addNeighbors(String p_country, String p_neighbor) {
		// check if the country exists, if the neighbor exists and if the neighbor is
		// already been added to the country.

		if (this.getD_countries().containsKey(p_country) && this.getD_countries().containsKey(p_neighbor)) {

			Country l_country = this.getD_countries().get(p_country);
			Country l_neighbor = this.getD_countries().get(p_neighbor);
			if (!this.getD_countries().get(p_country).getNeighbors().contains(p_neighbor)) {
				l_country.addNeighbors(l_neighbor);
			} else {
				System.err.println("The neighbor " + l_neighbor.getD_name() + " was already added as the neighbor of "
						+ l_country.getD_name());
			}
		} else {
			// TODO: throw an exception that the country or neighbor not given in list of
			// countries
		}
	}

	/**
	 * To add the neighbor to a country through the
	 * 
	 * @param p_country  The id of the country to which we need to add the neighbor
	 * @param p_neighbor The id of the country which needs to be added as the
	 *                   neighbor
	 */
	public void addNeighbors(int p_country, int p_neighbor) {
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
			// TODO: throw an exception that the country or neighbor not given in list of
			// countries
		}
	}

	public void removeNeighbor(String p_country, String p_neighbor) {
		if (this.getD_countriesId().containsKey(p_country) && this.getD_countriesId().containsKey(p_neighbor)) {
			Country l_country = this.getD_countriesId().get(p_country);
			Country l_neighbor = this.getD_countriesId().get(p_neighbor);

			if (l_country.getNeighbors().contains(l_neighbor)) {
				l_country.getNeighbors().remove(l_neighbor);
			} else {
				System.err.println("The neighbor " + l_neighbor.getD_name() + " was not present as the neighbor of "
						+ l_country.getD_name());
			}
		} else {
			// TODO: throw an exception that the country or neighbor not given in list of
			// countries
		}
	}

	/**
	 * Show Map during Map Editor Phase.
	 * 
	 * @param p_gameMap object of GameMap Class
	 */
	public void showMap(GameMap p_gameMap) {
		// for all continents
		System.out.println("The following is the text format of the map");
		System.out.println("----------------------------------------------------------------------");
		HashMap<String, Continent> l_continents = p_gameMap.getD_Continents();
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
		boolean l_isMapConnectedGraph = traverseMapGraph();
		boolean l_isContinentConnectedGraph = false;

		if (!l_isMapConnectedGraph) {
			return false;
		}

		for (HashMap.Entry<String, Continent> l_continentEntry : d_continents.entrySet()) {

			l_isContinentConnectedGraph = traverseContinentGraph(l_continentEntry.getValue());

			if (!l_isContinentConnectedGraph) {
				return false;
			}
		}
		return l_isMapConnectedGraph && l_isContinentConnectedGraph;
	}

	/**
	 * Traverses the map graph using depth-first search algorithm to visit all
	 * countries.
	 * 
	 * @return true if all countries have been visited, false otherwise.
	 */
	public boolean traverseMapGraph() {
		Set<Country> l_visitedCountries = new HashSet<>();
		Country l_startCountry = getD_countries().values().iterator().next();

		depthFirstSearch(l_startCountry, l_visitedCountries);

		return l_visitedCountries.size() == getD_countries().size();
	}

	/**
	 * Traverses the graph of countries within the specified continent using
	 * depth-first search algorithm.
	 * 
	 * @param p_continent The continent whose countries are to be traversed.
	 * @return true if all countries within the continent have been visited, false
	 *         otherwise.
	 */
	public boolean traverseContinentGraph(Continent p_continent) {
		Set<Country> l_countriesInContinent = p_continent.getD_countries();
		Set<Country> l_visitedCountries = new HashSet<>();
		Country startCountry = l_countriesInContinent.iterator().next();

		depthFirstSearch(startCountry, l_visitedCountries);

		return l_visitedCountries.size() == l_countriesInContinent.size();
	}

	/**
	 * Performs depth-first search traversal starting from the specified country.
	 * 
	 * @param p_country          The starting country for the traversal.
	 * @param p_visitedCountries Set to store visited countries during traversal.
	 */
	private void depthFirstSearch(Country p_country, Set<Country> p_visitedCountries) {
		p_visitedCountries.add(p_country);
		Set<Country> l_neighboringCountries = p_country.getNeighbors();

		for (Country l_neighbor : l_neighboringCountries) {
			if (!p_visitedCountries.contains(l_neighbor)) {
				depthFirstSearch(l_neighbor, p_visitedCountries);
			}
		}
	}
}
