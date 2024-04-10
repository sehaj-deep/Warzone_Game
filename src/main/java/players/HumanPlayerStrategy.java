package players;

import java.io.Serializable;
import game.GameEngine;
import orders.Advance;
import orders.Airlift;
import orders.Blockade;
import orders.Bomb;
import orders.Deploy;
import orders.Diplomacy;
import orders.Order;

/**
 * Represents a human player strategy in the game.
 */
public class HumanPlayerStrategy extends PlayerStrategy implements Serializable {

	/**
	 * Creates an order based on the provided tokens.
	 *
	 * @param p_player     The player issuing the order.
	 * @param p_tokens     The tokens representing the order.
	 * @param p_gameEngine The game engine managing the game.
	 * @return The created order.
	 */
	@Override
	protected Order createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine) {
		String l_sourceCountry = "";
		String l_targetCountry = "";
		String l_playerId = "";
		int l_numberOfArmies = 0;
		Order l_order;

		switch (p_tokens[0]) {
		case "deploy":
			l_targetCountry = p_tokens[1];
			l_numberOfArmies = Integer.parseInt(p_tokens[2]);
			l_order = issueDeployOrder(p_player, p_gameEngine, l_targetCountry, l_numberOfArmies);
			break;
		case "advance":
			l_sourceCountry = p_tokens[1];
			l_targetCountry = p_tokens[2];
			l_numberOfArmies = Integer.parseInt(p_tokens[3]);
			l_order = issueAdvanceOrder(p_player, p_gameEngine, l_sourceCountry, l_targetCountry, l_numberOfArmies);
			break;
		case "bomb":
			l_targetCountry = p_tokens[1];
			l_order = issueBombOrder(p_player, p_gameEngine, l_targetCountry);
			break;
		case "airlift":
			l_sourceCountry = p_tokens[1];
			l_targetCountry = p_tokens[2];
			l_numberOfArmies = Integer.parseInt(p_tokens[3]);
			l_order = issueAirliftCommand(p_player, p_gameEngine, l_sourceCountry, l_targetCountry, l_numberOfArmies);
			break;
		case "negotiate":
			l_playerId = p_tokens[1];
			l_order = issueNegotiateOrder(p_player, p_gameEngine, l_playerId);
			break;
		case "blockade":
			l_targetCountry = p_tokens[1];
			l_order = issueBlockadeOrder(p_player, p_gameEngine, l_targetCountry);
			break;
		default:
			System.out.println("Invalid order. Please try again.");
			l_order = null;
		}
		return l_order;
	}

	/**
	 * Issues a negotiate order.
	 *
	 * @param p_player     The player issuing the order.
	 * @param p_gameEngine The game engine managing the game.
	 * @param p_playerId   The ID of the player to negotiate with.
	 * @return The negotiate order.
	 */
	private Order issueNegotiateOrder(Player p_player, GameEngine p_gameEngine, String p_playerId) {
		Diplomacy l_negotiate = new Diplomacy(p_playerId, p_gameEngine);

		if (l_negotiate.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			// p_player.getListOrders().add(l_negotiate);
			return l_negotiate;
		}
		return null;
	}

	/**
	 * Issues a deploy order.
	 *
	 * @param p_player         The player issuing the order.
	 * @param p_gameEngine     The game engine managing the game.
	 * @param p_targetCountry  The target country for deployment.
	 * @param p_numberOfArmies The number of armies to deploy.
	 * @return The deploy order.
	 */
	private Order issueDeployOrder(Player p_player, GameEngine p_gameEngine, String p_targetCountry,
			int p_numberOfArmies) {
		Deploy l_deploy = new Deploy(p_targetCountry, p_numberOfArmies, p_gameEngine);

		if (l_deploy.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			return l_deploy;
		}
		return null;
	}

	/**
	 * Issues an advance order.
	 *
	 * @param p_player         The player issuing the order.
	 * @param p_gameEngine     The game engine managing the game.
	 * @param p_sourceCountry  The source country for the advance.
	 * @param p_targetCountry  The target country for the advance.
	 * @param p_numberOfArmies The number of armies to advance.
	 * @return The advance order.
	 */
	private Order issueAdvanceOrder(Player p_player, GameEngine p_gameEngine, String p_sourceCountry,
			String p_targetCountry, int p_numberOfArmies) {
		Advance l_advance = new Advance(p_sourceCountry, p_targetCountry, p_numberOfArmies, p_gameEngine);

		if (l_advance.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			return l_advance;
		}
		return null;
	}

	/**
	 * Issues a bomb order.
	 *
	 * @param p_player        The player issuing the order.
	 * @param p_gameEngine    The game engine managing the game.
	 * @param p_targetCountry The target country for the bomb.
	 * @return The bomb order.
	 */
	private Order issueBombOrder(Player p_player, GameEngine p_gameEngine, String p_targetCountry) {
		Bomb l_bomb = new Bomb(p_targetCountry, p_gameEngine);

		if (l_bomb.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			return l_bomb;
		}
		return null;
	}

	/**
	 * Issues an airlift command order.
	 *
	 * @param p_player         The player issuing the order.
	 * @param p_gameEngine     The game engine managing the game.
	 * @param p_sourceCountry  The source country for the airlift.
	 * @param p_targetCountry  The target country for the airlift.
	 * @param p_numberOfArmies The number of armies for the airlift.
	 * @return The airlift command order.
	 */
	private Order issueAirliftCommand(Player p_player, GameEngine p_gameEngine, String p_sourceCountry,
			String p_targetCountry, int p_numberOfArmies) {
		Airlift l_airlift = new Airlift(p_sourceCountry, p_targetCountry, p_numberOfArmies, p_gameEngine);

		if (l_airlift.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			return l_airlift;
		}
		return null;
	}

	/**
	 * Issues a blockade order.
	 *
	 * @param p_player        The player issuing the order.
	 * @param p_gameEngine    The game engine managing the game.
	 * @param p_targetCountry The target country for the blockade.
	 * @return The blockade order.
	 */
	private Order issueBlockadeOrder(Player p_player, GameEngine p_gameEngine, String p_targetCountry) {
		Blockade l_blockade = new Blockade(p_targetCountry, p_gameEngine);

		if (l_blockade.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			return l_blockade;
		}
		return null;
	}

	/**
	 * Resets the player's strategy.
	 */
	@Override
	public void reset() {
		setHasOrder(true);
	}
}