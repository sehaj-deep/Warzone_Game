package phases;

import game.GameState;
import map.MapEditor;

public interface StarterPhaseState {
    void addPlayer(String p_playerName, GameState p_state);

    void removePlayer(String p_playerName, GameState p_state);

    boolean isAssignCountriesValid(GameState p_state);

    void assignCountriesToPlayer(GameState p_state, MapEditor p_map);
}
