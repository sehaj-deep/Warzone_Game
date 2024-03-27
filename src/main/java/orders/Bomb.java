package orders;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import game.GameEngine;
import map.Country;
import players.Player;

/**
 * Bomb order to reduce the number of armies in a country.
 */
public class Bomb extends Order {

	/**
	 * The name of the country where to drop the bomb
	 */
	private String d_countryName;

	/**
	 * Parameterized Constructor for Bomb class.
	 *
	 * @param p_countryName The name of the country where to drop the bomb.
	 * @param p_gameEngine  The instance of Game Engine Class.
	 */
	public Bomb(String p_countryName, GameEngine p_gameEngine) {
		super(p_gameEngine, "Bomb");
		this.d_countryName = p_countryName;
		this.setIsAttack(true);
	}

	/**
	 * Executes the Bomb order by reducing the number of armies in the targeted
	 * country by half.
	 *
	 * @param p_playerId The ID of the player executing the order.
	 */
	@Override
	public void execute(int p_playerId) {
		Map<String, Integer> l_gameBoard = d_gameEngine.getGameBoard();
		l_gameBoard.put(d_countryName, l_gameBoard.get(d_countryName) / 2);
		d_gameEngine.setGameBoard(l_gameBoard);

		// decrement the number of bomb cards
		Player l_currentPlayer = d_gameEngine.getPlayers().get(p_playerId);
		l_currentPlayer.decreaseCardCount(this.d_orderName);

		System.out.println("Bomb executed: " + d_countryName + " has been bombed and it's armies reduced to half.");
	}

	/**
	 * checks if player can issue the Bomb order.
	 *
	 * @param p_playerId The ID of the player issuing the order.
	 * @return True if the order is valid to issue, otherwise false.
	 */
	@Override
	public boolean isValidIssue(int p_playerId) {
		Player l_currentPlayer = d_gameEngine.getPlayers().get(p_playerId);

		// check that territory is adjacent
		Set<String> l_countriesOwned = l_currentPlayer.getOwnership();
		for (String l_playerCountry : l_countriesOwned) {
			HashSet<Country> l_neighbors = d_gameEngine.getD_countries().get(l_playerCountry).getNeighbors();
			for (Country l_neighCountry : l_neighbors) {
				if (l_neighCountry.getD_name().equals(d_countryName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * checks if player can execute the Bomb order.
	 *
	 * @param p_playerId The ID of the player executing the order.
	 * @return True if the order is valid to execute, otherwise false.
	 */
	@Override
	public boolean isValidExecute(int p_playerId) {
		Player l_currentPlayer = d_gameEngine.getPlayers().get(p_playerId);
		if (l_currentPlayer.getCardCount(this.d_orderName) <= 0) {
			return false;
		}
		return isValidIssue(p_playerId);
	}
}
