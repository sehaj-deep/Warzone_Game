package game;

import java.util.*;

public class GameState {
    private List<Players> d_players;  // list of all players in the game
    private List<Integer> d_reinforcements;

    GameState(List<Players> p_players) {
        d_players = p_players;
    }

    public List<Players> getPlayers() {
        return d_players;
    }

    public List<Integer> getReinforcements() {
        return d_reinforcements;
    }
}