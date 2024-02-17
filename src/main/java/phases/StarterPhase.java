package phases;

import game.GameState;
import game.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Represents the phase where players are added or removed.
 */
public class StarterPhase {

    // List to store player names
    private final List<String> d_playerNameList = new ArrayList<>();

    // List of available countries
    private final List<String> d_availableCountries = new ArrayList<>(); // Assuming i have a list of country names

    /**
     * Constructor for the StarterPhase class.
     *
     * @param playerName The name of the player.
     */
    public StarterPhase(String playerName) {
        this.d_playerNameList.add(playerName);
    }

    /**
     * Adds a player to the player list and assigns countries.
     *
     * @param gameState The current game state.
     * @param player The player to add.
     */
    public void addPlayer(GameState gameState, Player player) {
        if (player == null || player.getPlayerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Player cannot be null or have an empty name");
        }

        if (d_playerNameList.contains(player.getPlayerName())) {
            throw new IllegalArgumentException("Player " + player.getPlayerName() + " already exists");
        }

        d_playerNameList.add(player.getPlayerName());
        assignCountriesToPlayer(gameState, player);
        System.out.println("Player: " + player.getPlayerName() + " successfully added with assigned countries: " + player.getOwnership());
    }

    /**
     * Assigns countries to the specified player.
     *
     * @param gameState The current game state.
     * @param player The player to whom countries are assigned.
     */
    private void assignCountriesToPlayer(GameState gameState, Player player) {
        // Check if there are available countries to assign
        if (d_availableCountries.isEmpty()) {
            throw new IllegalStateException("No available countries to assign");
        }

        List<String> l_assignedCountries = new ArrayList<>(d_availableCountries); // Copy available countries

        // Shuffle the list of available countries
        Collections.shuffle(l_assignedCountries);

        int l_playerCount = d_playerNameList.size();
        int l_countryCount = l_assignedCountries.size();
        int l_countriesPerPlayer = l_countryCount / l_playerCount;
        int l_extraCountries = l_countryCount % l_playerCount;

        int playerIndex = gameState.getPlayers().indexOf(player);
        int l_startIndex = playerIndex * l_countriesPerPlayer;
        int l_endIndex = l_startIndex + l_countriesPerPlayer;

        if (playerIndex < l_extraCountries) {
            l_startIndex += playerIndex;
            l_endIndex += playerIndex + 1;
        } else {
            l_startIndex += l_extraCountries;
            l_endIndex += l_extraCountries;
        }

        // Assign countries to the player based on the shuffled list
        List<String> playerCountries = l_assignedCountries.subList(l_startIndex, l_endIndex);

        // Remove assigned countries from the available countries list
        d_availableCountries.removeAll(playerCountries);

        // Update the player's owned countries
        player.setOwnership(new HashSet<>(playerCountries));
    }
}
