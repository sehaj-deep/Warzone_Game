package phases;

import java.util.ArrayList;
import java.util.List;

import game.GameEngine;
import game.Player;

/**
 * Phase that ends the game if there is a winner
 */
public class EndPhase extends MainPlay {
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
	 * check if there is a winner is found
	 */
	public void end() {
		kickOutPlayer();
		if (d_gameEngine.getGameState().getPlayers().size() == 1) {
			System.out.println(
					"Player " + d_gameEngine.getGameState().getPlayers().get(0).getPlayerName() + " has won the game");
			d_anyWinner = true;

		}
		this.next();
	}

	/**
	 * remove a player if there is no country owned by a player
	 */
	public void kickOutPlayer() {
		List<Player> l_copy = new ArrayList<>();
		l_copy.addAll(d_gameEngine.getGameState().getPlayers());
		for (Player l_player : d_gameEngine.getGameState().getPlayers()) {
			if (l_player.getOwnership().size() == 0) {
				// d_gameEngine.getGameState().getPlayers().remove(l_player);
				l_copy.remove(l_player);
			}
		}
		d_gameEngine.getGameState().setPlayers(l_copy);
	}

	@Override
	public void loadMap(String p_filename) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void editMap(String p_filename) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void removeContinent(String p_continentName) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void removeCountry(String p_countryName) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void saveMap(String p_filename) {
		this.printInvalidCommandMessage();

	}

	@Override
	public void reinforce() {
		this.printInvalidCommandMessage();

	}

	@Override
	public void attack() {
		this.printInvalidCommandMessage();

	}

	@Override
	public void fortify() {
		this.printInvalidCommandMessage();

	}
}