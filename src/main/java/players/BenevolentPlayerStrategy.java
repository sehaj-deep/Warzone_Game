package players;

import java.util.LinkedList;
import java.util.Queue;
import game.GameEngine;
import map.Country;
import orders.Advance;
import orders.Deploy;
import orders.Order;

/**
 * Benevolent Player's issue order implementation using player strategy
 */
public class BenevolentPlayerStrategy extends PlayerStrategy {
	/**
	 * The name of the weakest country owned by this player
	 */
	private String d_weakest;

	/**
	 * Boolean representing whether issuing Deploy order is allowed currently
	 */
	private boolean d_canDeploy = true;

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
		if (d_canDeploy) { // Make deploy order first
			l_order = deployToWeakest(p_player, p_gameEngine);
			l_currPhasePtr = d_canDeploy;
		}
		else if (getHasOrder()) { // After deploy, now move armies to weaker countries
			// No need to create card Order because benevolent player will never get a card
			// as the player does not attack
			if (d_waitingOrders.size() == 0) {
				// has not issued Advance orders yet, so add all Advance orders to waitingOrders
				moveToWeakerNeighbors(p_player, p_gameEngine);
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
		}
		else {
			// if the order is not valid, then ignore and skip the order
			return null;
		}
	}

	/**
	 * Find the weakest country
	 * 
	 * @param p_player     player whom this strategy belongs to
	 * @param p_gameEngine current game engine where this game runs
	 */
	public void findWeakestCountry(Player p_player, GameEngine p_gameEngine) {
		String l_weakest = ""; // name of the weakest country found so far
		int l_fewestNumArmy = Integer.MAX_VALUE; // number of armies in the weakest country found so far
		for (String l_countryName : p_player.getOwnership()) {
			int l_numArmy = p_gameEngine.getGameBoard().get(l_countryName);
			// Strongest iff a country has most army and located in the border
			if (l_numArmy <= l_fewestNumArmy) {
				l_fewestNumArmy = l_numArmy;
				l_weakest = l_countryName;
			}
		}
		d_weakest = l_weakest;
	}

	/**
	 * Deploy all the reinforcement armies to the weakest country of the player
	 * 
	 * @param p_player     player whom this strategy belongs to
	 * @param p_gameEngine current game engine where this game runs
	 * @return Order that deploys all bonus armies to the weakest country
	 */
	public Order deployToWeakest(Player p_player, GameEngine p_gameEngine) {
		int l_playerIdx = p_gameEngine.getPlayers().indexOf(p_player);
		findWeakestCountry(p_player, p_gameEngine);
		d_canDeploy = false;
		return new Deploy(d_weakest, p_gameEngine.getReinforcements().get(l_playerIdx), p_gameEngine);
	}

	/**
	 * Move armies to a weaker neighbor of the country for all countries owned by
	 * this player. Weak country means less armies compared to a neighboring
	 * country. This function will create Advance object for one move from a country
	 * to its neighbor, and add that Advance object to d_waitingOrders queue
	 * 
	 * @param p_player     player whom this strategy belongs to
	 * @param p_gameEngine current game engine where this game runs
	 */
	public void moveToWeakerNeighbors(Player p_player, GameEngine p_gameEngine) {
		for (String l_countryName : p_player.getOwnership()) {
			Country l_country = p_gameEngine.getD_countries(l_countryName);
			int l_numArmy = p_gameEngine.getGameBoard().get(l_countryName);
			for (Country l_neighbor : l_country.getNeighbors()) {
				if (p_player.getOwnership().contains(l_neighbor.getD_name())) {
					int l_numArmyNeighbor = p_gameEngine.getGameBoard().get(l_neighbor.getD_name());
					if (l_numArmy > l_numArmyNeighbor) { // such neighbor is a weaker country
						// l_country will send armies to this neighbor so that the armies in
						// this neighbor almost same as average of l_country's armies over l_country's
						// out degree including l_country itself
						int l_numDeployedArmy = l_numArmy / (l_country.getNeighbors().size() + 1);
						l_numDeployedArmy = l_numDeployedArmy - l_numArmyNeighbor;
						// validation of the order will be done in create order for efficiency
						Advance l_order = new Advance(l_countryName, l_neighbor.getD_name(), l_numDeployedArmy, p_gameEngine);
						d_waitingOrders.add(l_order);
					}
				}
			}
		}
	}

	/**
	 * Read d_weakest data member of this class
	 * 
	 * @return the name of the weakest country owned by this player
	 */
	public String getWeakest() {
		return d_weakest;
	}

	/**
	 * Update d_weakest data member of this class
	 * 
	 * @param p_weakest a new name of the weakest country
	 */
	public void setWeakest(String p_weakest) {
		d_weakest = p_weakest;
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
	 * Reset data attributes of PlayerStrategy object
	 */
	@Override
	protected void reset() {
		d_canDeploy = true;
		setHasOrder(true);
		d_weakest = null;
		d_waitingOrders = new LinkedList<>();
	}
}