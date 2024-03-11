package phases;

import game.GameState;
import map.MapEditor;

public interface StarterPhaseState {
    void addPlayer(String playerName, GameState state);

    void removePlayer(String playerName, GameState state);

    boolean isAssignCountriesValid(GameState state);

    void assignCountriesToPlayer(GameState state, MapEditor map);
}
