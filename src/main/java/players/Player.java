package players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import game.GameEngine;
import orders.Order;
import utils.ValidationException;

/**
 * Player Class this class stores information and the state of the game about a
 * player
 */

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * player name
	 */
	private String d_playerName;

	/**
	 * The player's strategy type
	 */
	private PlayerStrategy d_playerStrategy;

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
	 * Parameterized constructor for the player that requires PlayerStrategy
	 * 
	 * @param p_playerName     p_playerName To uniquely identify the player
	 * @param p_playerStrategy the behavior that defines how player plays the game
	 */
	public Player(String p_playerName, PlayerStrategy p_playerStrategy) {
		d_playerName = p_playerName;
		d_listOrders = new LinkedList<>();
		d_ownership = new HashSet<String>();
		d_listOfCards = new HashMap<>();
		d_playerStrategy = p_playerStrategy;
		d_listOfCards.put("Bomb", 0);
		d_listOfCards.put("Blockade", 0);
		d_listOfCards.put("Airlift", 0);
		d_listOfCards.put("Diplomacy", 0);
	}

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
	 * Gets the player strategy.
	 * 
	 * @return The player strategy.
	 */
	public PlayerStrategy getD_playerStrategy() {
		return d_playerStrategy;
	}

	/**
	 * Sets the player strategy.
	 * 
	 * @param d_playerStrategy The player strategy to set.
	 */
	public void setD_playerStrategy(PlayerStrategy d_playerStrategy) {
		this.d_playerStrategy = d_playerStrategy;
	}

	/**
	 * Parses and processes the issued order based on the provided command tokens.
	 * 
	 * @param p_tokens     The array of tokens representing the issued command.
	 * @param p_gameEngine The game engine instance managing the game state
	 * @throws ValidationException If the order issued is not valid.
	 */
	public void issue_order(String[] p_tokens, GameEngine p_gameEngine) throws ValidationException {
		Order l_order = d_playerStrategy.createOrder(this, p_tokens, p_gameEngine);
		if (l_order != null) {
			this.d_listOrders.add(l_order);
		}
		else {
			throw new ValidationException();
		}
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
		System.out.println(this.getPlayerName() + "'s cards in hand");
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

	/**
	 * Access d_playerStrategy data member of this class object
	 * 
	 * @return PlayerStrategy object that defines the behavior of this player
	 */
	public PlayerStrategy getPlayerStrategy() {
		return d_playerStrategy;
	}

	/**
	 * Update d_playerStrategy data member of this class object
	 *
	 * @param p_playerStrategy new PlayerStrategy class object
	 */
	public void setPlayerStrategy(PlayerStrategy p_playerStrategy) {
		d_playerStrategy = p_playerStrategy;
	}
}