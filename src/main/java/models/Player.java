package models;

import java.util.ArrayList;
import java.util.List;

public class Player {

    // list to store player names
    private final static List<Player> d_playerNameList = new ArrayList<>();

    // player name
    public String d_playerName;

    // defining constructor for player class
    public Player(String d_playerName) {
        this.d_playerName = d_playerName;
    }

    // getting player name
    public String get_playerName() {
        return d_playerName;
    }

    // method to Add player name
    public void setD_PlayerName(String p_playerName) {

        // possible error handling: if input player name is null or empty
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            System.out.println("Error: player name cannot be empty");
            return;
        }

        // possible error handling: if input is invalid characters
        if (!p_playerName.matches("[a-zA-Z0-9]+")) {
            System.out.println("Error: Invalid characters are not allowed");
            return;
        }

        // possible error handling: if player name already exist
        for (Player name : d_playerNameList) {
            if (name.get_playerName().equals(p_playerName)) {
                System.out.println("Error: player " + p_playerName + " already exist");
                return;
            }
        }

        // add new player name
        Player name = new Player(p_playerName);
        d_playerNameList.add(name);
        System.out.println("Player: " + name + " successfully added");
    }

    // method to remove player name
    public void removePlayer(String d_playerName) {

        // possible error handling: if input player name is null or empty
        if (d_playerName == null || d_playerName.trim().isEmpty()) {
            System.out.println("Error: player name cannot be empty");
            return;
        }

        boolean l_playerFound = false;

        // remove player name
        for (Player name : d_playerNameList) {
            if (name.get_playerName().equals(d_playerName)) {
                d_playerNameList.remove(name);
                System.out.println("Player: " + d_playerName + " successfully removed");
                l_playerFound = true;
                break;
            }
        }

        // possible error handling: if player name not found
        if (!l_playerFound) {
            System.out.println("Error: Player " + d_playerName + " not found");
        }
    }
}
