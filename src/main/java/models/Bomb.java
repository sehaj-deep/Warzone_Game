package models;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.GameEngine;
import game.GameState;
import game.Order;
import game.Player;

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
     * @param p_gameEngine The instance of Game Engine Class.
     */
	public Bomb(String p_countryName, GameEngine p_gameEngine) {
		super(p_gameEngine, "Bomb");
		this.d_countryName = p_countryName;
	}

	/**
     * Executes the Bomb order by reducing the number of armies in the targeted country by half.
     *
     * @param p_state The current state of the game.
     * @param p_playerId The ID of the player executing the order.
     */
	@Override
	public void execute(GameState p_state, int p_playerId) {
		Map<String, Integer> l_gameBoard = p_state.getGameBoard();
		l_gameBoard.put(d_countryName, l_gameBoard.get(d_countryName) / 2);
		p_state.setGameBoard(l_gameBoard);

		// decrement the number of bomb cards
		Player l_currentPlayer = p_state.getPlayers().get(p_playerId);
		l_currentPlayer.decreaseCardCount(this.d_orderName);
	}

	/**
     * checks if player can issue the Bomb order.
     *
     * @param p_state The current state of the game.
     * @param p_playerId The ID of the player issuing the order.
     * @return True if the order is valid to issue, otherwise false.
     */
	@Override
	public boolean isValidIssue(GameState p_state, int p_playerId) {
		Player l_currentPlayer = p_state.getPlayers().get(p_playerId);

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
     * @param p_state The current state of the game.
     * @param p_playerId The ID of the player executing the order.
     * @return True if the order is valid to execute, otherwise false.
     */
	@Override
	public boolean isValidExecute(GameState p_state, int p_playerId) {
		Player l_currentPlayer = p_state.getPlayers().get(p_playerId);
		if (l_currentPlayer.getCardCount(this.d_orderName) <= 0) {
			return false;
		}
		return isValidIssue(p_state, p_playerId);
	}

	/**
     * method to make the gamestate constant for Bomb order.
     *
     * @param p_state The current state of the game.
     * @param p_playerId The ID of the player executing the order.
     */
	@Override
	public void changeGameState(GameState p_state, int p_playerId) {
		return;
	}

}
