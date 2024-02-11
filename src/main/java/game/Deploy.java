package game;

/*
 * Implementation of Deploy order command from Order class
 */
public class Deploy implements Order {
	private int d_num_army;  // number of armies to be deployed
	private int d_country_id;  // id of country where armies will be deployed to
	private String d_order_name = "Deploy";  // name of the order type
	
	/*
	 * Parameterized Constructor of Deploy class
	 * 
	 * @param p_num_army is a number of armies for deployment
	 * @param p_country_id is the country where armies will be placed
	 */
	Deploy(int p_num_army, int p_country_id) {
		d_num_army = p_num_army;
		d_country_id = p_country_id;
	}
	
	/*
	 * Validate whether the given deploy order is legal according to the rules of the game
	 * 
	 * @param state is the game state for the requesting player at the current moment
	 * @return true if the order is valid. false if not a valid move
	 */
	@Override
	public boolean isValid(State state) {
		// p_remaining_amry is number of armies in available for deployment in the remaining reinforcements 
		if (!state.d_ownership.contains(d_country_id)) {
			// country where army to be deployed is not owned by the player issued the order
			return false;
		}
		if (d_num_army < 0) {
			// can't pass negative number for number of armies for deployment
			return false;
		}
		if (d_num_army > state.d_available_army ) {
			// deploying more armies than the player has
			return false;
		}
		return true;
	}
	
	/*
	 * Execute this deployment order 
	 */
	@Override
	public void execute() {
		/*
		 */
		System.out.println("IMPLEMENT EXECUTION");
	}
	
	/*
	 * Add order's id to the order name to differentiate an order from others in the same class
	 * 
	 * @param p_id is the distinguishable order id
	 */
	@Override
	public void addOrderID(String p_id) {
		d_order_name = d_order_name + p_id;
	}
	
	/*
	 * String format of the Deployment class for print statement
	 * 
	 * @return order name which is "Deploy"
	 */
	@Override
	public String toString() {
		return d_order_name;
	}
}
