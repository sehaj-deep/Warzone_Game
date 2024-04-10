package players;

import java.io.Serializable;

import game.GameEngine;
import orders.Order;

/**
 * Neutral player strategy that does nothing.
 */
public class NeutralPlayerStrategy extends PlayerStrategy implements Serializable {

	/**
	 * Creates a null order, as neutral players do not issue any orders.
	 * 
	 * @param p_player     The player adopting the neutral strategy.
	 * @param p_tokens     Command tokens provided.
	 * @param p_gameEngine The game engine.
	 * @return Null order, as neutral players don't issue orders.
	 */
	@Override
	protected Order createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine) {
		// Neutral player strategy does not issue any orders
		return null;
	}

	/**
	 * Resets the neutral player strategy.
	 */
	@Override
	public void reset() {
	}
}
