package map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import game.GameEngineNew;
import models.Continent;
import models.Country;
import phases.PlaySetup;

public class PostLoad extends Edit {

	public PostLoad(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void loadMap(String p_fileName) {
		System.out.println("map has been loaded");
	}

	@Override
	public void next() {
		System.out.println("must save map");
	}

	@Override
	public void editMap(String p_filename) {
		printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		int l_newContinentId = d_gameEngine.getD_continentId().size() + 1;

		if (d_gameEngine.getD_continents().containsKey(p_continentName)) {
			System.out.println("The continent " + p_continentName + " has already been added.");
			return;
		}
		Continent l_continent = new Continent(l_newContinentId, p_continentName, p_bonusArmies);
		d_gameEngine.getD_continents().put(p_continentName, l_continent);

		d_gameEngine.getD_continentId().put(l_newContinentId, l_continent);
		System.out.println("The continent " + d_gameEngine.getD_continents().get(p_continentName).getD_continentName()
				+ " has been added successfully.");
	}

	@Override
	public void removeContinent(String p_continentName) {
		if (d_gameEngine.getD_continents().containsKey(p_continentName)) {
			Continent l_remContinent = d_gameEngine.getD_continents().get(p_continentName);

			// remove it from the list of <int, Continent>
			int l_remContinentId = l_remContinent.getD_continentID();
			d_gameEngine.getD_continentId().remove(l_remContinentId);

			// remove it from the list of <String, Continent>
			String l_remContinentName = l_remContinent.getD_continentName();
			d_gameEngine.getD_continents().remove(l_remContinentName);

			System.out.println("The continent " + p_continentName + " has been removed successfully.");
		} else {
			System.out.println("The continent " + p_continentName + " does not exist.");
		}
	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		if (!d_gameEngine.getD_countries().containsKey(p_countryName)) {
			int l_newCountryId = d_gameEngine.getD_countries().size() + 1;
			Country l_country = new Country(l_newCountryId, p_countryName);

			d_gameEngine.getD_countriesId().put(l_newCountryId, l_country);
			d_gameEngine.getD_countries().put(p_countryName, l_country);

			// To add a country to its continent
			Continent l_continent = d_gameEngine.getD_continents(p_continent);
			l_continent.getD_countries().add(l_country);
			System.out.println("The country " + d_gameEngine.getD_countries().get(p_countryName).getD_name()
					+ " has been added into the continent "
					+ d_gameEngine.getD_continents(p_continent).getD_continentName());
		} else {
			System.out.println(
					"Country " + d_gameEngine.getD_countries().get(p_countryName).getD_name() + " already exists");
		}

	}

	@Override
	public void removeCountry(String p_countryName) {
		if (d_gameEngine.getD_countries().containsKey(p_countryName)) {

			// remove from continent
			Country l_countryToRemove = d_gameEngine.getD_countries().get(p_countryName);
			HashMap<String, Continent> l_listOfContinents = d_gameEngine.getD_continents();
			for (HashMap.Entry<String, Continent> l_continent : l_listOfContinents.entrySet()) {
				Set<Country> l_listOfCountries = l_continent.getValue().getD_countries();
				if (l_listOfCountries.contains(l_countryToRemove)) {
					l_continent.getValue().getD_countries().remove(l_countryToRemove);
					break;
				}
			}

			// to remove from each Countries' neighbors
			HashMap<String, Country> l_listOfCountries = d_gameEngine.getD_countries();
			for (HashMap.Entry<String, Country> l_country : l_listOfCountries.entrySet()) {
				HashSet<Country> l_neighbors = l_country.getValue().getNeighbors();
				for (Country l_countryNeighbor : l_neighbors) {
					if (l_countryNeighbor.equals(l_countryToRemove)) {
						l_neighbors.remove(l_countryToRemove);
						break;
					}
				}
			}

			// get the Integer id of the continent and remove from the Integer HashMap
			int l_countryId = d_gameEngine.getD_countries().get(p_countryName).getD_id();
			d_gameEngine.getD_countriesId().remove(l_countryId);

			// remove from the String HashMap
			d_gameEngine.getD_countries().remove(p_countryName);
			System.out.println("The country " + p_countryName + " has been removed successfully.");

		} else {
			System.out.println("The country " + p_countryName + " does not exist.");
		}

	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		if (!p_country.equalsIgnoreCase(p_neighbor)) {
			if (d_gameEngine.getD_countries().containsKey(p_country)
					&& d_gameEngine.getD_countries().containsKey(p_neighbor)) {

				Country l_country = d_gameEngine.getD_countries().get(p_country);
				Country l_neighbor = d_gameEngine.getD_countries().get(p_neighbor);

				if (!d_gameEngine.getD_countries().get(p_country).getNeighbors().contains(l_neighbor)) {
					l_country.addNeighbors(l_neighbor);
					System.out.println("The country " + p_neighbor + " has been added as the neighbor of " + p_country);
				} else {
					System.err.println("The neighbor " + l_neighbor.getD_name()
							+ " was already added as the neighbor of " + l_country.getD_name());
				}
			} else {
				System.out.println("Either the country or the neighbor does not exist.");
			}
		} else {
			System.out.println("The country " + p_country + " cannot be it's own neighbor.");
		}
	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		if (d_gameEngine.getD_countries().containsKey(p_country)
				&& d_gameEngine.getD_countries().containsKey(p_neighbor)) {
			Country l_country = d_gameEngine.getD_countries().get(p_country);
			Country l_neighbor = d_gameEngine.getD_countries().get(p_neighbor);

			if (l_country.getNeighbors().contains(l_neighbor)) {
				l_country.getNeighbors().remove(l_neighbor);
				System.out.println("The country: " + p_neighbor + " has been removed as the neighbor of " + p_country);
			} else {
				System.err.println("The neighbor " + l_neighbor.getD_name() + " was not present as the neighbor of "
						+ l_country.getD_name());
			}
		} else {
			System.out.println("Either/Both " + p_country + " or " + p_neighbor + " does not exist.");
		}
	}

	@Override
	public void saveMap(String p_filename) {
		System.out.println("map has been saved");

		d_gameEngine.setPhase(new PlaySetup(d_gameEngine));
	}

}
