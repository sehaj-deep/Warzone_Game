package models;

import java.util.ArrayList;
import java.util.List;

public class Player {

    /* Each player name adding to d_playerName list */
    private final static List<Player> d_playerNameList = new ArrayList<>();

    /**/
    public String d_playerName;

    /* Defining constructor for player class */
    public Player(String d_playerName) {
        this.d_playerName = d_playerName;
    }

    /* getting player name */
    public String get_playerName() {
        return d_playerName;
    }

    /* Method to Add player */
    public void setD_PlayerName(String d_playerName) {
      Player name = new Player(d_playerName);
      d_playerNameList.add(name);
      System.out.println("Player name "+name+" successfully added");
    }

}
