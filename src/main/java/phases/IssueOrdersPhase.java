package phases;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.GameEngine;
import players.HumanPlayerStrategy;
import players.Player;
import utils.ValidationException;

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
	 * @param p_hasCommands A map indicating whether each player has commands left
	 *                      to issue.
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
     * Checks if any player has commands left to issue.
     *
     * @return true if any player has commands left to issue, false otherwise.
     */
	public boolean anyPlayerWithCommandLeft() {
		for (Player l_player : d_gameEngine.getPlayers()) {
			if (l_player.getPlayerStrategy().getHasOrder()) {
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
//		Map<Player, Boolean> l_hasCommands = new HashMap<>();
//		for (Player l_player : d_gameEngine.getPlayers()) {
		// if
		// (l_player.getD_playerStrategy().getClass().equals(HumanPlayerStrategy.class))
		// {
//			l_hasCommands.put(l_player, true);
		// } else {

		// }

		// }

		for (Player l_player : d_gameEngine.getPlayers()) {
			l_player.getPlayerStrategy().reset();
		}
		// while (anyCommandLeft(l_hasCommands)) {
		while (anyPlayerWithCommandLeft()) {
			for (Player l_player : d_gameEngine.getPlayers()) {

//				if (!l_hasCommands.get(l_player)) {
//					continue;
//				}
				if (!l_player.getPlayerStrategy().getHasOrder()) {
					continue;
				}

				if (l_player.getD_playerStrategy().getClass().equals(HumanPlayerStrategy.class)) {
					String l_orderCommand;
					boolean l_isCommandValid = false;

					do {
						System.out.print(
								"\n" + l_player.getPlayerName() + ", enter an order (type 'none' if no commands): ");
						l_orderCommand = p_scanner.nextLine();

						if (l_orderCommand.equalsIgnoreCase("showmap")) {
							showMap();
						} else {
							try {
								l_player.issue_order(l_orderCommand.split("\\s+"), d_gameEngine);
								l_isCommandValid = true;
							} catch (ValidationException e) {
								// newly created order is invalid, so continue asking the player until valid one
								// is given
								continue;
							}
						}
					} while (!l_isCommandValid);
				} else {
					try {
						l_player.issue_order(null, d_gameEngine);
					} catch (ValidationException e) {
						// ignore and skip the exception in this case as null order is not added in the
						// player's orders list
						continue;
					}
				}
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
		printInvalidCommandMessage();

	}

	/**
	 * Edits the loaded map.
	 *
	 * @param p_filename The filename of the map to edit.
	 */
	@Override
	public void editMap(String p_filename) {
		printInvalidCommandMessage();

	}

	/**
	 * Adds a continent to the map.
	 *
	 * @param p_continentName The name of the continent to add.
	 * @param p_bonusArmies   The bonus armies awarded for controlling the
	 *                        continent.
	 */
	@Override
	public void addContinent(String p_continentName, int p_bonusArmies) {
		printInvalidCommandMessage();

	}

	/**
	 * Removes a continent with the specified name.
	 *
	 * @param p_continentName The name of the continent to remove.
	 */
	@Override
	public void removeContinent(String p_continentName) {
		printInvalidCommandMessage();

	}

	/**
	 * Adds a country to the map.
	 *
	 * @param p_countryName The name of the country to add.
	 * @param p_continent   The continent to which the country belongs.
	 */
	@Override
	public void addCountry(String p_countryName, String p_continent) {
		printInvalidCommandMessage();

	}

	/**
	 * Removes a country with the specified name.
	 *
	 * @param p_countryName The name of the country to remove.
	 */
	@Override
	public void removeCountry(String p_countryName) {
		printInvalidCommandMessage();

	}

	/**
	 * Adds a neighbor country to an existing country.
	 *
	 * @param p_country  The name of the country to add a neighbor to.
	 * @param p_neighbor The name of the neighboring country.
	 */
	@Override
	public void addNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();

	}

	/**
	 * Removes a neighbor country from an existing country.
	 *
	 * @param p_country  The name of the country to remove a neighbor from.
	 * @param p_neighbor The name of the neighboring country to remove.
	 */
	@Override
	public void removeNeighbor(String p_country, String p_neighbor) {
		printInvalidCommandMessage();

	}

	/**
	 * Saves the current state of the map to a file.
	 *
	 * @param p_filename The filename to save the map to.
	 */
	@Override
	public void saveMap(String p_filename) {
		printInvalidCommandMessage();

	}

	/**
	 * Moves the game to the next phase after the issuing orders phase.
	 */
	@Override
	public void next() {
		d_gameEngine.setPhase(new ExecuteOrdersPhase(d_gameEngine));
	}

	@Override
	public void setupTournament(String p_mapFile, List<String> p_playerStrategies) {
		this.printInvalidCommandMessage();
	}

}
