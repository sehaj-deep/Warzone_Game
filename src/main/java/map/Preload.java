package map;

public class Preload extends Edit {

	@Override
	public void loadMap() {
		// FIXME:
//		d_gameEngine.setPhase(new PostLoad(d_gameEngine));

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
		System.out.println("must load map");

	}

}
