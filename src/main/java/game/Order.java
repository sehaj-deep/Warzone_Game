package game;

/**
 * abstract interface of Order class to represent player's orders All order
 * types will inherit from Order class
 */
public interface Order {

	/**
	 * execute the order
	 *
	 * @param p_state    is the game state at the current moment
	 * @param p_playerId the id of player gave this order
	 */
	public void execute(GameState p_state, int p_playerId);

	/**
	 * check the validity of the order
	 *
	 * @param p_state       is the current game state storing how many armies in
	 *                      country, player, etc
	 * @param p_playerId    is the id of the player who gave this order
	 * @param p_isExecution true if in execution phase. false else
	 * @return true if the order is valid
	 */
	public boolean isValid(GameState p_state, int p_playerId, boolean p_isExecution);

	/**
	 * change the game state to accommodate the given order This method does not
	 * execute the order but modify the game state to validate succeeding orders
	 *
	 * @param p_state    is the current game state storing how many armies in
	 *                   country, player, etc
	 * @param p_playerId is the id of the player who gave this order
	 */
	public void changeGameState(GameState p_state, int p_playerId);

	/**
	 * add order ID to order name (mainly for debugging purpose) to help
	 * differentiate this order from the orders from the same type
	 *
	 * @param p_id is the order id
	 */
	public void addOrderID(String p_id);

	/**
	 * string representation of Order class auto invoked in print statement
	 */
	public String toString();
}
