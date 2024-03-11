package phases;

import game.GameEngineNew;

import java.util.ArrayList;
import java.util.List;

public abstract class Phase {
	protected GameEngineNew d_gameEngine;

	public Phase(GameEngineNew p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
	}

	public static final List<String> d_playerNameList = new ArrayList<>();

	// general behavior
	abstract public void loadMap();

	abstract public void showMap();

	// edit map state behavior
	abstract public void addCountry();

	abstract public void removeCountry();

	abstract public void saveMap();

	// play state behavior
	// game setup state behavior

	abstract public void addPlayers(String p_playerName);

	abstract public void removePlayers(String p_playerName);

	abstract public void assignCountries(GameEngineNew p_gMap);

	// reinforcement state behavior
	abstract public void reinforce();

	// attack state behavior
	abstract public void attack();

	// fortify state behavior
	abstract public void fortify();

	// end state behavior
	abstract public void endGame();

	// go to next phase
	abstract public void next();

	// methods common to all states
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName());
	}
}
