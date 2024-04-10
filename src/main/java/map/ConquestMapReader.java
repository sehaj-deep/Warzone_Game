package map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import game.GameEngine;

/**
 * Reads and processes a Conquest map file.
 */
public class ConquestMapReader implements Serializable {

	/** 
	 * The game engine instance. 
	 */
	private GameEngine d_gameEngine;

	/**
     * Constructs a ConquestMapReader object with a specified game engine.
     *
     * @param p_gameEngine The game engine instance.
     */
	public ConquestMapReader(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
     * Reads a Conquest map file and processes it.
     *
     * @param p_fileName  The filename of the Conquest map file.
     * @param p_createNew Indicates whether to create a new file if not found.
     */
	public void readConquestMap(String p_fileName, boolean p_createNew) {
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

		// to skip content above [Continents]
		while (!l_singleLine.equals("[Continents]") && l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
		}

		// to skip [Continents] line
		l_singleLine = l_scanner.nextLine();

		// to map Continents into Hashmap
		while (!l_singleLine.equals("[Territories]") && l_scanner.hasNextLine()) {
			readContinents(l_singleLine);
			l_singleLine = l_scanner.nextLine();
		}

		// to map countries and neighbors
		while (l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			readCountriesAndNeighbors(l_singleLine);
		}
	}

    /**
     * Processes the continent information from a line of text.
     *
     * @param p_singleLine The line of text containing continent information.
     */
	public void readContinents(String p_singleLine) {
		if (p_singleLine.length() > 1) {
			String[] l_continentArr = p_singleLine.split("=");
			addContinent(l_continentArr[0], Integer.parseInt(l_continentArr[1]));
		}
	}

    /**
     * Processes the country and its neighbors information from a line of text.
     *
     * @param p_singleLine The line of text containing country and neighbors information.
     */
	public void readCountriesAndNeighbors(String p_singleLine) {
		if (p_singleLine.length() > 1 && p_singleLine.contains(",")) {
			String[] l_countryNames = p_singleLine.split("\\,");
			String l_country = l_countryNames[0];
			String l_continentName = "";
			for (int i = 1; i < l_countryNames.length; i++) {
				if (Character.isDigit(l_countryNames[i].charAt(0))) {
					continue;
				}
				if (l_continentName.equals("")) {
					l_continentName = l_countryNames[i];
					addCountry(l_country, l_continentName);
				} else {
					addNeighbor(l_country, l_countryNames[i]);
				}
			}
		}
	}

    /**
     * Adds a continent to the game engine's data.
     *
     * @param p_continentName The name of the continent to be added.
     * @param p_bonusArmies   The bonus armies for the continent.
     */
	public void addContinent(String p_continentName, int p_bonusArmies) {
		if (!d_gameEngine.getD_continents().containsKey(p_continentName)) {
			Continent l_continent = new Continent(p_continentName, p_bonusArmies);
			d_gameEngine.getD_continents().put(p_continentName, l_continent);
		}
	}

    /**
     * Adds a country to the game engine's data.
     *
     * @param p_countryName  The name of the country to be added.
     * @param p_continentName The name of the continent to which the country belongs.
     */
	public void addCountry(String p_countryName, String p_continentName) {
		// to check that the country has not been already added to the list
		if (d_gameEngine.getD_countries().containsKey(p_countryName)) {
			if (d_gameEngine.getD_continents().containsKey(p_continentName)) {
				Continent l_continent = d_gameEngine.getD_continents().get(p_continentName);
				Country l_country = d_gameEngine.getD_countries().get(p_countryName);
				l_continent.addD_countries(l_country);
			} else {
				System.out.println("The continent " + p_continentName + " does not exist.");
			}
		} else {
			Country l_country = new Country(p_countryName);
			d_gameEngine.getD_countries().put(p_countryName, l_country);
			Continent l_associatedContinent = d_gameEngine.getD_continents().get(p_continentName);
			l_associatedContinent.addD_countries(l_country);
		}
	}

    /**
     * Adds a neighbor to a country.
     *
     * @param p_countryName  The name of the country to which the neighbor will be added.
     * @param p_neighborName The name of the neighbor country to be added.
     */
	public void addNeighbor(String p_countryName, String p_neighborName) {
		Country l_country;
		Country l_neighbor;

		if (d_gameEngine.getD_countries().containsKey(p_countryName)
				&& d_gameEngine.getD_countries().containsKey(p_neighborName)) {

			l_country = d_gameEngine.getD_countries().get(p_countryName);
			l_neighbor = d_gameEngine.getD_countries().get(p_neighborName);

		} else if (d_gameEngine.getD_countries().containsKey(p_countryName)
				&& !d_gameEngine.getD_countries().containsKey(p_neighborName)) {

			l_country = d_gameEngine.getD_countries().get(p_countryName);
			l_neighbor = new Country(p_neighborName);

		} else {
			System.out.println("The country " + p_countryName + " does not exist.");
			return;
		}

		if (!l_country.getNeighbors().contains(l_neighbor)) {
			l_country.addNeighbors(l_neighbor);
		} else {
			System.err.println("The neighbor " + l_neighbor.getD_name() + " was already added as the neighbor of "
					+ l_country.getD_name());
		}
	}

}
