package phases;

public abstract class Play extends Phase {
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
		// d_gameEngine.setPhase(new End(d_gameEngine)));
	}
}
