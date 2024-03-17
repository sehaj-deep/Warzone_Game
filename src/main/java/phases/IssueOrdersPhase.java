package phases;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import game.GameEngine;
import game.Player;

/**
 * Represents the phase where players issue orders during gameplay.
 */
public class IssueOrdersPhase extends MainPlay {

	/**
     * the Constructor for IssueOrdersPhase.
     *
     * @param p_gameEngine The instance of the game engine.
     */
	public IssueOrdersPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);

	}

	/**
     * Method to allow players to issue orders during this phase.
     *
     * @param p_scanner Scanner object for user input.
     */
	public void issueOrders(Scanner p_scanner) {
		Map<Player, Boolean> l_hasCommands = new HashMap<>();
		for (Player l_player : d_gameEngine.getD_players()) {
			l_hasCommands.put(l_player, true);
		}

		for (Player l_player : d_gameEngine.getD_players()) {
			if (!l_hasCommands.get(l_player)) {
				continue;
			}

			String l_orderCommand;
			System.out.print("\n" + l_player.getPlayerName() + ", enter an order (type 'none' if no commands): ");
			l_orderCommand = p_scanner.nextLine();

			if (l_orderCommand.toLowerCase().equals("none")) {
				l_hasCommands.put(l_player, false);
			} else if (l_orderCommand.toLowerCase().equals("showmap")) {
				showMap();
			} else {
				l_player.issue_order(l_orderCommand.split("\\s+"), d_gameEngine);
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

	/**
     * Moves the game to the next phase after the issuing orders phase.
     */
	@Override
	public void next() {
		d_gameEngine.setPhase(new ExecuteOrdersPhase(d_gameEngine));
	}

}
