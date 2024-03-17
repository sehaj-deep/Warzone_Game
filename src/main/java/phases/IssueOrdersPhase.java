package phases;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import game.GameEngine;
import game.Player;

public class IssueOrdersPhase extends MainPlay {

	public IssueOrdersPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);

	}

	public boolean anyCommandLeft(Map<Player, Boolean> p_hasCommands) {
		for (boolean l_hasCommand : p_hasCommands.values()) {
			if (l_hasCommand) {
				return true;
			}
		}
		return false;
	}

	public void issueOrders(Scanner p_scanner) {
		Map<Player, Boolean> l_hasCommands = new HashMap<>();
		for (Player l_player : d_gameEngine.getGameState().getPlayers()) {
			l_hasCommands.put(l_player, true);
		}

		while (anyCommandLeft(l_hasCommands)) {
			for (Player l_player : d_gameEngine.getGameState().getPlayers()) {

				if (!l_hasCommands.get(l_player)) {
					continue;
				}

				String l_orderCommand;
				boolean l_isCommandValid = false;

				do {
					System.out
							.print("\n" + l_player.getPlayerName() + ", enter an order (type 'none' if no commands): ");
					l_orderCommand = p_scanner.nextLine();

					if (l_orderCommand.toLowerCase().equals("none")) {
						l_hasCommands.put(l_player, false);
						break;
					} else if (l_orderCommand.toLowerCase().equals("showmap")) {
						showMap();
					} else {
						l_isCommandValid = l_player.issue_order(l_orderCommand.split("\\s+"), d_gameEngine);
					}
				} while (!l_isCommandValid);
			}
		}
		this.next();
	}

	@Override
	public void loadMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeContinent(String p_continentName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCountry(String p_countryName, String p_continent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCountry(String p_countryName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinforce() {
		// TODO Auto-generated method stub

	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fortify() {
		// TODO Auto-generated method stub

	}

	@Override
	public void next() {
		d_gameEngine.setPhase(new ExecuteOrdersPhase(d_gameEngine));
	}

}
