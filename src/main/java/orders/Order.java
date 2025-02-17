package orders;

import java.io.Serializable;

import game.GameEngine;

/**
 * abstract interface of Order class to represent player's orders All order
 * types will inherit from Order class
 */
public abstract class Order implements Serializable {

	/**
	 * The game engine associated with the order.
	 */
	protected GameEngine d_gameEngine;

	/**
	 * The name of the order
	 */
	protected String d_orderName;

	/**
	 * Indicates whether the order is an attack move
	 */
	private boolean d_isAttack;

	/**
	 * The name of the target country for the order
	 */
	protected String d_targetCountry;

	/**
	 * Constructs a new Order object with the provided game engine and order name.
	 * 
	 * @param p_gameEngineNew The game engine providing the context for the order.
	 * @param p_orderName     The name of the order.
	 */

	protected Order(GameEngine p_gameEngineNew, String p_orderName) {
		this.d_orderName = p_orderName;
		this.d_gameEngine = p_gameEngineNew;
		this.d_isAttack = false;
	}

	/**
	 * Access d_isAttack data variable
	 * 
	 * @return true if this order is attacking move. else, false
	 */
	public boolean getIsAttack() {
		return this.d_isAttack;
	}

	/**
	 * access d_targetCountry data variable
	 * 
	 * @return target country name
	 */
	public String getTargetCountry() {
		return d_targetCountry;
	}

	/**
	 * Update d_isAttack data variable
	 * 
	 * @param p_isAttack a new boolean value denoting whether this order is for an
	 *                   attack
	 */
	public void setIsAttack(boolean p_isAttack) {
		this.d_isAttack = p_isAttack;
	}

	/**
	 * execute the order
	 *
	 * @param p_playerId the id of player gave this order
	 */
	public abstract void execute(int p_playerId);

	/**
	 * check the validity of the order for Issue Order Phase
	 *
	 * @param p_playerId is the id of the player who gave this order
	 * @return true if the order is valid
	 */
	public abstract boolean isValidIssue(int p_playerId);

	/**
	 * check the validity of the order for Execute Order Phase
	 *
	 * @param p_playerId is the id of the player who gave this order
	 * @return true if the order is valid
	 */
	public abstract boolean isValidExecute(int p_playerId);

	/**
	 * string representation of Order class auto invoked in print statement
	 */
	public String toString() {
		return this.d_orderName;
	}
}
