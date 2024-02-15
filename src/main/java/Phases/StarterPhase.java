package Phases;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the phase where players are added or removed.
 */
public class StarterPhase {

    // List to store player names
    private static final List<String> playerNameList = new ArrayList<>();

    // Player name
    private final String playerName;

    /**
     * Constructor for the StarterPhase class.
     *
     * @param playerName The name of the player.
     */
    public StarterPhase(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Adds a player to the player list.
     *
     * @param playerName The name of the player to add.
     */
    public static void addPlayer(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!playerName.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Invalid characters are not allowed");
        }

        if (playerNameList.contains(playerName)) {
            throw new IllegalArgumentException("Player " + playerName + " already exists");
        }

        playerNameList.add(playerName);
        System.out.println("Player: " + playerName + " successfully added");
    }

    /**
     * Removes a player from the player list.
     *
     * @param playerName The name of the player to remove.
     */
    public static void removePlayer(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!playerNameList.contains(playerName)) {
            throw new IllegalArgumentException("Player " + playerName + " not found");
        }

        playerNameList.remove(playerName);
        System.out.println("Player: " + playerName + " successfully removed");
    }

    
}

