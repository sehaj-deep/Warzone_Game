package Phases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the phase where players are added or removed.
 */
public class StarterPhase {

    // List to store player names
    private static final List<String> D_PLAYER_NAME_LIST = new ArrayList<>();

    // Player name
    private final String d_playerName;

    // Map to store player names and their assigned countries
    private static final Map<String, List<String>> D_PLAYER_COUNTRIES_MAP = new HashMap<>();

    // List of available countries
    private static final List<String> D_AVAILABLE_COUNTRIES = new ArrayList<>(); // Assuming you have a list of country names

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
    public static void addPlayer(String p_playerName) {
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!p_playerName.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Invalid characters are not allowed");
        }

        if (D_PLAYER_NAME_LIST.contains(p_playerName)) {
            throw new IllegalArgumentException("Player " + p_playerName + " already exists");
        }

        D_PLAYER_NAME_LIST.add(p_playerName);
        assignCountriesToPlayer(p_playerName);
        System.out.println("Player: " + p_playerName + " successfully added with assigned countries: " + D_PLAYER_COUNTRIES_MAP.get(p_playerName));
    }

    /**
     * Removes a player from the player list.
     *
     * @param p_playerName The name of the player to remove.
     */
    public static void removePlayer(String p_playerName) {
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!D_PLAYER_NAME_LIST.contains(p_playerName)) {
            throw new IllegalArgumentException("Player " + p_playerName + " not found");
        }

        D_PLAYER_NAME_LIST.remove(p_playerName);
        D_PLAYER_COUNTRIES_MAP.remove(p_playerName);
        System.out.println("Player: " + p_playerName + " successfully removed");
    }

    /**
     * Assigns countries to the specified player.
     *
     * @param p_playerName The name of the player.
     */
    private static void assignCountriesToPlayer(String p_playerName) {
        List<String> l_assignedCountries = new ArrayList<>();

        int l_playerCount = D_PLAYER_NAME_LIST.size();
        int l_countryCount = D_AVAILABLE_COUNTRIES.size();
        int l_countriesPerPlayer = l_countryCount / l_playerCount;
        int l_extraCountries = l_countryCount % l_playerCount;

        int l_startIndex = D_PLAYER_NAME_LIST.indexOf(p_playerName) * l_countriesPerPlayer;
        int l_endIndex = l_startIndex + l_countriesPerPlayer;

        if (D_PLAYER_NAME_LIST.indexOf(p_playerName) < l_extraCountries) {
            l_startIndex += D_PLAYER_NAME_LIST.indexOf(p_playerName);
            l_endIndex += D_PLAYER_NAME_LIST.indexOf(p_playerName) + 1;
        } else {
            l_startIndex += l_extraCountries;
            l_endIndex += l_extraCountries;
        }

        l_assignedCountries.addAll(D_AVAILABLE_COUNTRIES.subList(l_startIndex, l_endIndex));
        D_AVAILABLE_COUNTRIES.removeAll(l_assignedCountries);

        D_PLAYER_COUNTRIES_MAP.put(p_playerName, l_assignedCountries);
    }
}