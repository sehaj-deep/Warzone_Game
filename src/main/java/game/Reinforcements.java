package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import models.Continent;
import models.Country;
import map.GameMap;

/**
 * Reinforcements class represents the reinforcement phase in the game.
 */
public class Reinforcements {
    
    private int d_reinforcementArmies;
    private Map<String, Country> d_countriesOwned; // A HashMap to store countries owned by the player
    private static GameMap d_gameMap; // A reference to the game map
    Player d_player; // player Instance

    /**
     * Constructor for Reinforcements class.
     * @param gameMap The game map object.
     * @param player The player instance.
     */
    //@param playerCountriesMap The map of player names and their assigned countries.
    public Reinforcements(GameMap p_gameMap, Player p_player) {
        d_gameMap = p_gameMap;
        this.d_player = p_player;
    }

    /**
     * Method to calculate reinforcement armies for the player.
     */
    public void calculateReinforcementArmies() {
        d_reinforcementArmies = d_countriesOwned.size() / 3; // Basic calculation based on territories owned

        // Add bonus armies from continents owned
        for (HashMap.Entry<String, Continent> l_continent : d_gameMap.getD_Continents().entrySet()) {

            Set<Country> l_countries = l_continent.getValue().getD_countries();
            boolean l_OwnsAllCountries = false;
            for (Country country : l_countries) {
                if (!d_player.getOwnership().contains(country.getD_name())) {
                      l_OwnsAllCountries = true;
                  break;
                }
            }
            if (!l_OwnsAllCountries) {
                // Get the bonus armies for the continent from the game map
                int bonusArmies = l_continent.getValue().getD_continentValue();
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

