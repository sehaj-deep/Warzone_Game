package map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import game.GameEngine;

/**
 * The DominationMapReader class is responsible for reading domination map files and constructing
 * the game map accordingly.
 */
public class DominationMapReader implements DominationReader {

    /** 
	 * The GameEngine instance associated with this reader
	 */
	private GameEngine d_gameEngine;

    /**
     * Constructs a DominationMapReader with the specified GameEngine instance.
     *
     * @param p_gameEngine The GameEngine instance to associate with this reader.
     */
	public DominationMapReader(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

    /**
     * Reads a domination map file and constructs the game map accordingly.
     *
     * @param p_fileName  The name of the domination map file.
     * @param p_createNew Indicates whether to create a new file if it doesn't exist.
     */
	public void readDominationMap(String p_fileName, boolean p_createNew) {

		Scanner l_scanner = null;

		try {
			File l_file = new File(p_fileName);
			if (!l_file.exists() && p_createNew == false) {
				return;
			}
			if (!l_file.exists() && p_createNew) {
				try {
					l_file.createNewFile();
					System.out.println("New file created.");
					return;
				} catch (IOException e) {
					System.out.println("File creation failed");
				}
			}
			l_scanner = new Scanner(new FileInputStream(l_file));
		} catch (FileNotFoundException e) {
			System.err.println("Error opening the file");
			l_scanner.close();
			System.exit(0);
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
			readContinents(l_singleLine, l_continentId);
		}

		// To fetch
		while (!l_singleLine.equals("[borders]") && l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			readCountries(l_singleLine);
		}

		// To map country neighbors
		while (l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			readCountryNeighbors(l_singleLine);
		}

		l_scanner.close();
	}

	/**
	 * Reads continent data from a line under [continents] in the map file.
	 *
	 * @param p_mapEditor   The MapEditor instance to which continents are added.
	 * @param p_singleLine  The line containing continent data.
	 * @param p_continentId The identifier for the continent being read.
	 */
	public void readContinents(String p_singleLine, int p_continentId) {
		String[] l_continentArr = p_singleLine.split("\\s");
		if (l_continentArr.length >= 2)
			addContinent(p_continentId, l_continentArr[0], Integer.parseInt(l_continentArr[1]));
	}

	/**
	 * Reads country data from a line under [countries] in the map file.
	 *
	 * @param p_mapEditor  The MapEditor instance to which countries are added.
	 * @param p_singleLine The line containing country data.
	 */
	public void readCountries(String p_singleLine) {
		String[] l_countriesArr = p_singleLine.split("\\s");
		if (l_countriesArr.length >= 3)
			addCountry(Integer.parseInt(l_countriesArr[0]), l_countriesArr[1], Integer.parseInt(l_countriesArr[2]));
	}

	/**
	 * Reads country neighbors from a line under [borders] in the map file.
	 *
	 * @param p_mapEditor  The MapEditor instance to which country neighbors are
	 *                     added.
	 * @param p_singleLine The line containing country neighbor data.
	 */
	public void readCountryNeighbors(String p_singleLine) {

		String[] l_neighborsArr = p_singleLine.split("\\s+");

		for (int i = 1; i < l_neighborsArr.length; i++) {
			addNeighbor(Integer.parseInt(l_neighborsArr[0]), Integer.parseInt(l_neighborsArr[i]));
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

}
