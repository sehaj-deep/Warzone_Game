package phases;

import java.util.ArrayList;
import java.util.List;

import game.GameEngine;
import players.Player;

/**
 * Phase that ends the game if there is a winner
 */
public class EndPhase extends MainPlay {
	/**
	 * Store the boolean value of winner player
	 */
	private boolean d_anyWinner;

	/**
	 * constructor of End Phase
	 *
	 * @param p_gameEngine a context object for ExecuteOrders phase
	 */
	public EndPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_anyWinner = false;
	}

	/**
	 * Getter for boolean value of winner player
	 *
	 * @return the boolean value of winner player
	 */
	public boolean getAnyWinner() {
		return d_anyWinner;
	}

	/**
	 * move to the next state(phase)
	 */
	public void next() {
		d_gameEngine.setPhase(new ReinforcePhase(d_gameEngine));
	}

	/**
	 * check if there is a winner found
	 */
	public void checkForWinner() {
		kickOutPlayer();
		if (d_gameEngine.getPlayers().size() == 1) {
			System.out.println("Player " + d_gameEngine.getPlayers().get(0).getPlayerName() + " has won the game!");
			d_anyWinner = true;
		}
		this.next();
	}

	/**
	 * remove a player if there is no country owned by a player
	 */
	public void kickOutPlayer() {
		List<Player> l_copy = new ArrayList<>();
		l_copy.addAll(d_gameEngine.getPlayers());
		for (Player l_player : d_gameEngine.getPlayers()) {
			if (l_player.getOwnership().size() == 0) {
				// d_gameEngine.getGameState().getPlayers().remove(l_player);
				l_copy.remove(l_player);
			}
		}
		d_gameEngine.setPlayers(l_copy);
	}

	/**
	 * Load map while playing
	 *
	 * @param p_filename the filename of the map
	 */
	@Override
	public void loadMap(String p_filename) {
		this.printInvalidCommandMessage();

	}

	/**
	 * Edit the map of the countries
	 *
	 * @param p_filename the filename of the map
	 */
	@Override
	public void editMap(String p_filename) {
		this.printInvalidCommandMessage();

	}

	/**
	 * Add the new continent taking by command from player
	 *
	 * @param p_continentName the name of the continent
	 * @param p_bonusArmies   the bonus armies for owning the continent
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		this.printInvalidCommandMessage();

	}

	/**
	 * Remove continent form game map
	 *
	 * @param p_continentName the name of the continent to remove
	 */
	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();

	}

	/**
	 * Add country in map
	 *
	 * @param p_countryName the name of the country
	 * @param p_continent   the continent to which the country belongs
	 */
	@Override
	public void addCountry(String p_countryName, String p_continent) {
		this.printInvalidCommandMessage();

	}

	/**
	 * Remove the country from country map
	 *
	 * @param p_countryName the name of the country to remove
	 */
	@Override
	public void removeCountry(String p_countryName) {
		this.printInvalidCommandMessage();

	}

	/**
	 * Add neighbor country in map
	 *
	 * @param p_country  the name of the country
	 * @param p_neighbor the name of the neighbor country
	 */
	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();

	}

	/**
	 * Remove neighbor country from map
	 *
	 * @param p_country  the name of the country
	 * @param p_neighbor the name of the neighbor country to remove
	 */
	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();

	}

	/**
	 * Save the map in game
	 *
	 * @param p_filename The filename to save the map to.
	 */
	@Override
	public void saveMap(String p_filename) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void startTournament(List<String> p_mapFiles, List<String> p_playerStrategies, int p_gamesToBePlayed,
			int p_maxNumberOfTurns) {
		this.printInvalidCommandMessage();
	}
}