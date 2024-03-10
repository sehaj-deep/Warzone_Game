package phases;

import game.GameEngineNew;

public abstract class MainPlay extends Play {

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

}
