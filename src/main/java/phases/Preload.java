package phases;

import game.GameEngine;
import map.DominationMapReader;

/**
 * Represents the preload phase in the map editing process.
 */
public class Preload extends Edit {

	DominationMapReader mapReader;

	/**
	 * Constructor for the Preload phase.
	 * 
	 * @param p_gameEngine A context object for the Preload phase.
	 */
	public Preload(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * Moves to the next phase. Sets the phase to PostLoad.
	 */
	@Override
	public void next() {
		d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

	/**
	 * Edits the map, Reads the map file, displays the filename, and moves to the
	 * next phase.
	 * 
	 * @param p_filename The filename of the map to be edited.
	 */
	@Override
	public void editMap(String p_filename) {
//		readMap(p_filename, false);
//		String[] l_path = p_filename.split("/");
//		System.out.println("You are now editing " + l_path[l_path.length - 1]);

		// trying to read data from DominationMapReader
		mapReader = new DominationMapReader(d_gameEngine);
		mapReader.readMap(p_filename, false);
		String[] l_path = p_filename.split("/");
		System.out.println("You are now editing " + l_path[l_path.length - 1]);

		next();
	}

	/**
	 * Loads a map file.
	 * 
	 * @param p_fileName The filename of the map to load.
	 */
	@Override
	public void loadMap(String p_fileName) {
		printInvalidCommandMessage();
	}

	/**
	 * Adds a continent to the map.
	 *
	 * @param p_continentName The name of the continent to add.
	 * @param p_bonusArmies   The bonus armies associated with the continent.
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		printInvalidCommandMessage();
	}

	/**
	 * Removes a continent from the map.
	 *
	 * @param p_continentName The name of the continent to remove.
	 */
	@Override
	public void removeContinent(String p_continentName) {
		printInvalidCommandMessage();
	}

	/**
	 * Adds a country to the map.
	 *
	 * @param p_countryName The name of the country to add.
	 * @param p_continent   The continent to which the country belongs.
	 */
	@Override
	public void addCountry(String p_countryName, String p_continent) {
		printInvalidCommandMessage();
	}

	/**
	 * Removes a country from the map.
	 *
	 * @param p_countryName The name of the country to remove.
	 */
	@Override
	public void removeCountry(String p_countryName) {
		printInvalidCommandMessage();
	}

	/**
	 * Adds a neighbor to a country.
	 *
	 * @param p_country  The name of the country.
	 * @param p_neighbor The name of the neighbor country.
	 */
	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	/**
	 * Removes a neighbor from a country.
	 *
	 * @param p_country  The name of the country.
	 * @param p_neighbor The name of the neighbor country to remove.
	 */
	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();
	}

	/**
	 * Saves the map to a file.
	 *
	 * @param p_filename The filename to save the map to.
	 */
	@Override
	public void saveMap(String p_filename) {
		printInvalidCommandMessage();
	}

}
