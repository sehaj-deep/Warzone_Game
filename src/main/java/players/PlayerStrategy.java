package players;

import game.GameEngine;
import orders.Order;

public abstract class PlayerStrategy {
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
}
