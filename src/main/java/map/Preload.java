package map;

import game.GameEngineNew;

public class Preload extends Edit {

	public Preload(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void loadMap() {

		d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

	@Override
	public void addCountry() {
		printInvalidCommandMessage();
	}

	@Override
	public void removeCountry() {
		printInvalidCommandMessage();
	}

	@Override
	public void saveMap() {
		printInvalidCommandMessage();
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}
}
