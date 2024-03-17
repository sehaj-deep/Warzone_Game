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
     * Checks if any commands are left to be issued by players.
     *
     * @param p_hasCommands A map indicating whether each player has commands left to issue.
     * @return true if there are any commands left to be issued, false otherwise.
     */
	public boolean anyCommandLeft(Map<Player, Boolean> p_hasCommands) {
		for (boolean l_hasCommand : p_hasCommands.values()) {
			if (l_hasCommand) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to allow players to issue orders during this phase.
	 *
	 * @param p_scanner Scanner object for user input.
	 */
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

	/**
     * Loads the map from a file.
     *
     * @param p_filename The filename of the map to load.
     */
	@Override
	public void loadMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	/**
     * Edits the loaded map.
     *
     * @param p_filename The filename of the map to edit.
     */
	@Override
	public void editMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	/**
     * Adds a continent to the map.
     *
     * @param p_continentName The name of the continent to add.
     * @param p_bonusArmies   The bonus armies awarded for controlling the continent.
     */
	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		// TODO Auto-generated method stub

	}

    /**
     * Removes a continent with the specified name.
     *
     * @param p_continentName The name of the continent to remove.
     */
	@Override
	public void removeContinent(String p_continentName) {
		// TODO Auto-generated method stub

	}

	/**
     * Adds a country to the map.
     *
     * @param p_countryName The name of the country to add.
     * @param p_continent   The continent to which the country belongs.
     */
	@Override
	public void addCountry(String p_countryName, String p_continent) {
		// TODO Auto-generated method stub

	}

	/**
     * Removes a country with the specified name.
     *
     * @param p_countryName The name of the country to remove.
     */
	@Override
	public void removeCountry(String p_countryName) {
		// TODO Auto-generated method stub

	}

	/**
     * Adds a neighbor country to an existing country.
     *
     * @param p_country  The name of the country to add a neighbor to.
     * @param p_neighbor The name of the neighboring country.
     */
	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		// TODO Auto-generated method stub

	}

	/**
     * Removes a neighbor country from an existing country.
     *
     * @param p_country  The name of the country to remove a neighbor from.
     * @param p_neighbor The name of the neighboring country to remove.
     */
	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		// TODO Auto-generated method stub

	}

	/**
     * Saves the current state of the map to a file.
     *
     * @param p_filename The filename to save the map to.
     */
	@Override
	public void saveMap(String p_filename) {
		// TODO Auto-generated method stub

	}

	/**
     * Reinforces the army.
     */
	@Override
	public void reinforce() {
		// TODO Auto-generated method stub

	}

	/**
     * Executes attacks between players.
     */
	@Override
	public void attack() {
		// TODO Auto-generated method stub

	}

	/**
     * Fortifies the player's positions on the map.
     */
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
