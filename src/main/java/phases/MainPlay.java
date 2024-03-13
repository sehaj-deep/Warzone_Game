package phases;

import game.GameEngineNew;
import game.GameState;
import map.MapEditor;

public abstract class MainPlay extends Play {

	/**
	 * Constructor
	 * 
	 * @param p_gameEngine a context object for MainPlay phase
	 */
	public MainPlay(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	public void loadMap() {
		this.printInvalidCommandMessage();
	}

	public void setPlayers() {
		this.printInvalidCommandMessage();
	}

	public void assignCountries() {
		this.printInvalidCommandMessage();
	}

	/**
	 * Execute the state-specific behavior of this phase
	 * 
	 * @param p_state Current state(situation) of the game at the moment
	 * @param p_gMap  Game map on which the game is played
	 */
	abstract public void run(GameState p_state, MapEditor p_gMap);
}

