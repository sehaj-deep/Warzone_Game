package phases;

import game.GameEngineNew;

public abstract class Play extends Phase {
	/**
	 * Constructor
	 * 
	 * @param p_gameEngine a context object for Play phase
	 */
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
	public void addPlayers(String p_playerName) {
		printInvalidCommandMessage();
	}

	public void removePlayers(String p_playerName) {
		printInvalidCommandMessage();
	}
}
