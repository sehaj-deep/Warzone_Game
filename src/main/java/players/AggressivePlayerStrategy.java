package players;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import game.GameEngine;
import map.Country;
import orders.Advance;
import orders.Deploy;
import orders.Order;

/**
 * Aggressive Player's issue order implementation using player strategy
 */
public class AggressivePlayerStrategy extends PlayerStrategy implements Serializable {
	/**
	 * Boolean representing whether issuing Deploy order is allowed currently
	 */
	private boolean d_canDeploy = true;

	/**
	 * Boolean representing whether issuing attacking type Advance order is allowed
	 * currently
	 */
	private boolean d_canAttack = true;

	/**
	 * The name of the strongest country owned by this player
	 */
	private String d_strongest = null;

	/**
	 * Queue of orders waiting to be added to player's order's list
	 */
	private Queue<Order> d_waitingOrders = new LinkedList<>();

	/**
	 * Create an order to be issued by the player using this PlayerStrategy
	 * 
	 * @param p_player     Player object that uses this PlayerStrategy
	 * @param p_tokens     string tokens from user commands. Expect null for this
	 *                     PlayerStrategy
	 * @param p_gameEngine a context object for ExecuteOrders phase
	 * @return a new order to be issued
	 */
	@Override
	protected Order createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine) {
		int l_playerIdx = p_gameEngine.getPlayers().indexOf(p_player);
		Order l_order = null;
		boolean l_currPhasePtr;
		if (d_canDeploy) {
			l_order = deployAggressive(p_player, p_gameEngine);
			l_currPhasePtr = d_canDeploy;
		} else if (d_canAttack) {
			l_order = orderAttack(p_player, p_gameEngine);
			l_currPhasePtr = d_canAttack;
		} else if (getHasOrder()) { // After deploy, now move armies to weaker countries
			// No need to create card Order because benevolent player will never get a card
			// as the player does not attack
			if (d_waitingOrders.size() == 0) {
				// has not issued Advance orders yet, so add all Advance orders to waitingOrders
				moveToStrongestNeighbor(p_player, p_gameEngine);
			}
			if (d_waitingOrders.size() > 0) {
				l_order = d_waitingOrders.remove();
			}
			if (d_waitingOrders.size() == 0) {
				// if d_waitingOrder's size becomes 0 after remove,
				// then no more Orders from this player
				setHasOrder(false);
			}
		}
		if (l_order != null && l_order.isValidIssue(l_playerIdx)) {
			return l_order;
		} else {
			// if the order is not valid, then ignore and skip the order
			return null;
		}
	}

	/**
	 * Deploy all the reinforcement armies to the strongest country of the player
	 * 
	 * @param p_player     player whom this strategy belongs to
	 * @param p_gameEngine current game engine where this game runs
	 * @return Order that deploys all bonus armies to the strongest country
	 */
	public Order deployAggressive(Player p_player, GameEngine p_gameEngine) {
		int l_playerIdx = p_gameEngine.getPlayers().indexOf(p_player);
		findStrongestCountry(p_player, p_gameEngine);
		d_canDeploy = false;
		return new Deploy(d_strongest, p_gameEngine.getReinforcements().get(l_playerIdx), p_gameEngine);
	}

	/**
	 * Find the strongest country bordering enemy
	 * 
	 * @param p_player     player whom this strategy belongs to
	 * @param p_gameEngine current game engine where this game runs
	 */
	public void findStrongestCountry(Player p_player, GameEngine p_gameEngine) {
		String l_strongest = ""; // name of the strongest country found so far
		int l_mostNumArmy = 0; // number of armies in the strongest country found so far
		for (String l_countryName : p_player.getOwnership()) {
			int l_numArmy = p_gameEngine.getGameBoard().get(l_countryName);
			// To attack from the strongest, the country needs to border enemy
			boolean l_isBorder = false;
			Country l_country = p_gameEngine.getD_countries(l_countryName);
			for (Country l_neighbor : l_country.getNeighbors()) {
				if (!p_player.getOwnership().contains(l_neighbor.getD_name())) {
					l_isBorder = true;
					break;
				}
			}
			// Strongest if a country has most army and located in the border
			if (l_isBorder && l_numArmy >= l_mostNumArmy) {
				l_mostNumArmy = l_numArmy;
				l_strongest = l_countryName;
			}
		}
		d_strongest = l_strongest;
	}

	/**
	 * Create an attacking order from the strongest country to weakest enemy
	 * 
	 * @param p_player     Player object that uses this PlayerStrategy
	 * @param p_gameEngine a context object for ExecuteOrders phase
	 * @return a new order to be issued
	 */
	public Order orderAttack(Player p_player, GameEngine p_gameEngine) {
		Country l_country = p_gameEngine.getD_countries(d_strongest);
		// Find the weakest enemy territory in terms of number of army
		int l_fewestNumArmy = Integer.MAX_VALUE;
		String l_weakestEnemy = "";
		for (Country l_neighbor : l_country.getNeighbors()) {
			if (!p_player.getOwnership().contains(l_neighbor.getD_name())) {
				int l_numArmy = p_gameEngine.getGameBoard().get(l_neighbor.getD_name());
				if (l_numArmy <= l_fewestNumArmy) {
					l_fewestNumArmy = l_numArmy;
					l_weakestEnemy = l_neighbor.getD_name();
				}
			}
		}
		d_canAttack = false;
		// send all armies except one from my strongest to opponent's weakest for attack
		// one army needs to stay in a country
		int l_myNumArmy = p_gameEngine.getGameBoard().get(d_strongest) - 1;
		return new Advance(d_strongest, l_weakestEnemy, l_myNumArmy, p_gameEngine);
	}

	/**
	 * Read d_strongest data member of this class
	 * 
	 * @return the name of the strongest country owned by this player
	 */
	public String getStrongest() {
		return d_strongest;
	}

	/**
	 * Update d_strongest data member of this class
	 * 
	 * @param p_strongest a new name of the strongest country
	 */
	public void setStrongest(String p_strongest) {
		d_strongest = p_strongest;
	}

	/**
	 * Move armies to the strongest neighbor of the country for all countries owned
	 * by this player. Strong country means more armies compared to a neighboring
	 * country. This function will create Advance object for one move from a country
	 * to its neighbor, and add that Advance object to d_waitingOrders queue
	 * 
	 * @param p_player     player whom this strategy belongs to
	 * @param p_gameEngine current game engine where this game runs
	 */
	public void moveToStrongestNeighbor(Player p_player, GameEngine p_gameEngine) {
		for (String l_countryName : p_player.getOwnership()) {
			Country l_country = p_gameEngine.getD_countries(l_countryName);
			// max number of army initialized as this country's army because the strongest
			// neighbor needs to have more armies than this country to move
			int l_maxNumArmy = p_gameEngine.getGameBoard().get(l_countryName);
			String l_strongestNeighborName = null;
			for (Country l_neighbor : l_country.getNeighbors()) {
				if (p_player.getOwnership().contains(l_neighbor.getD_name())) {
					int l_numArmyNeighbor = p_gameEngine.getGameBoard().get(l_neighbor.getD_name());
					if (l_maxNumArmy < l_numArmyNeighbor) { // such neighbor is a stronger country

						l_maxNumArmy = l_numArmyNeighbor;
						l_strongestNeighborName = l_neighbor.getD_name();
					}

				}

			}
			if (l_strongestNeighborName != null) {
				// l_country will send all armies except 1 to the strongest neighbor
				// validation of the order will be done in create order for efficiency
				int l_numArmy = p_gameEngine.getGameBoard().get(l_countryName) - 1;
				Advance l_order = new Advance(l_countryName, l_strongestNeighborName, l_numArmy, p_gameEngine);
				d_waitingOrders.add(l_order);
			}
		}
	}

	/**
	 * Read d_waitingOrders data member of this class
	 * 
	 * @return a queue of Order objects to be added to the player's orders list
	 */
	public Queue<Order> getWaitingOrders() {
		return d_waitingOrders;
	}

	/**
	 * Read d_canDeploy data member of this class
	 * 
	 * @return a boolean representing whether this player can make Deploy order
	 */
	public boolean getCanDeploy() {
		return d_canDeploy;
	}

	/**
	 * Update d_canDeploy data member of this class
	 * 
	 * @param p_canDeploy a new boolean value whether this player can make Deploy
	 *                    order
	 */
	public void setCanDeploy(boolean p_canDeploy) {
		d_canDeploy = p_canDeploy;
	}

	/**
	 * Read d_canAttack data member of this class
	 * 
	 * @return a boolean representing whether this player can make Attacking order
	 */
	public boolean getCanAttack() {
		return d_canAttack;
	}

	/**
	 * Update d_canAttack data member of this class
	 * 
	 * @param p_canAttack a new boolean value whether this player can make Attacking
	 *                    order
	 */
	public void setCanAttack(boolean p_canAttack) {
		d_canAttack = p_canAttack;
	}

	/**
	 * Reset data attributes of PlayerStrategy object
	 */
	@Override
	public void reset() {
		d_canDeploy = true;
		d_canAttack = true;
		setHasOrder(true);
		d_strongest = null;
		d_waitingOrders = new LinkedList<>();
	}
}