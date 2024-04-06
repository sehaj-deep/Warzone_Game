package players;

import game.GameEngine;
import map.Country;
import orders.Order;

public abstract class PlayerStrategy {
//	Player d_player;
//
//	/**
//	 * PlayerStrategy Constructor
//	 * 
//	 * @param p_player player that uses this strategy
//	 */
//	PlayerStrategy(Player p_player) {
//		this.d_player = p_player;
//	}

	protected abstract Order createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine);

	protected abstract Country toAttack();

	protected abstract Country toAttackFrom();

	protected abstract Country toMoveFrom();

	protected abstract Country toDefend();
}
