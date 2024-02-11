package models;

import java.util.ArrayList;
import java.util.List;

public class Player {

    // each player name adding to d_playerName list
    private final static List<Player> d_playerNameList = new ArrayList<>();

    // getting each player name using this string
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
    public void setD_PlayerName(String d_playerName) {

        // same player name not allowed
        for (Player name : d_playerNameList) {
            if (name.get_playerName().equals(d_playerName)) {
                System.out.println("Error: player " + d_playerName + " already exist");
            }
            return;
        }

        Player name = new Player(d_playerName);
        d_playerNameList.add(name);
        System.out.println("Player: " + name + " successfully added");
    }

    // Method to remove player name
    public void removePlayer(String d_playerName) {

        boolean playerFound = false;

        for (Player name : d_playerNameList) {
            if (name.get_playerName().equals(d_playerName)) {
                d_playerNameList.remove(name);
                System.out.println("Player: " + d_playerName + " successfully removed");
                playerFound = true;
                break;
            }
        }

        if (!playerFound) {
            System.out.println("Error: Player " + d_playerName + " not found");
        }
    }
}
