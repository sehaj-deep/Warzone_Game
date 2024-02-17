package phases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.GameMap;
import game.GameState;
import game.Player;
import game.Reinforcements;

/**
 * ReinforcePhase class represents the reinforcement phase in the game.
 */
public class ReinforcePhase {
    private GameMap gameMap;
    public void execute(GameState p_state){
        for(Player p_player: state.getPlayers())
        {
            public Reinforcements(p_state.getGameMap(),p_state.getPlayers()){
            this.d_gameMap = gameMap;
            this.d_countriesOwned = new HashMap<>();
            initializeCountries(playerCountriesMap);

            Reinforcements l_reinforcements = new ReinforcePhase(p_state.getGameMap(),p_state.getPlayers());

            l_reinforcements.calculateReinforcementArmies();

            int reinforcementArmies = l_reinforcements.getReinforcementArmies();

            p_state.getReinforcements().add(reinforcementArmies);

            }
        }

    }

    
}
