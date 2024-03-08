package map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * The MapReader class represents a utility for reading map data from a file and
 * populating a MapEditor.
 */
public class MapReader {

	/**
	 * Reads map data from a file and populates a MapEditor instance.
	 *
	 * @param p_mapEditor The MapEditor instance to populate.
	 * @param p_fileName  The name of the file containing the map data.
	 * @param p_createNew true if file does not exist and we want to create a new
	 *                    one
	 */
	public static void readMap(MapEditor p_mapEditor, String p_fileName, boolean p_createNew) {

		Scanner l_scanner = null;

		try {
			File l_file = new File(p_fileName);
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
			readContinents(p_mapEditor, l_singleLine, l_continentId);
		}

		// To fetch
		while (!l_singleLine.equals("[borders]") && l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			readCountries(p_mapEditor, l_singleLine);
		}

		// To map country neighbors
		while (l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			readCountryNeighbors(p_mapEditor, l_singleLine);
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
	public static void readContinents(MapEditor p_mapEditor, String p_singleLine, int p_continentId) {
		String[] l_continentArr = p_singleLine.split("\\s");
		if (l_continentArr.length >= 2)
			p_mapEditor.addContinent(p_continentId, l_continentArr[0], Integer.parseInt(l_continentArr[1]));
	}

	/**
	 * Reads country data from a line under [countries] in the map file.
	 *
	 * @param p_mapEditor  The MapEditor instance to which countries are added.
	 * @param p_singleLine The line containing country data.
	 */
	public static void readCountries(MapEditor p_mapEditor, String p_singleLine) {
		String[] l_countriesArr = p_singleLine.split("\\s");
		if (l_countriesArr.length >= 3)
			p_mapEditor.addCountry(Integer.parseInt(l_countriesArr[0]), l_countriesArr[1],
					Integer.parseInt(l_countriesArr[2]));
	}

	/**
	 * Reads country neighbors from a line under [borders] in the map file.
	 *
	 * @param p_mapEditor  The MapEditor instance to which country neighbors are
	 *                     added.
	 * @param p_singleLine The line containing country neighbor data.
	 */
	public static void readCountryNeighbors(MapEditor p_mapEditor, String p_singleLine) {

		String[] l_neighborsArr = p_singleLine.split("\\s+");

		for (int i = 1; i < l_neighborsArr.length; i++) {
			p_mapEditor.addNeighbor(Integer.parseInt(l_neighborsArr[0]), Integer.parseInt(l_neighborsArr[i]));
		}
	}
}
