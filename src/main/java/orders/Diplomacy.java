package orders;

import game.GameEngine;
import game.GameState;
import players.Player;

/**
 * The diplomacy class check if attack is valid or not based on player using diplomacy card.
 */
public class Diplomacy extends Order {

	/**
	 * Target player
	 */
	String d_targetPlayer;

	/**
	 * Constructor for Diplomacy class
	 *
	 * @param p_newGameEngine the engine of the game
	 * @param p_targetPlayer  name of the opponent player
	 */
	public Diplomacy(String p_targetPlayer, GameEngine p_newGameEngine) {
		super(p_newGameEngine, "Diplomacy");
		this.d_targetPlayer = p_targetPlayer;
	}

	/**
	 * This class return boolean value after checking if the command is valid
	 *
	 * @param p_state    is the current game state storing how many armies in
	 *                   country, player, etc
	 * @param p_playerId is the id of the player who gave this order
	 * @return the boolean value true if the command is valid else false
	 */
	@Override
	public boolean isValidIssue(GameState p_state, int p_playerId) {

		Player issuingPlayer = p_state.getPlayers().get(p_playerId);

		// Check if the player has the Diplomacy card
		if (!(issuingPlayer.getCardCount("Diplomacy") >= 1)) {
			System.err.println("Diplomacy card is not available to use");
			return false;
		}

		// Check if the target player name is empty
		if (d_targetPlayer == null || d_targetPlayer.trim().isEmpty()) {
			System.err.println("Target player name cannot be empty");
			return false;
		}

		// Check if the target player exists
		boolean playerExists = p_state.getPlayers().stream()
				.anyMatch(player -> player.getPlayerName().equals(d_targetPlayer.trim()));
		if (!playerExists) {
			System.err.println("Player " + d_targetPlayer + " doesn't exist");
			return false;
		}

		return true;
	}

	/**
	 * Execute execution order if the commands are valid
	 *
	 * @param p_state    is the game state at the current moment
	 * @param p_playerId the id of player gave this order
	 */
	@Override
	public void execute(GameState p_state, int p_playerId) {
		Player l_issuingPlayer = p_state.getPlayers().get(p_playerId);
		Player l_targetPlayer = null;

		// Find the target player in the list of players
		for (Player player : p_state.getPlayers()) {
			if (player.getPlayerName().equals(d_targetPlayer)) {
				l_targetPlayer = player;
				break;
			}
		}

		l_issuingPlayer.addNegotiatedPlayer(l_targetPlayer);
		assert l_targetPlayer != null;
		l_targetPlayer.addNegotiatedPlayer(l_issuingPlayer);
		l_issuingPlayer.decreaseCardCount("Diplomacy");

		System.out.println(l_issuingPlayer.getPlayerName() + " is negotiated with " + d_targetPlayer);
	}

	/**
	 * This class check if a player made a deal with another player then the attack
	 * is not allowed
	 * 
	 * @param p_state    The instance of GameState class
	 * @param p_playerId The index of Player
	 * @return the boolean value true if attack is allowed
	 */
	@Override
	public boolean isValidExecute(GameState p_state, int p_playerId) {

		Player l_issuingPlayer = p_state.getPlayers().get(p_playerId);

		// if the player is inputting null name
		if (d_targetPlayer == null || d_targetPlayer.trim().isEmpty()) {
			System.err.println("Target player name cannot be empty");
			return false;
		}

		// if the opponent player doesn't exist
		boolean l_playerExists = false;

		for (Player p : p_state.getPlayers()) {
			if (p.getPlayerName().equals(d_targetPlayer)) {
				l_playerExists = true;
				break;
			}
		}

		return l_playerExists;

	}

	/**
	 * method to make the game state constant for diplomacy order.
	 *
	 * @param p_state    The current state of the game.
	 * @param p_playerId The ID of the player executing the order.
	 */
	@Override
	public void changeGameState(GameState p_state, int p_playerId) {
		return;
	}

	/**
	 * to String method
	 */
	@Override
	public String toString() {
		return null;
	}
}
