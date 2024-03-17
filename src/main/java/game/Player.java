package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.*;

/**
 * Player Class this class stores information and the state of the game about a
 * player
 */

public class Player {
	private String d_playerName; // player name
	private Queue<Order> d_listOrders; // list of orders issued by the player
	private Set<String> d_ownership; // set of countries owned by the player
	private HashMap<String, Integer> d_listOfCards;
	List<Player> d_negotiatedWith = new ArrayList<Player>();

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
		d_listOfCards.put("bomb", 0);
		d_listOfCards.put("blockade", 0);
		d_listOfCards.put("airlift", 0);
		d_listOfCards.put("diplomacy", 0);
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
	 * issue an order for this round
	 *
	 * @param p_newOrder new order a player wants to take in the current round
	 */
	public void issue_order(Order p_newOrder) {
		d_listOrders.add(p_newOrder);
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
	 * add the name of the player who made deal not to attack
	 *
	 * @param p_negotiatedPlayer add the name of player who made negotiation
	 */
	public void addNegotiatedPlayer(Player p_negotiatedPlayer ) {
		this.d_negotiatedWith.add(p_negotiatedPlayer);
	}

	/**
	 * Reset the negotiation
	 */
	public void resetTheNegotiation() {
		d_negotiatedWith.clear();
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
	 * get the list of the card owned by player
	 *
	 * @return the card owned by player
	 */
	public HashMap<String, Integer> getD_listOfCards() {
		return d_listOfCards;
	}

	/**
	 * To increment the value of the card by 1
	 *
	 * @param p_cardName The name of the card whose value is to be changed
	 */
	public void increaseCardCount(String p_cardName) {
		d_listOfCards.put(p_cardName, d_listOfCards.get(p_cardName) + 1);
	}

}