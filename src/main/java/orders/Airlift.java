package orders;

import java.io.Serializable;
import java.util.Map;

import game.GameEngine;
import players.Player;

/**
 * Airlift order to transfer armies from one country to another.
 */
public class Airlift extends Order implements Serializable {

	/**
	 * number of armies to airlift
	 */
	private int d_numArmies;

	/**
	 * The country from where to airlift armies
	 */
	private String d_sourceCountry;

	/**
	 * Parameterized constructor of Airlift class
	 * 
	 * @param p_sourceCountry The country to lift armies from.
	 * @param p_targetCountry The country to drop armies into
	 * @param p_numArmies     The number of armies to airlift
	 * @param p_gameEngineNew The instance of Game Engine Class
	 */
	public Airlift(String p_sourceCountry, String p_targetCountry, int p_numArmies, GameEngine p_gameEngineNew) {
		super(p_gameEngineNew, "Airlift");
		this.d_sourceCountry = p_sourceCountry;
		this.d_targetCountry = p_targetCountry;
		this.d_numArmies = p_numArmies;
	}

	/**
	 * Executes the Airlift order by moving armies from source country to target
	 * country.
	 *
	 * @param p_playerId The ID of the player executing the order.
	 */
	@Override
	public void execute(int p_playerId) {
		Map<String, Integer> l_gameBoard = d_gameEngine.getGameBoard();
		l_gameBoard.put(d_sourceCountry, l_gameBoard.get(d_sourceCountry) - d_numArmies);
		l_gameBoard.put(d_targetCountry, l_gameBoard.get(d_targetCountry) + d_numArmies);
		d_gameEngine.setGameBoard(l_gameBoard);

		// decrement the number of airlift cards
		Player l_currentPlayer = d_gameEngine.getPlayers().get(p_playerId);
		l_currentPlayer.decreaseCardCount(this.d_orderName);

		System.out.println(
				"Airlift executed:" + d_numArmies + "armies moved from " + d_sourceCountry + " to " + d_targetCountry);
	}

	/**
	 * checks if player can issue the Airlift order.
	 *
	 * @param p_playerId The ID of the player issuing the order.
	 * @return True if the order is valid to issue, otherwise false.
	 */
	@Override
	public boolean isValidIssue(int p_playerId) {

		Player l_currentPlayer = d_gameEngine.getPlayers().get(p_playerId);

		if (d_numArmies < 0) {
			return false;
		}

		if (!l_currentPlayer.getOwnership().contains(d_sourceCountry)) {
			return false;
		}
		if (!l_currentPlayer.getOwnership().contains(d_targetCountry)) {
			return false;
		}
		Map<String, Integer> l_gameBoard = d_gameEngine.getGameBoard();
		int l_sourceArmies = l_gameBoard.get(d_sourceCountry);
		if (l_sourceArmies < d_numArmies) {
			return false;
		}

		return true;
	}

	/**
	 * checks if player can execute the Airlift order.
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
