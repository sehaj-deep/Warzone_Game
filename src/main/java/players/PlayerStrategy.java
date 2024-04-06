package players;

import game.GameEngine;
import orders.Order;

/**
 * Abstract class for PlayerStrategy by Strategy pattern from which all concrete
 * strategies must inherit
 */
public abstract class PlayerStrategy {
	/**
	 * Boolean representing whether issuing an order is allowed currently
	 */
	private boolean d_hasOrder = true;

	/**
	 * Create an order to be issued by the player using this PlayerStrategy
	 * 
	 * @param p_player     Player object that uses this PlayerStrategy
	 * @param p_tokens     string tokens from user commands. Expect null for non
	 *                     Human Player
	 * @param p_gameEngine a context object for ExecuteOrders phase
	 * @return a new order to be issued by the player using this PlayerStrategy
	 */
	protected abstract Order createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine);

	/**
	 * Reset data attributes of PlayerStrategy object
	 */
	public abstract void reset();

	/**
	 * Read d_hasOrder data member of this class
	 * 
	 * @return a boolean representing whether this player can make an Order
	 */
	public boolean getHasOrder() {
		return d_hasOrder;
	}

	/**
	 * Update d_hasOrder data member of this class
	 * 
	 * @param p_hasOrder a new boolean value whether this player can make an Order
	 *                   order
	 */
	public void setHasOrder(boolean p_hasOrder) {
		d_hasOrder = p_hasOrder;
	}
}
