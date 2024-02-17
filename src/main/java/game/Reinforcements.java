package game;

import java.util.HashMap;
import java.util.HashSet;
//import java.util.List;
import java.util.Map;

import models.Continent;
import models.Country;
import map.GameMap;

/**
 * Reinforcements class represents the reinforcement phase in the game.
 */
public class Reinforcements {
    /*
     * through object of game_state, go to the object of a particular player and get the list of countries owned by him.
    //  * keep boolean all_conqueredCountries = true;
    //  * loop over each continent
    //  * get the list of countries inside each continent
    //  * match if each country of the continent is present owned by the player (HashSet in both)
    //  * if not, put boolean to false
    //  * check if allCountriesConquered is true, get the bonus armies from continent 
    //  * (Do maintain a sum of num Of armies for each player,maybe return it to somewhere)
    //  * 
    //  * At the start of each loop, put boolean to true
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */

// i need to pass the game state as parameter , i need to update to reinforcement army to each player 


    private int d_reinforcementArmies;
    private Map<String, Country> d_countriesOwned; // A HashMap to store countries owned by the player
    private GameMap d_gameMap; // A reference to the game map
    Player d_player; // player Instance

    /**
     * Constructor for Reinforcements class.
     * @param gameMap The game map object.
     * @param player The player instance.
     */
    //@param playerCountriesMap The map of player names and their assigned countries.
    public Reinforcements(GameMap gameMap, Player p_player) {
        this.d_gameMap = gameMap;
        this.d_player = p_player;
    }

    // /**
    //  * Method to initialize countries owned by players.
    //  * @param playerCountriesMap The map of player names and their assigned countries.
    //  */
    // private void initializeCountries(Map<String, List<String>> playerCountriesMap) {
    //     for (List<String> countries : playerCountriesMap.values()) {
    //         for (String countryName : countries) {
    //             addCountry(countryName);
    //         }
    //     }
    // }

    // /**
    //  * Method to add a country owned by the player.
    //  * @param countryName The name of the country.
    //  */
    // public void addCountry(String countryName) {
    //     Country country = d_gameMap.getCountries().get(countryName);
    //     if (country != null) {
    //         d_countriesOwned.put(countryName, country);
    //     }
    // }

    /**
     * Method to calculate reinforcement armies for the player.
     */
    public void calculateReinforcementArmies() {
        d_reinforcementArmies = d_countriesOwned.size() / 3; // Basic calculation based on territories owned

        // Add bonus armies from continents owned
        for (HashMap.Entry<String, Continent> l_continent : d_gameMap.getD_Continents().entrySet()) {

            HashSet<Country> l_countries = l_continent.getD_countries();
            boolean ownsAllCountries = true;
            for (Country country : l_countries) {
                if (!d_player.getOwnership().contains(country.getName())) {
                      ownsAllCountries = false;
                  break;
                }
            }
            if (ownsAllCountries) {
                // Get the bonus armies for the continent from the game map
                int bonusArmies = l_continent.getD_continentValue();
                d_reinforcementArmies += bonusArmies;
            }
        }

        // Ensure minimum reinforcement armies
        if (d_reinforcementArmies < 3) {
            d_reinforcementArmies = 3;
        }
    }

    /**
     * Method to get reinforcement armies for the player.
     * @return The number of reinforcement armies for the player.
     */
    public int getReinforcementArmies() {
        return d_reinforcementArmies;
    }
}

