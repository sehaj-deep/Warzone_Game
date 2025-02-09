package players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import game.GameEngine;
import map.Country;
import orders.Order;

/**
 * Strategy for a cheater player that conquers neighboring enemy countries and
 * doubles the number of armies on its own countries.
 */
public class CheaterPlayerStrategy extends PlayerStrategy implements Serializable {

    /**
     * Creates orders for the cheater player strategy.
     *
     * This method conquers neighboring enemy countries and doubles the number of armies on its own countries.
     *
     * @param p_player     The player implementing the strategy.
     * @param p_tokens     Tokens representing the issued command (not used).
     * @param p_gameEngine The game engine instance.
     * @return Always returns null since no specific orders are created.
     */
	@Override
	protected Order createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine) {

		conquerNeighboringCountries(p_player, p_gameEngine);
		doubleArmies(p_player, p_gameEngine);

		setHasOrder(false);

		return null;
	}

	/**
	 * Conquer all neighboring enemy countries
	 * 
	 * @param p_cheater
	 * @param p_gameEngine
	 */
	private void conquerNeighboringCountries(Player p_cheater, GameEngine p_gameEngine) {
		List<String> l_countriesToConquer = new ArrayList<String>();
		for (String l_cheaterCountryName : p_cheater.getOwnership()) {
			Country l_cheaterCountry = p_gameEngine.getD_countries(l_cheaterCountryName);
			for (Country l_neighbor : l_cheaterCountry.getNeighbors()) {
				Player l_neighborOwner = getOwnerOfCountry(p_gameEngine, l_neighbor);
				if (l_neighborOwner != null && !l_neighborOwner.getPlayerName().equals(p_cheater.getPlayerName())) {
					l_neighborOwner.removeCountry(l_neighbor.getD_name());
					l_countriesToConquer.add(l_neighbor.getD_name());
				}
			}
		}

		for (String l_countryToConquer : l_countriesToConquer) {
			p_cheater.conquerCountry(l_countryToConquer);
		}
	}

	/**
	 * Double the number of armies on countries with enemy neighbors
	 * 
	 * @param p_cheater
	 * @param p_gameEngine
	 */
	private void doubleArmies(Player p_cheater, GameEngine p_gameEngine) {
		for (String l_cheaterCountryName : p_cheater.getOwnership()) {
			Country l_cheaterCountry = p_gameEngine.getD_countries(l_cheaterCountryName);
			for (Country l_neighbor : l_cheaterCountry.getNeighbors()) {
				Player l_neighborOwner = getOwnerOfCountry(p_gameEngine, l_neighbor);
				if (l_neighborOwner != null && !l_neighborOwner.getPlayerName().equals(p_cheater.getPlayerName())) {
					int l_currentArmies = p_gameEngine.getGameBoard().get(l_cheaterCountry.getD_name());
					p_gameEngine.getGameBoard().put(l_cheaterCountry.getD_name(), l_currentArmies * 2);
				}
			}
		}
	}

	/**
	 * Gets the owner of the country with the given name.
	 * 
	 * @param gameEngine The game engine instance.
	 * @param p_country  The name of the country.
	 * @return The owner of the country.
	 */
	private Player getOwnerOfCountry(GameEngine gameEngine, Country p_country) {
		for (Player l_player : gameEngine.getPlayers()) {
			if (l_player.getOwnership().contains(p_country.getD_name())) {
				return l_player;
			}
		}
		return null;
	}

	/**
     * Resets the strategy.
     *
     * Sets the hasOrder flag to true to indicate that the player can issue orders again.
     */
	@Override
	public void reset() {
		setHasOrder(true);
	}
}
