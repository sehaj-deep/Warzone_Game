package phases;

import game.GameEngineNew;

public class PlaySetup extends Play {

	public PlaySetup(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void loadMap(String p_filename) {
		// TODO call respective methods

	}

	@Override
	public void setPlayers() {
		// TODO call respective methods

	}

	@Override
	public void assignCountries() {
		// TODO call respective methods

	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		// TODO invalid
	}

	@Override
	public void removeCountry(String p_continentName) {
		// TODO invalid

	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeContinent(String p_continentName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinforce() {
		// TODO invalid

	}

	@Override
	public void attack() {
		// TODO invalid

	}

	@Override
	public void fortify() {
		// TODO invalid

	}

	@Override
	public void next() {
		// TODO Uncomment when reinforcement phase is implemented
		// d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

}
