package phases;

import game.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;


/**
 * Represents the phase where players are added or removed.
 */
public class StarterPhase {

    // List to store player names
    private final List<String> d_playerNameList = new ArrayList<>();

    // Player name
    private final String d_playerName;

    // Map to store player names and their assigned countries
    private final Map<String, List<String>> d_playerCountriesMap = new HashMap<>();

    // List of available countries
    private final List<String> d_availableCountries = new ArrayList<>(); // Assuming i have a list of country names

    /**
     * Constructor for the StarterPhase class.
     *
     * @param playerName The name of the player.
     */
    public StarterPhase(String playerName) {
        this.d_playerName = playerName;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getPlayerName() {
        return d_playerName;
    }

    /**
     * Adds a player to the player list and assigns countries.
     *
     * @param p_playerName The name of the player to add.
     */
    public void addPlayer(String p_playerName) {
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!p_playerName.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Invalid characters are not allowed");
        }

        if (d_playerNameList.contains(p_playerName)) {
            throw new IllegalArgumentException("Player " + p_playerName + " already exists");
        }

        d_playerNameList.add(p_playerName);
        assignCountriesToPlayer(p_playerName);
        System.out.println("Player: " + p_playerName + " successfully added with assigned countries: " + d_playerCountriesMap.get(p_playerName));
    }

    /**
     * Removes a player from the player list.
     *
     * @param p_playerName The name of the player to remove.
     */
    public void removePlayer(String p_playerName) {
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!d_playerNameList.contains(p_playerName)) {
            throw new IllegalArgumentException("Player " + p_playerName + " not found");
        }

        d_playerNameList.remove(p_playerName);
        d_playerCountriesMap.remove(p_playerName);
        System.out.println("Player: " + p_playerName + " successfully removed");
    }

    /**
     * Assigns countries to the specified player.
     *
     * @param p_playerName The name of the player.
     */
    private void assignCountriesToPlayer(String p_playerName) {
        List<String> l_assignedCountries = new ArrayList<>(d_availableCountries); // Copy available countries

        // Shuffle the list of available countries
        Collections.shuffle(l_assignedCountries);

        int l_playerCount = d_playerNameList.size();
        int l_countryCount = l_assignedCountries.size();
        int l_countriesPerPlayer = l_countryCount / l_playerCount;
        int l_extraCountries = l_countryCount % l_playerCount;

        int l_startIndex = d_playerNameList.indexOf(p_playerName) * l_countriesPerPlayer;
        int l_endIndex = l_startIndex + l_countriesPerPlayer;

        if (d_playerNameList.indexOf(p_playerName) < l_extraCountries) {
            l_startIndex += d_playerNameList.indexOf(p_playerName);
            l_endIndex += d_playerNameList.indexOf(p_playerName) + 1;
        } else {
            l_startIndex += l_extraCountries;
            l_endIndex += l_extraCountries;
        }

        // Assign countries to the player based on the shuffled list
        List<String> playerCountries = l_assignedCountries.subList(l_startIndex, l_endIndex);

        // Remove assigned countries from the available countries list
        d_availableCountries.removeAll(playerCountries);

        // Update the player countries map
        d_playerCountriesMap.put(p_playerName, playerCountries);
    }
}