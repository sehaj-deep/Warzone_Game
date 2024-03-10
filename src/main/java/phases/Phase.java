package phases;

import game.GameEngineNew;

public abstract class Phase {
	protected GameEngineNew d_gameEngine;

	public Phase(GameEngineNew p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
	}

	// general behavior
	abstract public void loadMap(String p_filename);

	abstract public void showMap();

	// edit map state behavior
	abstract public void editMap(String p_filename);

	abstract public void addContinent(String p_continentName, int p_bonusArmies);

	abstract public void removeContinent(String p_continentName);

	abstract public void addCountry(String p_countryName, String p_continent);

	abstract public void removeCountry(String p_countryName);

	abstract public void addNeighbor(String p_country, String p_neighbor);

	abstract public void removeNeighbor(String p_country, String p_neighbor);

	abstract public void validateMap();

	abstract public void saveMap(String p_filename);

	// play state behavior
	// game setup state behavior
	abstract public void setPlayers();

	abstract public void assignCountries();

	// reinforcement state behavior
	abstract public void reinforce();

	// attack state behavior
	abstract public void attack();

	// fortify state behavior
	abstract public void fortify();

	// end state behavior
	abstract public void endGame();

	// go to next phase
	abstract public void next();

	// methods common to all states
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName());
	}
}
