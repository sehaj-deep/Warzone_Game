package map;

import java.util.HashMap;
import java.util.Set;

import game.GameEngine;
import models.Continent;
import models.Country;
import phases.Phase;

public abstract class Edit extends Phase {
	public Edit(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	public void setPlayers() {
		printInvalidCommandMessage();
	}

	public void assignCountries() {
		printInvalidCommandMessage();
	}

	public void reinforce() {
		printInvalidCommandMessage();
	}

	public void attack() {
		printInvalidCommandMessage();
	}

	public void fortify() {
		printInvalidCommandMessage();
	}

	public void endGame() {
		printInvalidCommandMessage();
	}

	public void addPlayers(String p_playerName) {
		printInvalidCommandMessage();
	}

	public void removePlayers(String p_playerName) {
		printInvalidCommandMessage();
	}

	public void showMap() {
		System.out.println("The following is the text format of the map");
		System.out.println("----------------------------------------------------------------------");

		HashMap<String, Continent> l_continents = d_gameEngine.getD_continents();
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

}
