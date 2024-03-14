package map;

import game.GameEngineNew;
import phases.Phase;

public abstract class Edit extends Phase {
	public Edit(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	public void setPlayers() {
		printInvalidCommandMessage();
	}

	public void assignCountries() {
		printInvalidCommandMessage();
	}

	public void reinforce() {
		printInvalidCommandMessage();
	}

	public void attack() {
		printInvalidCommandMessage();
	}

	public void fortify() {
		printInvalidCommandMessage();
	}

	public void endGame() {
		printInvalidCommandMessage();
	}

	public void addPlayers(String p_playerName) {
		printInvalidCommandMessage();
	}

	public void removePlayers(String p_playerName) {
		printInvalidCommandMessage();
	}

}
