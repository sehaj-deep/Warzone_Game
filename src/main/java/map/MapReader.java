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
		
		Scanner  l_sc = null;
		
		try {
			File l_file = new File(p_mapName);
			l_sc = new Scanner(new FileInputStream(l_file));
		}
		catch(FileNotFoundException e) {
			System.out.println("Error opening the file");
			System.exit(1);
		}
		
		String l_singleLine = l_sc.nextLine();
		
		//TODO: check where is empty line considered.
		//to skip names of files
		while(!l_singleLine.equals("[continents]")&& l_sc.hasNextLine()) {
			l_singleLine = l_sc.nextLine();
		}
		
		int l_continentId = 0;
		//to fetch continents
		while(!l_singleLine.equals("[countries]") && l_sc.hasNextLine()) {
			l_continentId++;
			l_singleLine = l_sc.nextLine();
			readContinents(p_gameMap, l_singleLine, l_continentId);
		}
		
		//To fetch countries
		while(!l_singleLine.equals("[borders]") && l_sc.hasNextLine()) {
			l_singleLine = l_sc.nextLine();
			readCountries(p_gameMap, l_singleLine);
		}
		
		//To map neighbors
		while(l_sc.hasNextLine()) {
			l_singleLine = l_sc.nextLine();
			readNeighbors(p_gameMap, l_singleLine);
		}
		
	}
	
	/**
	 * 
	 * @param p_singleLine Line under [continents] in map file
	 */
	public static void readContinents(GameMap p_gameMap, String p_singleLine, int p_continentID) {
		String[] l_continentArr = p_singleLine.split("\\s");
		
	}
	
	/**
	 * 
	 * @param p_singleLine Line under [countries] in map file
	 */
	public static void readCountries(GameMap p_gameMap, String p_singleLine) {
		
	}
	
	public static void readNeighbors(GameMap p_gameMap, String p_singleLine) {
		
	}
}
