package phases;

import game.GameState;
import game.Player;
import game.Reinforcements;

/**
 * ReinforcePhase class represents the reinforcement phase in the game.
 */
public class ReinforcePhase {
    public void execute(GameState p_state){
        for(Player p_player: p_state.getPlayers())
        {
            Reinforcements l_reinforcements = new Reinforcements(p_state.getGameMap(),p_player);

            l_reinforcements.calculateReinforcementArmies();

            int reinforcementArmies = l_reinforcements.getReinforcementArmies();

            p_state.getReinforcements().add(reinforcementArmies);

            }
        }

    }

