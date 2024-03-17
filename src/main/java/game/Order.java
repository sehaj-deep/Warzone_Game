package game;

/**
 * abstract interface of Order class to represent player's orders All order
 * types will inherit from Order class
 */
public abstract class Order {
	protected GameEngineNew d_gameEngine;
	protected String d_orderName;

	public Order(GameEngineNew p_gameEngineNew, String p_orderName) {
		this.d_orderName = p_orderName;
		this.d_gameEngine = p_gameEngineNew;
	}

	/**
	 * execute the order
	 *
	 * @param p_state    is the game state at the current moment
	 * @param p_playerId the id of player gave this order
	 */
	public abstract void execute(GameState p_state, int p_playerId);

	/**
	 * check the validity of the order for Issue Order Phase
	 *
	 * @param p_state    is the current game state storing how many armies in
	 *                   country, player, etc
	 * @param p_playerId is the id of the player who gave this order
	 * @return true if the order is valid
	 */
	public abstract boolean isValidIssue(GameState p_state, int p_playerId);

	/**
	 * check the validity of the order for Execute Order Phase
	 *
	 * @param p_state    is the current game state storing how many armies in
	 *                   country, player, etc
	 * @param p_playerId is the id of the player who gave this order
	 * @return true if the order is valid
	 */
	public abstract boolean isValidExecute(GameState p_state, int p_playerId);

	/**
	 * change the game state to accommodate the given order This method does not
	 * execute the order but modify the game state to validate succeeding orders
	 *
	 * @param p_state    is the current game state storing how many armies in
	 *                   country, player, etc
	 * @param p_playerId is the id of the player who gave this order
	 */
	public abstract void changeGameState(GameState p_state, int p_playerId);

	/**
	 * string representation of Order class auto invoked in print statement
	 */
	public String toString() {
		return this.d_orderName;
	}
}
