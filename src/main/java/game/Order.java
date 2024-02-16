package game;


/**
 * abstract interface of Order class to represent player's orders
 * All order types will inherit from Order class
 */
public interface Order {

	/** execute the order
	 *
	 */
	public void execute();

	/** check the validity of the order
	 *
	 * @param p_state is the current game state storing how many armies in country, player, etc
	 * @param p_playerID is the id of the player who gave this order
	 */
	public boolean isValid(GameState p_state, int p_playerID);

	/** add order ID to order name (mainly for debugging purpose)
	 *  to help differentiate this order from the orders from the same type
	 *
	 * @param p_id is the order id
	 */
	public void addOrderID(String p_id);

	/** string representation of Order class
	 *  auto invoked in print statement
	 */
	public String toString();
}
