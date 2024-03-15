package map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import game.GameEngineNew;
import models.Continent;
import models.Country;

public class Preload extends Edit {

	public Preload(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

	@Override
	public void editMap(String p_filename) {
		readMap(p_filename, true);
		String[] l_path = p_filename.split("/");
		System.out.println("You are now editing " + l_path[l_path.length - 1]);

		next();
	}

	@Override
	public void loadMap(String p_fileName) {
		printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		printInvalidCommandMessage();
	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(String p_countryName) {
		printInvalidCommandMessage();
	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	@Override
	public void saveMap(String p_filename) {
		printInvalidCommandMessage();
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
