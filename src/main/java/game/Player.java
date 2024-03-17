package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import models.Airlift;
import models.Bomb;
import models.Diplomacy;

/**
 * Player Class this class stores information and the state of the game about a
 * player
 */

public class Player {

	/**
	 * player name
	 */
	private String d_playerName;

	/**
	 * list of orders issued by the player
	 */
	private Queue<Order> d_listOrders;

	/**
	 * set of countries owned by the player
	 */
	private Set<String> d_ownership;

	/**
	 * A mapping of card names to the count of those cards held by the player.
	 */
	private HashMap<String, Integer> d_listOfCards;
	private final List<Player> d_negotiatedWith = new ArrayList<>();

	/**
	 * Parameterized constructor for the player
	 *
	 * @param p_playerName To uniquely identify the player
	 */
	public Player(String p_playerName) {
		d_playerName = p_playerName;
		d_listOrders = new LinkedList<>();
		d_ownership = new HashSet<String>();
		d_listOfCards = new HashMap<>();
		d_listOfCards.put("Bomb", 0);
		d_listOfCards.put("Blockade", 0);
		d_listOfCards.put("Airlift", 0);
		d_listOfCards.put("Diplomacy", 0);
	}

	/**
	 * get this player's name
	 *
	 * @return player's name
	 */
	public String getPlayerName() {
		return d_playerName;
	}

	/**
	 * get the countries owned by this player
	 *
	 * @return a set of countries owned by the player
	 */
	public Set<String> getOwnership() {
		return d_ownership;
	}

	/**
	 * set the player's countries owned set to the set provided
	 *
	 * @param p_ownership a new set of countries owned
	 */
	public void setOwnership(Set<String> p_ownership) {
		d_ownership = p_ownership;
	}

	/**
	 * get the list of orders issued by this player
	 *
	 * @return a queue data structure storing the orders in the order of issuance
	 */
	public Queue<Order> getListOrders() {
		return d_listOrders;
	}

	/**
	 * conquer a country to the player's owned countries
	 *
	 * @param p_country is the country name to be added to player's owned countries
	 */
	public void conquerCountry(String p_country) {
		d_ownership.add(p_country);
	}

	/**
	 * remove a country from the player's owned countries
	 *
	 * @param p_country is the country name to be removed from player's owned
	 *                  countries
	 */
	public void removeCountry(String p_country) {
		d_ownership.remove(p_country);
	}

	/**
	 * Parses and processes the issued order based on the provided command tokens.
	 * 
	 * @param p_tokens     The array of tokens representing the issued command.
	 * @param p_gameEngine The game engine instance managing the game state.
	 * @return boolean indicating whether the order was successfully issued or not.
	 */
	public boolean issue_order(String[] p_tokens, GameEngine p_gameEngine) {

		boolean l_isCommandValid = false;
		String l_sourceCountry = "";
		String l_targetCountry = "";
		String l_playerId = "";
		int l_numberOfArmies = 0;

		switch (p_tokens[0]) {
		case "deploy":
			l_targetCountry = p_tokens[1];
			l_numberOfArmies = Integer.parseInt(p_tokens[2]);
			l_isCommandValid = issueDeployOrder(p_gameEngine, l_targetCountry, l_numberOfArmies);
			break;
		case "advance":
			l_sourceCountry = p_tokens[1];
			l_targetCountry = p_tokens[2];
			l_numberOfArmies = Integer.parseInt(p_tokens[3]);
			l_isCommandValid = issueAdvanceOrder(p_gameEngine, l_sourceCountry, l_targetCountry, l_numberOfArmies);
			break;
		case "bomb":
			l_targetCountry = p_tokens[1];
			l_isCommandValid = issueBombOrder(p_gameEngine, l_targetCountry);
			break;
		case "airlift":
			l_sourceCountry = p_tokens[1];
			l_targetCountry = p_tokens[2];
			l_numberOfArmies = Integer.parseInt(p_tokens[3]);
			l_isCommandValid = issueAirliftCommand(p_gameEngine, l_sourceCountry, l_targetCountry, l_numberOfArmies);
			break;
		case "negotiate":
			l_playerId = p_tokens[1];
			l_isCommandValid = issueNegotiateOrder(p_gameEngine, l_playerId);
			break;
		case "blockade":
			l_targetCountry = p_tokens[1];
			l_isCommandValid = issueBlockadeOrder(p_gameEngine, l_targetCountry);
			break;
		default:
			System.out.println("Invalid order. Please try again.");
			l_isCommandValid = false;
		}
		return l_isCommandValid;
	}

