package models;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.GameEngineNew;
import game.GameState;
import game.Order;
import game.Player;

public class Bomb extends Order {

	/**
	 * The name of the country where to drop the bomb
	 */
	private String d_countryName;

	public Bomb(String p_countryName, GameEngineNew p_gameEngine) {
		super(p_gameEngine, "Bomb");
		this.d_countryName = p_countryName;
	}

	@Override
	public void execute(GameState p_state, int p_playerId) {
		Map<String, Integer> l_gameBoard = p_state.getGameBoard();
		l_gameBoard.put(d_countryName, l_gameBoard.get(d_countryName) / 2);
		p_state.setGameBoard(l_gameBoard);

		// decrement the number of bomb cards
		Player l_currentPlayer = p_state.getPlayers().get(p_playerId);
		l_currentPlayer.decreaseCardCount(this.d_orderName);
	}

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

	@Override
	public boolean isValidExecute(GameState p_state, int p_playerId) {
		Player l_currentPlayer = p_state.getPlayers().get(p_playerId);
		if (l_currentPlayer.getCardCount(this.d_orderName) <= 0) {
			return false;
		}
		return isValidIssue(p_state, p_playerId);
	}

	@Override
	public void changeGameState(GameState p_state, int p_playerId) {
		return;
	}

}
