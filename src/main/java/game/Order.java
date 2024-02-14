package game;


/*
 * abstract interface of Order class to represent player's orders
 * All order types will inherit from Order class
 */
public interface Order {
	
	public void execute();
	
	public boolean isValid(GameState p_state, int p_playerID);
	
	public void addOrderID(String p_id);
	
	public String toString();
}
