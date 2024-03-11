package phases;

import game.GameEngineNew;
import game.GameState;
import map.MapEditor;


public class PlaySetup extends Play {

	public PlaySetup(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void loadMap() {
		// TODO call respective methods

	}

	@Override
	public void setPlayers() {
		// TODO call respective methods

	}

	private StarterPhaseState currentPhase;
	GameState GameState;
	@Override
	public void assignCountries() {
		currentPhase.assignCountriesToPlayer(GameState, new MapEditor());
	}

	@Override
	public void addCountry() {
		// TODO invalid
	}

	@Override
	public void removeCountry() {
		// TODO invalid

	}

	@Override
	public void reinforce() {
		// TODO invalid

	}

	@Override
	public void attack() {
		// TODO invalid

	}

	@Override
	public void fortify() {
		// TODO invalid

	}

	@Override
	public void next() {
		// TODO Uncomment when reinforcement phase is implemented
		// d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

}
