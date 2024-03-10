package phases;

import game.GameEngineNew;

public abstract class Play extends Phase {
	public Play(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	public void showMap() {
		System.out.println("map is being displayed");
	}

	public void editCountry() {
		printInvalidCommandMessage();
	}

	public void saveMap() {
		printInvalidCommandMessage();
	}

	public void endGame() {
		// TODO
		// d_gameEngine.setPhase(new End(d_gameEngine)));
	}
}
