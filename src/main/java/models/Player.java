package models;

import java.util.ArrayList;
import java.util.List;

public class Player {

    // player name list
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

        // if input player name is null or empty
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            System.out.println("Error: player name cannot be empty");
            return;
        }

        if (!p_playerName.matches("[a-zA-Z0-9]+")) {
            System.out.println("Error: Invalid characters are not allowed");
            return;
        }

        // player name already exist
        for (Player name : d_playerNameList) {
            if (name.get_playerName().equals(p_playerName)) {
                System.out.println("Error: player " + p_playerName + " already exist");
                return;
            }
        }

        // add new player
        Player name = new Player(p_playerName);
        d_playerNameList.add(name);
        System.out.println("Player: " + name + " successfully added");
    }

    // Method to remove player name
    public void removePlayer(String d_playerName) {

        // if input player name is null or empty
        if (d_playerName == null || d_playerName.trim().isEmpty()) {
            System.out.println("Error: player name cannot be empty");
            return;
        }

        boolean l_playerFound = false;

        // remove player
        for (Player name : d_playerNameList) {
            if (name.get_playerName().equals(d_playerName)) {
                d_playerNameList.remove(name);
                System.out.println("Player: " + d_playerName + " successfully removed");
                l_playerFound = true;
                break;
            }
        }

        // if player not found
        if (!l_playerFound) {
            System.out.println("Error: Player " + d_playerName + " not found");
        }
    }
}
