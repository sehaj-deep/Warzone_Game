package game;

/**
 * Implementation of Deploy order command from Order class
 */
public class Deploy implements Order {
	private int d_numArmy; // number of armies to be deployed
	private String d_countryId; // name of country where armies will be deployed to
	private String d_orderName = "Deploy"; // name of the order type

	/**
	 * Parameterized Constructor of Deploy class
	 * 
	 * @param p_numArmy   is a number of armies for deployment
	 * @param p_countryId is the country where armies will be placed
	 */
	public Deploy(int p_numArmy, String p_countryId) {
		d_numArmy = p_numArmy;
		d_countryId = p_countryId;
	}

	/**
	 * getter function to access d_numArmy
	 *
	 * @return number of army specified in the deploy command
	 */
	public int getNumArmy() {
		return d_numArmy;
	}

	/**
	 * getter function to access d_countryId
	 *
	 * @return country name of the destination of deployed army in this order
	 */
	public String getCountryName() {
		return d_countryId;
	}

	/**
	 * Validate whether the given deploy order is legal according to the rules of
	 * the game if valid, the number of reinforcement available for the given player
	 * will be reduced by the d_numArmy
	 * 
	 * @param p_state       is the game state for the requesting player at the
	 *                      current moment
	 * @param p_playerId    the id of player gave this deploy order
	 * @param p_isExecution true if in execution phase. false else
	 * @return true if the order is valid. false if not a valid move
	 */
	@Override
	public boolean isValid(GameState p_state, int p_playerId, boolean p_isExecution) {
		// p_remaining_amry is number of armies in available for deployment in the
		// remaining reinforcements
		if (!p_state.getPlayers().get(p_playerId).getOwnership().contains(d_countryId)) {
			// country where army to be deployed is not owned by the player issued the order
			return false;
		}
		if (d_numArmy < 0) {
			// can't pass negative number for number of armies for deployment
			return false;
		}
		if (d_numArmy > p_state.getReinforcements().get(p_playerId) && !p_isExecution) {
			// deploying more armies than the player has
			return false;
		}
		return true;
	}

	/**
	 * reduce the number of reinforcement available for the given player by the
	 * d_numArmy This helps validation of succeeding deploy orders
	 *
	 * @param p_state    is the game state for the requesting player at the current
	 *                   moment
	 * @param p_playerId the id of player gave this deploy order
	 */
	@Override
	public void changeGameState(GameState p_state, int p_playerId) {
		int reinforcementAvailable = p_state.getReinforcements().get(p_playerId);
		// reinforcementAvailable = reinforcementAvailable - d_numArmy;
		// System.out.println("In method: " + reinforcementAvailable);
		p_state.getReinforcements().set(p_playerId, reinforcementAvailable - d_numArmy);
	}

	/**
	 * Execute this deployment order i.e., move some reinforcement armies to the
	 * destination country
	 *
	 * @param p_state    is the game state at the current moment
	 * @param p_playerId the id of player gave this deploy order
	 */
	@Override
	public void execute(GameState p_state, int p_playerId) {
		int currNumArmy = p_state.getGameBoard().get(d_countryId);
		p_state.getGameBoard().put(d_countryId, currNumArmy + d_numArmy);
	}

	/**
	 * Add order's id to the order name to differentiate an order from others in the
	 * same class used mainly for debugging
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
