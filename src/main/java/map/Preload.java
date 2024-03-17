package map;

import game.GameEngineNew;

public class Preload extends Edit {

	public Preload(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

	@Override
	public void editMap(String p_filename) {
		readMap(p_filename, false);
		String[] l_path = p_filename.split("/");
		System.out.println("You are now editing " + l_path[l_path.length - 1]);

		next();
	}

	@Override
	public void loadMap(String p_fileName) {
		printInvalidCommandMessage();
	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeContinent(String p_continentName) {
		printInvalidCommandMessage();
	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeCountry(String p_countryName) {
		printInvalidCommandMessage();
	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	@Override
	public void saveMap(String p_filename) {
		printInvalidCommandMessage();
	}

}
