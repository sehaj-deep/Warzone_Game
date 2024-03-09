package phases;

public abstract class Edit extends Phase {
	public void showMap() {
		System.out.println("edited map is displayed");
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