	private boolean issueNegotiateOrder(GameEngine p_gameEngine, String l_playerId) {
		Diplomacy l_negotiate = new Diplomacy(l_playerId, p_gameEngine);

		if (l_negotiate.isValidIssue(p_gameEngine.getGameState(),
				p_gameEngine.getGameState().getPlayers().indexOf(this))) {
			d_listOrders.add(l_negotiate);
			return true;
		}
		return false;
	}

	private boolean issueDeployOrder(GameEngine p_gameEngine, String l_targetCountry, int l_numberOfArmies) {
		Deploy l_deploy = new Deploy(l_targetCountry, l_numberOfArmies, p_gameEngine);

		if (l_deploy.isValidIssue(p_gameEngine.getGameState(),
				p_gameEngine.getGameState().getPlayers().indexOf(this))) {
			d_listOrders.add(l_deploy);
			return true;
		}
		return false;
	}

	private boolean issueAdvanceOrder(GameEngine p_gameEngine, String l_sourceCountry, String l_targetCountry,
			int l_numberOfArmies) {
		Advance l_advance = new Advance(l_sourceCountry, l_targetCountry, l_numberOfArmies, p_gameEngine);

		if (l_advance.isValidIssue(p_gameEngine.getGameState(),
				p_gameEngine.getGameState().getPlayers().indexOf(this))) {
			d_listOrders.add(l_advance);
			return true;
		}
		return false;
	}

	private boolean issueBombOrder(GameEngine p_gameEngine, String l_targetCountry) {
		Bomb l_bomb = new Bomb(l_targetCountry, p_gameEngine);

		if (l_bomb.isValidIssue(p_gameEngine.getGameState(), p_gameEngine.getGameState().getPlayers().indexOf(this))) {
			d_listOrders.add(l_bomb);
			return true;
		}
		return false;
	}

	private boolean issueAirliftCommand(GameEngine p_gameEngine, String l_sourceCountry, String l_targetCountry,
			int l_numberOfArmies) {
		Airlift l_airlift = new Airlift(l_sourceCountry, l_targetCountry, l_numberOfArmies, p_gameEngine);

		if (l_airlift.isValidIssue(p_gameEngine.getGameState(),
				p_gameEngine.getGameState().getPlayers().indexOf(this))) {
			d_listOrders.add(l_airlift);
			return true;
		}
		return false;
	}

	private boolean issueBlockadeOrder(GameEngine p_gameEngine, String l_targetCountry) {
		Blockade l_blockade = new Blockade(l_targetCountry, p_gameEngine);

		if (l_blockade.isValidIssue(p_gameEngine.getGameState(),
				p_gameEngine.getGameState().getPlayers().indexOf(this))) {
			d_listOrders.add(l_blockade);
			return true;
		}
		return false;
	}

	/**
	 * get next order in the order list of a player
	 *
	 * @return An order to be executed next
	 */
	public Order next_order() {
		return d_listOrders.poll();
	}

	/**
	 * To get the count of a particular card that the player holds
	 * 
	 * @param p_cardName The name of the card
	 * @return The count of the card
	 */
	public int getCardCount(String p_cardName) {
		return d_listOfCards.get(p_cardName);
	}

	/**
	 * To increment the value of the card by 1
	 * 
	 * @param p_cardName The name of the card whose value is to be changed
	 */
	public void increaseCardCount(String p_cardName) {
		d_listOfCards.put(p_cardName, d_listOfCards.get(p_cardName) + 1);
	}

	/**
	 * To decrement the number of cards by 1
	 * 
	 * @param p_cardName The name of the card whose value is to be changed
	 */
	public void decreaseCardCount(String p_cardName) {
		d_listOfCards.put(p_cardName, d_listOfCards.get(p_cardName) - 1);
	}

	/**
	 * To display the card count of each card
	 */
	public void displayCards() {
		for (HashMap.Entry<String, Integer> l_cards : d_listOfCards.entrySet()) {
			System.out.println("The count of the Card :" + l_cards.getKey() + " is:" + l_cards.getValue());
		}
	}

	/**
	 * add the name of the player who made deal not to attack
	 *
	 * @param p_negotiatedPlayer add the name of player who made negotiation
	 */
	public void addNegotiatedPlayer(Player p_negotiatedPlayer) {
		this.d_negotiatedWith.add(p_negotiatedPlayer);
	}

	/**
	 * Getter for d_negotiate
	 *
	 * @return the name of the player whom player has negotiated with
	 */
	public List<Player> getD_negotiatedWith() {
		return this.d_negotiatedWith;
	}

	/**
	 * Reset the negotiation
	 */
	public void resetTheNegotiation() {
		d_negotiatedWith.clear();
	}

}
