package orders;

import java.io.Serializable;

import game.GameEngine;
import players.Player;

/**
 * The diplomacy class check if attack is valid or not based on player using
 * diplomacy card.
 */
public class Diplomacy extends Order implements Serializable {

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
	 * @param p_playerId is the id of the player who gave this order
	 * @return the boolean value true if the command is valid else false
	 */
	@Override
	public boolean isValidIssue(int p_playerId) {

		Player issuingPlayer = d_gameEngine.getPlayers().get(p_playerId);

		// Check if the player has the Diplomacy card
		if (issuingPlayer.getCardCount("Diplomacy") < 1) {
			System.err.println("Diplomacy card is not available to use");
			return false;
		}

		// Check if the target player name is empty
		if (d_targetPlayer == null || d_targetPlayer.trim().isEmpty()) {
			System.err.println("Target player name cannot be empty");
			return false;
		}

		// Check if the target player exists
		boolean playerExists = d_gameEngine.getPlayers().stream()
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
	 * @param p_playerId the id of player gave this order
	 */
	@Override
	public void execute(int p_playerId) {
		Player l_issuingPlayer = d_gameEngine.getPlayers().get(p_playerId);
		Player l_targetPlayer = null;

		// Find the target player in the list of players
		for (Player player : d_gameEngine.getPlayers()) {
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
	 * @param p_playerId The index of Player
	 * @return the boolean value true if attack is allowed
	 */
	@Override
	public boolean isValidExecute(int p_playerId) {

		// if the player is inputting null name
		if (d_targetPlayer == null || d_targetPlayer.trim().isEmpty()) {
			System.err.println("Target player name cannot be empty");
			return false;
		}

		// if the opponent player doesn't exist
		boolean l_playerExists = false;

		for (Player p : d_gameEngine.getPlayers()) {
			if (p.getPlayerName().equals(d_targetPlayer)) {
				l_playerExists = true;
				break;
			}
		}

		return l_playerExists;

	}

	/**
	 * to String method
	 */
	@Override
	public String toString() {
		return "Diplomacy order";
	}
}
