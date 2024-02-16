package game;

/**
 * Implementation of Deploy order command from Order class
 */
public class Deploy implements Order {
	private int d_numArmy;  // number of armies to be deployed
	private int d_countryId;  // id of country where armies will be deployed to
	private String d_orderName = "Deploy";  // name of the order type
	
	/**
	 * Parameterized Constructor of Deploy class
	 * 
	 * @param p_numArmy is a number of armies for deployment
	 * @param p_countryId is the country where armies will be placed
	 */
	Deploy(int p_numArmy, int p_countryId) {
		d_numArmy = p_numArmy;
		d_countryId = p_countryId;
	}
	
	/**
	 * Validate whether the given deploy order is legal according to the rules of the game
	 * 
	 * @param p_state is the game state for the requesting player at the current moment
	 * @return true if the order is valid. false if not a valid move
	 */
	@Override
	public boolean isValid(GameState p_state, int p_playerId) {
		// p_remaining_amry is number of armies in available for deployment in the remaining reinforcements 
		if (!p_state.getPlayers().get(p_playerId).getOwnership().contains(d_countryId)) {
			// country where army to be deployed is not owned by the player issued the order
			return false;
		}
		if (d_numArmy < 0) {
			// can't pass negative number for number of armies for deployment
			return false;
		}
		if (d_numArmy > p_state.getReinforcements().get(p_playerId) ) {
			// deploying more armies than the player has
			return false;
		}
		return true;
	}
	
	/**
	 * Execute this deployment order 
	 */
	@Override
	public void execute() {
		/*
		p_state.getReinforcements().set(p_playerId) = p_state.getReinforcements().get(p_playerId) - d_num_army
		 */
		System.out.println("IMPLEMENT EXECUTION");
	}
	
	/**
	 * Add order's id to the order name to differentiate an order from others in the same class
	 * 
	 * @param p_id is the distinguishable order id
	 */
	@Override
	public void addOrderID(String p_id) {
		d_orderName = d_orderName + p_id;
	}
	
	/**
	 * String format of the Deployment class for print statement
	 * 
	 * @return order name which is "Deploy"
	 */
	@Override
	public String toString() {
		return d_orderName;
	}
}
