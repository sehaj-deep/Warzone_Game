package game;

import java.util.*;

/**  Player Class
 * this class stores information and the state of the game about a player
 */


public class Player {
	private int d_playerId;  // player id
	private String d_playerName;  // player name
	private Queue<Order> d_listOrders;  // list of orders issued by the player
	private Set<Integer> d_ownership;  // set of countries owned by the player
	
	
	/*
	 * Parameterized constructor for the player
	 * 
	 * @param p_player_id is a player id in integer 
	 */
	Player (int p_playerId) {
		d_playerId = p_playerId;
		d_listOrders = new LinkedList<>();
		d_ownership = new HashSet<Integer>();
	}

	/*
	 * get this player's id
	 * 
	 * @return player's id
	 */
	public int getPlayerId() {
		return d_playerId;
	}

	/*
	 * get this player's name
	 *
	 * @return player's name
	 */
	public String getPlayerName() {
		return d_playerName;
	}

	/*
	 * set this player's name
	 *
	 * @param new player's name
	 */
	public void setPlayerName(String p_playerName) {
		d_playerName = p_playerName;
	}


	
	/*
	 * get the countries owned by this player
	 * 
	 * @return a set of countries owned by the player
	 */
	public Set<Integer> getOwnership() {
		return d_ownership;
	}


	/*
	* set the player's countries owned set to the set provided
	*
	* @param a new set of countries owned
	 */
	public void setOwnership(Set<Integer> p_ownership) {
		d_ownership = p_ownership;
	}
	
	/*
	 * get the list of orders issued by this player
	 * 
	 * @return a queue data structure storing the orders in the order of issuance
	 */
	public Queue<Order> getListOrders() {
		return d_listOrders;
	}
	
	/*
	 * issue an order for this round
	 * 
	 * @param new order a player wants to take in the current round
	 */
	public void issue_order(Order p_newOrder) {
		d_listOrders.add(p_newOrder);
	}
	
	/*
	 * get next order in the order list of a player
	 * 
	 * @return An order to be executed next
	 */
	public Order next_order() {
		return d_listOrders.poll();
	}
}
