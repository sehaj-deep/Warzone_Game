package game;

import java.util.*;

/**
 * Player Class
 * this class stores information and the state of the game about a player
 */


public class Player {
    private String d_playerName;  // player name
    private Queue<Order> d_listOrders;  // list of orders issued by the player
    private Set<String> d_ownership;  // set of countries owned by the player


    /**
     * Parameterized constructor for the player
     *
     * @param p_playerId is a player id in integer
     */
    public Player(String p_playerName) {
        //d_playerId = p_playerId;
    		d_playerName = p_playerName;
        d_listOrders = new LinkedList<>();
        d_ownership = new HashSet<String>();
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
}
