package map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class reads the map file
 */
public class MapReader {

	public static void readMap(GameMap p_gameMap, String p_mapName) {

		Scanner l_scanner = null;

		try {
			File l_file = new File(p_mapName);
			l_scanner = new Scanner(new FileInputStream(l_file));
		} catch (FileNotFoundException e) {
			System.err.println("Error opening the file");
			l_scanner.close();
			System.exit(1);
		}

		String l_singleLine = l_scanner.nextLine();

		// TODO: check where is empty line considered.
		// to skip names of files
		while (!l_singleLine.equals("[continents]") && l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
		}

		int l_continentId = 0;
		// to fetch continents
		while (!l_singleLine.equals("[countries]") && l_scanner.hasNextLine()) {
			l_continentId++;
			l_singleLine = l_scanner.nextLine();
			readContinents(p_gameMap, l_singleLine, l_continentId);
		}

		// To fetch
		while (!l_singleLine.equals("[borders]") && l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			readCountries(p_gameMap, l_singleLine);
		}

		// To map country neighbors
		while (l_scanner.hasNextLine()) {
			l_singleLine = l_scanner.nextLine();
			readCountryNeighbors(p_gameMap, l_singleLine);
		}

		l_scanner.close();
	}

	/**
	 * 
	 * @param p_singleLine Line under [continents] in map file
	 */
	public static void readContinents(GameMap p_gameMap, String p_singleLine, int p_continentId) {
		String[] l_continentArr = p_singleLine.split("\\s");
		if (l_continentArr.length >= 2)
			p_gameMap.addContinent(p_continentId, l_continentArr[0], Integer.parseInt(l_continentArr[1]));
	}

	/**
	 * 
	 * @param p_singleLine Line under [countries] in map file
	 */
	public static void readCountries(GameMap p_gameMap, String p_singleLine) {
		String[] l_countriesArr = p_singleLine.split("\\s");
		if (l_countriesArr.length >= 3)
			p_gameMap.addCountry(Integer.parseInt(l_countriesArr[0]), l_countriesArr[1],
					Integer.parseInt(l_countriesArr[2]));
	}

	public static void readCountryNeighbors(GameMap p_gameMap, String p_singleLine) {

		String[] l_neighborsArr = p_singleLine.split("\\s+");

		for (int i = 1; i < l_neighborsArr.length; i++) {
			p_gameMap.addNeighbors(Integer.parseInt(l_neighborsArr[0]), Integer.parseInt(l_neighborsArr[i]));
		}
	}
}
