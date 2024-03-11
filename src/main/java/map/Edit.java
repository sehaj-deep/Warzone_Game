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

}
