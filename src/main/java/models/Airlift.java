package models;

import java.util.Map;

import game.GameEngineNew;
import game.GameState;
import game.Order;
import game.Player;

public class Airlift extends Order {

	/**
	 * number of armies to airlift
	 */
	private int d_numArmies;

	/**
	 * The country from where to airlift armies
	 */
	private String d_sourceCountry;

	/**
	 * The country to drop airlifted armies
	 */
	private String d_targetCountry;

	/**
	 * Parameterized constructor of Airlift class
	 * 
	 * @param p_sourceCountry The country to lift armies from.
	 * @param p_targetCountry The country to drop armies into
	 * @param p_numArmies     The number of armies to airlift
	 * @param p_gameEngineNew The instance of Game Engine Class
	 */
	public Airlift(String p_sourceCountry, String p_targetCountry, int p_numArmies, GameEngineNew p_gameEngineNew) {
		super(p_gameEngineNew, "Airlift");
		this.d_sourceCountry = p_sourceCountry;
		this.d_targetCountry = p_targetCountry;
		this.d_numArmies = p_numArmies;
	}

	@Override
	public void execute(GameState p_state, int p_playerId) {
		Map<String, Integer> l_gameBoard = p_state.getGameBoard();
		l_gameBoard.put(d_sourceCountry, l_gameBoard.get(d_sourceCountry) - d_numArmies);
		l_gameBoard.put(d_targetCountry, l_gameBoard.get(d_targetCountry) + d_numArmies);
		p_state.setGameBoard(l_gameBoard);

		// decrement the number of airlift cards
		Player l_currentPlayer = p_state.getPlayers().get(p_playerId);
		l_currentPlayer.decreaseCardCount(this.d_orderName);
	}

	@Override
	public boolean isValidIssue(GameState p_state, int p_playerId) {

		Player l_currentPlayer = p_state.getPlayers().get(p_playerId);

		if (d_numArmies < 0) {
			return false;
		}

		if (!l_currentPlayer.getOwnership().contains(d_sourceCountry)) {
			return false;
		}
		if (!l_currentPlayer.getOwnership().contains(d_targetCountry)) {
			return false;
		}
		Map<String, Integer> l_gameBoard = p_state.getGameBoard();
		int l_sourceArmies = l_gameBoard.get(d_sourceCountry);
		if (l_sourceArmies <= d_numArmies) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isValidExecute(GameState p_state, int p_playerId) {
		Player l_currentPlayer = p_state.getPlayers().get(p_playerId);
		if (l_currentPlayer.getCardCount(this.d_orderName) <= 0) {
			return false;
		}
		return isValidExecute(p_state, p_playerId);
	}

	@Override
	public void changeGameState(GameState p_state, int p_playerId) {
		return;
	}

}
