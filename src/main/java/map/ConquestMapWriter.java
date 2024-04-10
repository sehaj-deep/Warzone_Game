package map;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import game.GameEngine;

/**
 * Writes Conquest map data to a file.
 */
public class ConquestMapWriter {

    /** 
	 * The game engine instance. 
	 */
	private GameEngine d_gameEngine;

    /**
     * Constructs a ConquestMapWriter object with a specified game engine.
     *
     * @param p_gameEngine The game engine instance.
     */
	public ConquestMapWriter(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

    /**
     * Writes Conquest map data to a file.
     *
     * @param p_mapName The filename of the Conquest map file to be written.
     */
	public void writeConquestFile(String p_mapName) {

		PrintWriter l_printWriter = null;

		try {
			l_printWriter = new PrintWriter(new FileOutputStream(p_mapName));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}

		// to start writing the file
		l_printWriter.println("[Map]");
		l_printWriter.println();

		// to write continents
		l_printWriter.println("[Continents]");

		for (int i = 1; i <= d_gameEngine.getD_continentId().size(); i++) {
			Continent l_currContinent = d_gameEngine.getD_continentId(i);
			l_printWriter
					.println(l_currContinent.getD_continentName() + "=" + l_currContinent.getD_continentBonusArmies());
		}

		l_printWriter.println();

		// to print all the countries to the map
		l_printWriter.println("[Territories]");

		// to write country for each territory
		for (Country l_country : d_gameEngine.getD_countries().values()) {
			l_printWriter.print(l_country.getD_name() + ",");

			// to write neighbors and continents of each country

			// search in the each continent and check the name of the continent in which
			// this country is present.
			for (Continent l_continent : d_gameEngine.getD_continents().values()) {
				if (l_continent.getD_countries().contains(l_country)) {
					l_printWriter.println(l_continent.getD_continentName() + ",");
					break;
				}
			}
			// write all the neighbors of this country
			StringBuilder l_allNeighbors = new StringBuilder();
			for (Country l_neighbor : l_country.getNeighbors()) {
				l_allNeighbors.append(l_neighbor).append(",");
			}

			// to remove the last comma
			if (!l_country.getNeighbors().isEmpty())
				l_allNeighbors.setLength(l_allNeighbors.length() - 1);

			// print all neighbors
			l_printWriter.print(l_allNeighbors);
		}

		// to close the printwriter
		l_printWriter.close();
	}
}
