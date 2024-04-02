package players;

import game.GameEngine;
import map.Country;
import orders.Advance;
import orders.Airlift;
import orders.Blockade;
import orders.Bomb;
import orders.Deploy;
import orders.Diplomacy;

public class HumanPlayerStrategy extends PlayerStrategy {

	@Override
	protected boolean createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine) {
		boolean l_isCommandValid = false;
		String l_sourceCountry = "";
		String l_targetCountry = "";
		String l_playerId = "";
		int l_numberOfArmies = 0;

		switch (p_tokens[0]) {
		case "deploy":
			l_targetCountry = p_tokens[1];
			l_numberOfArmies = Integer.parseInt(p_tokens[2]);
			l_isCommandValid = issueDeployOrder(p_player, p_gameEngine, l_targetCountry, l_numberOfArmies);
			break;
		case "advance":
			l_sourceCountry = p_tokens[1];
			l_targetCountry = p_tokens[2];
			l_numberOfArmies = Integer.parseInt(p_tokens[3]);
			l_isCommandValid = issueAdvanceOrder(p_player, p_gameEngine, l_sourceCountry, l_targetCountry,
					l_numberOfArmies);
			break;
		case "bomb":
			l_targetCountry = p_tokens[1];
			l_isCommandValid = issueBombOrder(p_player, p_gameEngine, l_targetCountry);
			break;
		case "airlift":
			l_sourceCountry = p_tokens[1];
			l_targetCountry = p_tokens[2];
			l_numberOfArmies = Integer.parseInt(p_tokens[3]);
			l_isCommandValid = issueAirliftCommand(p_player, p_gameEngine, l_sourceCountry, l_targetCountry,
					l_numberOfArmies);
			break;
		case "negotiate":
			l_playerId = p_tokens[1];
			l_isCommandValid = issueNegotiateOrder(p_player, p_gameEngine, l_playerId);
			break;
		case "blockade":
			l_targetCountry = p_tokens[1];
			l_isCommandValid = issueBlockadeOrder(p_player, p_gameEngine, l_targetCountry);
			break;
		default:
			System.out.println("Invalid order. Please try again.");
			l_isCommandValid = false;
		}
		return l_isCommandValid;
	}

	private boolean issueNegotiateOrder(Player p_player, GameEngine p_gameEngine, String p_playerId) {
		Diplomacy l_negotiate = new Diplomacy(p_playerId, p_gameEngine);

		if (l_negotiate.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			p_player.getListOrders().add(l_negotiate);
			return true;
		}
		return false;
	}

	private boolean issueDeployOrder(Player p_player, GameEngine p_gameEngine, String p_targetCountry,
			int p_numberOfArmies) {
		Deploy l_deploy = new Deploy(p_targetCountry, p_numberOfArmies, p_gameEngine);

		if (l_deploy.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			p_player.getListOrders().add(l_deploy);
			return true;
		}
		return false;
	}

	private boolean issueAdvanceOrder(Player p_player, GameEngine p_gameEngine, String p_sourceCountry,
			String p_targetCountry, int p_numberOfArmies) {
		Advance l_advance = new Advance(p_sourceCountry, p_targetCountry, p_numberOfArmies, p_gameEngine);

		if (l_advance.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			p_player.getListOrders().add(l_advance);
			return true;
		}
		return false;
	}

	private boolean issueBombOrder(Player p_player, GameEngine p_gameEngine, String p_targetCountry) {
		Bomb l_bomb = new Bomb(p_targetCountry, p_gameEngine);

		if (l_bomb.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			p_player.getListOrders().add(l_bomb);
			return true;
		}
		return false;
	}

	private boolean issueAirliftCommand(Player p_player, GameEngine p_gameEngine, String p_sourceCountry,
			String p_targetCountry, int p_numberOfArmies) {
		Airlift l_airlift = new Airlift(p_sourceCountry, p_targetCountry, p_numberOfArmies, p_gameEngine);

		if (l_airlift.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			p_player.getListOrders().add(l_airlift);
			return true;
		}
		return false;
	}

	private boolean issueBlockadeOrder(Player p_player, GameEngine p_gameEngine, String p_targetCountry) {
		Blockade l_blockade = new Blockade(p_targetCountry, p_gameEngine);

		if (l_blockade.isValidIssue(p_gameEngine.getPlayers().indexOf(p_player))) {
			p_player.getListOrders().add(l_blockade);
			return true;
		}
		return false;
	}

	@Override
	protected Country toAttack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Country toAttackFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Country toMoveFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Country toDefend() {
		// TODO Auto-generated method stub
		return null;
	}
}
