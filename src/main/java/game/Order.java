package game;


/*
 * abstract interface of Order class to represent player's orders
 * All order types will inherit from Order class
 */
public interface Order {
	
	public void execute();
	
	public boolean isValid(State p_state);
	
	public void addOrderID(String p_id);
	
	public String toString();
}
