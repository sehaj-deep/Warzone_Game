package game;

import utils.ValidationException;

/**
 * Implementation of Deploy order command from Order class
 */
public class Deploy extends Order {
	private int d_numArmy; // number of armies to be deployed
	private String d_countryId; // name of country where armies will be deployed to

	/**
	 * Parameterized Constructor of Deploy class
	 * 
	 * @param p_numArmy       is a number of armies for deployment
	 * @param p_countryId     is the country where armies will be placed
	 * @param p_gameEngineNew the game engine of the game
	 */
	public Deploy(int p_numArmy, String p_countryId, GameEngineNew p_gameEngineNew) {
		super(p_gameEngineNew, "Deploy");
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
	 * @param p_state    is the game state for the requesting player at the current
	 *                   moment
	 * @param p_playerId the id of player gave this deploy order
	 * @return true if the order is valid. false if not a valid move
	 */
	@Override
	public boolean isValidIssue(GameState p_state, int p_playerId) {
		String l_errMessage = "";
		try {
			if (!p_state.getPlayers().get(p_playerId).getOwnership().contains(d_countryId)) {
				// country where army to be deployed is not owned by the player issued the order
				l_errMessage = "Country where army to be deployed is not owned by the player issued the order";
				throw new ValidationException(l_errMessage);
			}
			if (d_numArmy < 0) {
				// can't pass negative number for number of armies for deployment
				l_errMessage = "Can't pass negative number for number of armies for deployment";
				throw new ValidationException(l_errMessage);
			}
			if (d_numArmy > p_state.getReinforcements().get(p_playerId)) {
				// deploying more armies than the player has
				l_errMessage = "Can't deploy more armies than the reinforcement armies available to the player";
				throw new ValidationException(l_errMessage);
			}
		}
		catch (ValidationException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	/**
	 * Validate whether the given deploy order is legal for execution
	 * 
	 * @param p_state    is the game state for the requesting player at the current
	 *                   moment
	 * @param p_playerId the id of player gave this deploy order
	 * @return true if the order is valid. false if not a valid move
	 */
	@Override
	public boolean isValidExecute(GameState p_state, int p_playerId) {
		String l_errMessage = "";
		try {
			if (!p_state.getPlayers().get(p_playerId).getOwnership().contains(d_countryId)) {
				// country where army to be deployed is not owned by the player issued the order
				l_errMessage = "Country where army to be deployed is not owned by the player issued the order";
				throw new ValidationException();
			}
			if (d_numArmy < 0) {
				// can't pass negative number for number of armies for deployment
				l_errMessage = "Can't pass negative number for number of armies for deployment";
				throw new ValidationException();
			}
		}
		catch (ValidationException e) {
			System.out.println(l_errMessage);
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
		int l_reinforcementAvailable = p_state.getReinforcements().get(p_playerId);
		p_state.getReinforcements().set(p_playerId, l_reinforcementAvailable - d_numArmy);
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
		int l_currNumArmy = p_state.getGameBoard().get(d_countryId);
		p_state.getGameBoard().put(d_countryId, l_currNumArmy + d_numArmy);
	}

	/**
	 * Add order's id to the order name to differentiate an order from others in the
	 * same class used mainly for debugging
	 * 
	 * @param p_id is the distinguishable order id
	 */
	public void addOrderID(String p_id) {
		d_orderName = d_orderName + p_id;
	}
}