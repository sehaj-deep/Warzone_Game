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
	String playerName;

	@Override
	public void addPlayers() {
		currentPhase.addPlayer(playerName, GameState);
	}

	@Override
	public void removePlayers() {
		currentPhase.removePlayer(playerName, GameState);
	}

	@Override
	public void assignCountries() {
		currentPhase.assignCountriesToPlayer(GameState, new MapEditor());
	}

	@Override
	public void addCountry() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeCountry() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void reinforce() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void attack() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void fortify() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void next() {
		// TODO Uncomment when reinforcement phase is implemented
		// d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

}
