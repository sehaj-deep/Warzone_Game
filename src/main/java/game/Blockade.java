package game;

import java.util.Set;

import utils.ValidationException;

/**
 * The Blockade class represents a command to blockade a territory in the game.
 * When executed, the army count in the target territory is tripled, and the
 * territory becomes neutral. Inherits from the Order class.
 */
public class Blockade extends Order {
	
	/**
     * The ID of the country to be blockaded.
     */
	private String d_countryId;

	/**
	 * Constructs a Blockade object with the specified country ID and game engine.
	 * 
	 * @param p_countryId     The ID of the country to be blockaded.
	 * @param p_gameEngineNew The game engine associated with the order.
	 */
	public Blockade(String p_countryId, GameEngine p_gameEngineNew) {
		super(p_gameEngineNew, "Blockade");
		d_countryId = p_countryId;
	}

	/**
	 * Gets the country ID associated with the blockade order.
	 * 
	 * @return The country ID.
	 */
	public String getD_countryId() {
		return d_countryId;
	}

	/**
	 * Executes the blockade order by tripling the army count in the target
	 * territory and removing ownership of the territory from the player.
	 * 
	 * @param p_state    The current game state.
	 * @param p_playerId The ID of the player executing the order.
	 */
	@Override
	public void execute(GameState p_state, int p_playerId) {
		int currNumArmy = p_state.getGameBoard().get(d_countryId);
		p_state.getGameBoard().put(d_countryId, currNumArmy * 3);

		for (Player l_player : p_state.getPlayers()) {
			Set<String> l_playerCountries = l_player.getOwnership();
			if (l_playerCountries.contains(d_countryId)) {
				l_playerCountries.remove(d_countryId);
				break;
			}
		}

		System.out.println("Blockade executed: " + d_countryId + " is now neutral and army unites have tripled.");
	}

	/**
	 * Checks if the blockade order is valid to issue.
	 * 
	 * @param p_state    The current game state.
	 * @param p_playerId The ID of the player issuing the order.
	 * @return True if the order is valid to issue, false otherwise.
	 */
	@Override
	public boolean isValidIssue(GameState p_state, int p_playerId) {
		String l_errMessage = "";
		try {
			if (p_state.getGameBoard().get(d_countryId) <= 0) {
				l_errMessage = "Blockade can't be issued on country without any army units";
				throw new ValidationException();
			}
		} catch (ValidationException e) {
			System.out.println(l_errMessage);
			return false;
		}
		return true;
	}

	/**
	 * Checks if the blockade order is valid to execute.
	 * 
	 * @param p_state    The current game state.
	 * @param p_playerId The ID of the player executing the order.
	 * @return True if the order is valid to execute, false otherwise.
	 */
	@Override
	public boolean isValidExecute(GameState p_state, int p_playerId) {
		return this.isValidIssue(p_state, p_playerId);
	}

	/**
	 * Changes the game state after executing the blockade order. This method is
	 * empty as the execution does not change any game state other than the game
	 * board.
	 * 
	 * @param p_state    The current game state.
	 * @param p_playerId The ID of the player executing the order.
	 */
	@Override
	public void changeGameState(GameState p_state, int p_playerId) {
		// No changes required in game state after executing the blockade order
	}
}
