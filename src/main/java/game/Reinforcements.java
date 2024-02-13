package game;
import java.util.HashMap;
import java.util.Map;

import models.Continent;
import map.GameMap;

/**
 * Player class represents players in the game.
 */
public class Reinforcements {
    private String name;
    private int d_reinforcementArmies;
    private Map<String, Integer> d_territoriesOwned; // A Hashmap to store Map of territories owned by the player
    private Map<String, Continent> d_continentsOwned; // A Hashmap to store Map of continents owned by the player
    private GameMap d_gameMap; // A Reference to the game map

    /**
     * Constructor for Player class.
     * @param name The name of the player.
     * @param gameMap The game map object.
     */
    public Reinforcements(String name, GameMap gameMap) {
        this.name = name;
        this.d_territoriesOwned = new HashMap<>();
        this.d_continentsOwned = new HashMap<>();
        this.d_gameMap = gameMap;
    }

    /**
     * Method to add a continent owned by the player.
     * @param continentName The name of the continent.
     */
    public void addContinent(String continentName) {
        Continent continent = d_gameMap.getD_Continents().get(continentName);
        if (continent != null) {
            d_continentsOwned.put(continentName, continent);
        }
    }

    /**
     * Method to calculate reinforcement armies for the player.
     */
    public void calculateReinforcementArmies() {
        d_reinforcementArmies = d_territoriesOwned.size() / 3; // Basic calculation based on territories owned

        // Add bonus armies from continents owned
        for (Continent continent : d_continentsOwned.values()) {
            d_reinforcementArmies += continent.getBonusArmies();
        }

        // it Ensure minimum reinforcement armies
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
