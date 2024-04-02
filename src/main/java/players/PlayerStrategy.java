package players;

import game.GameEngine;
import map.Country;

public abstract class PlayerStrategy {

	protected abstract boolean createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine);

	protected abstract Country toAttack();

	protected abstract Country toAttackFrom();

	protected abstract Country toMoveFrom();

	protected abstract Country toDefend();
}
