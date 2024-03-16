package phases;

import java.util.HashMap;
import java.util.Set;

import game.GameEngineNew;
import models.Continent;
import models.Country;

public abstract class Play extends Phase {
	/**
	 * Constructor
	 * 
	 * @param p_gameEngine a context object for Play phase
	 */
	public Play(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
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

		// to get the list of players
		// FIXME: Find how to get list of players
//		List<Player> l_allPlayers = d_gameEngine.getD_players();
//		if (l_allPlayers.size() == 0) {
//			return;
//		}
//
//		// FIXME: Find how to get list of armies for each country
//		Map<String, Integer> l_countriesArmies = p_gameState.getGameBoard();
//
//		for (Player l_currPlayer : l_allPlayers) {
//			System.out.print(l_currPlayer.getPlayerName() + ": ");
//
//			Set<String> l_countriesOwned = l_currPlayer.getOwnership();
//
//			for (String l_singleCountry : l_countriesOwned) {
//				int l_numOfArmies = l_countriesArmies.get(l_singleCountry);
//				System.out.print("[" + l_singleCountry + ", " + l_numOfArmies + "] ");
//			}
//			System.out.println();
	}

	public void editCountry() {
		printInvalidCommandMessage();
	}

	public void saveMap() {
		printInvalidCommandMessage();
	}

	public void endGame() {
		// TODO
		// d_gameEngine.setPhase(new End(d_gameEngine)));
	}
}
