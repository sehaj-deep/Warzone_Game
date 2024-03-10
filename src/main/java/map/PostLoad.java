package map;

import game.GameEngineNew;
import phases.PlaySetup;

public class PostLoad extends Edit {

	public PostLoad(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	public void loadMap() {
		System.out.println("map has been loaded");
	}

	public void editCountry() {
		System.out.println("country has been edited");
	}

	public void saveMap() {
		System.out.println("map has been saved");

		d_gameEngine.setPhase(new PlaySetup(d_gameEngine));
	}

	public void next() {
		System.out.println("must save map");
	}

	@Override
	public void addCountry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCountry() {
		// TODO Auto-generated method stub

	}

}
