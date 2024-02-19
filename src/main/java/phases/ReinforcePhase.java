package phases;
import game.GameState;
import game.Player;
import game.Reinforcements;
import map.MapEditor;

/**
 * ReinforcePhase class represents the reinforcement phase in the game.
 */
public class ReinforcePhase {
		public ReinforcePhase() {}
	
    public void execute(GameState p_state, MapEditor p_mapEditor){
        for(Player p_player: p_state.getPlayers())
        {
            Reinforcements l_reinforcements = new Reinforcements(p_mapEditor, p_player);

            l_reinforcements.calculateReinforcementArmies();

            int reinforcementArmies = l_reinforcements.getReinforcementArmies();

            p_state.getReinforcements().add(reinforcementArmies);

            }
        }

    }