package map;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import game.GameEngine;

public class DominationMapWriter implements DominationWriter {

	private GameEngine d_gameEngine;

	public DominationMapWriter(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	public void writeDominationFile(String p_mapName) {

		PrintWriter l_printWriter = null;

		try {
			l_printWriter = new PrintWriter(new FileOutputStream(p_mapName));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}

		// to start writing the file
		l_printWriter.println("[files]");
		l_printWriter.println();

		// to write continents
		l_printWriter.println("[continents]");

		for (int i = 1; i <= d_gameEngine.getD_continentId().size(); i++) {
			Continent l_currContinent = d_gameEngine.getD_continentId(i);
			l_printWriter
					.println(l_currContinent.getD_continentName() + " " + l_currContinent.getD_continentBonusArmies());
		}

		l_printWriter.println();

		// to print all the countries to the map
		l_printWriter.println("[countries]");

		// to go inside each continent to know which countries are present inside each
		// continent
		for (int i = 1; i <= d_gameEngine.getD_continentId().size(); i++) {
			Continent l_currContinent = d_gameEngine.getD_continentId().get(i);
			int l_currContinentID = l_currContinent.getD_continentID();
			Set<Country> l_allCountries = l_currContinent.getD_countries();

			for (Country l_country : l_allCountries) {
				int l_countryId = l_country.getD_id();
				String l_countryName = l_country.getD_name();

				l_printWriter.println(l_countryId + " " + l_countryName + " " + "" + l_currContinentID);
			}
		}

		l_printWriter.println();

		// to print the neighbors of all countries

		l_printWriter.println("[borders]");

		// to go inside each country and get the list of neighbors

		for (int i = 1; i <= d_gameEngine.getD_countriesId().size(); i++) {
			Country l_currCountry = d_gameEngine.getD_countriesId().get(i);
			HashSet<Country> l_neighbors = l_currCountry.getNeighbors();
			l_printWriter.print(i + " ");
			for (Country l_currNeighbor : l_neighbors) {
				l_printWriter.print(l_currNeighbor.getD_id() + " ");
			}
			l_printWriter.println();
		}
		// to close the printwriter
		l_printWriter.close();
	}

}
