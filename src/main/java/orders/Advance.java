package orders;

import java.util.List;
import java.util.Random;
import game.GameEngine;
import map.Country;
import players.Player;
import utils.ValidationException;

/**
 * Concrete command class for Advance order which inherits Abstract concrete
 * class Order
 */
public class Advance extends Order {

	/**
	 * The number of armies for advancement.
	 */
	private int d_numArmy;

	/**
	 * The country where armies will be moving from.
	 */
	private String d_sourceCountry;

	/**
	 * Parameterized constructor of Advance Order
	 * 
	 * @param p_numArmy       a number of armies for advancement
	 * @param p_sourceCountry the country where armies will be moving from
	 * @param p_targetCountry the country where armies will be placed
	 * @param p_gameEngine    the game engine of the game
	 */
	public Advance(String p_sourceCountry, String p_targetCountry, int p_numArmy, GameEngine p_gameEngine) {
		super(p_gameEngine, "Advance");
		d_targetCountry = p_targetCountry;
		d_sourceCountry = p_sourceCountry;
		d_numArmy = p_numArmy;
	}

	/**
	 * access d_sourceCountry data variable
	 * 
	 * @return source country name
	 */
	public String getSourceCountry() {
		return d_sourceCountry;
	}

	/**
	 * access d_numArmy data variable
	 * 
	 * @return number of armies to be advanced
	 */
	public int getNumArmy() {
		return d_numArmy;
	}

	/**
	 * Execute this advance order Move some armies from source country to the
	 * destination country Or attack the destination country with the given army in
	 * the order
	 *
	 * @param p_playerId the id of player gave this advance order
	 */
	@Override
	public void execute(int p_playerId) {
		int l_currSourceNumArmy = d_gameEngine.getGameBoard().get(d_sourceCountry);
		// decrease the source army
		d_gameEngine.getGameBoard().put(d_sourceCountry, l_currSourceNumArmy - d_numArmy);
		if (!getIsAttack()) { // not an attacking advance
			int l_currTargetNumArmy = d_gameEngine.getGameBoard().get(d_targetCountry);
			// increase the target army
			d_gameEngine.getGameBoard().put(d_targetCountry, l_currTargetNumArmy + d_numArmy);
		}
		else { // attacking advance
			attack(p_playerId);
		}
	}

	/**
	 * Execute attacking move of advance order
	 * 
	 * 
	 * @param p_playerId the id of player gave this advance order
	 */
	public void attack(int p_playerId) {
		// find the owner of the target country
		List<Player> l_players = d_gameEngine.getPlayers();
		int l_prevOwnerId;
		boolean isNeutralCountry = true;
		for (l_prevOwnerId = 0; l_prevOwnerId < l_players.size(); l_prevOwnerId++) {
			if (l_players.get(l_prevOwnerId).getOwnership().contains(d_targetCountry)) {
				// found the owner
				isNeutralCountry = false;
				break;
			}
		}
		// get the number of defending armies in the target country
		int l_numArmyDefence = d_gameEngine.getGameBoard().get(d_targetCountry);
		int l_numArmyAttack = d_numArmy;
		int l_killDefence; // chance of killing defending.
		int l_killAttack; // chance of killing and attacking army
		Random random = new Random();
		while (l_numArmyDefence > 0 && l_numArmyAttack > 0) {
			l_killDefence = random.nextInt(10 - 1 + 1) + 1;
			l_killAttack = random.nextInt(10 - 1 + 1) + 1;
			// let attacking kill defending first
			if (l_killDefence <= 6) { // an attacking army has 60% killing a defending
				l_numArmyDefence = l_numArmyDefence - 1;
				if (l_numArmyDefence == 0) {
					continue;
				}
			}
			if (l_killAttack <= 7) { // an defending army has 70% killing a attacking
				l_numArmyAttack = l_numArmyAttack - 1;
				if (l_numArmyAttack == 0) {
					break;
				}
			}
		}
		if (l_numArmyDefence == 0) { // conquer successful
			// add the target country to owner countries of the player who gave the advance
			d_gameEngine.getPlayers().get(p_playerId).conquerCountry(d_targetCountry);
			if (!isNeutralCountry) {
				// remove the target country from the previous owner as it is not neutral by
				// blockade
				d_gameEngine.getPlayers().get(l_prevOwnerId).removeCountry(d_targetCountry);
			}
			d_gameEngine.getGameBoard().put(d_targetCountry, l_numArmyAttack);

			System.out.println("Advance executed: " + d_targetCountry + " has been conquered.");
		}
		else { // no conquer. previous owner still has the target country
			d_gameEngine.getGameBoard().put(d_targetCountry, l_numArmyDefence);
			System.out.println("Advance executed: " + d_targetCountry + " defended successfully.");
		}
	}

	/**
	 * Validate whether the given advance order is legal valid if source country
	 * owned by the player and number of armies less than number of armies in the
	 * source country
	 * 
	 * 
	 * @param p_playerId the id of player gave this deploy order
	 * @return true if the order is valid. false if not a valid move
	 */
	@Override
	public boolean isValidIssue(int p_playerId) {
		String l_errMessage = "";
		try {
			if (!d_gameEngine.getPlayers().get(p_playerId).getOwnership().contains(d_sourceCountry)) {
				// check if source country in the player's ownership
				l_errMessage = "Country where army is from is not owned by the player issued the order";
				throw new ValidationException(l_errMessage);
			}
			if (d_numArmy >= d_gameEngine.getGameBoard().get(d_sourceCountry)) {
				l_errMessage = "Number of armies to advance must be less than " + "the number of armies of the source country";
				throw new ValidationException(l_errMessage);
			}
			if (d_numArmy < 0) {
				l_errMessage = "Number of armies can't be negative";
				throw new ValidationException(l_errMessage);
			}
			Country l_target = d_gameEngine.getD_countries(d_targetCountry);
			if (!d_gameEngine.getD_countries(d_sourceCountry).getNeighbors().contains(l_target)) {
				l_errMessage = "There is no link from source country to destination country ";
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
	 * Validate whether the given advance order is legal valid during order
	 * execution time validation conditions same as isValidIssue However, this
	 * function also determines whether this advance order consists attacking move
	 * 
	 * 
	 * @param p_playerId the id of player gave this deploy order
	 * @return true if the order is valid. false if not a valid move
	 */
	@Override
	public boolean isValidExecute(int p_playerId) {
		if (!isValidIssue(p_playerId)) {
			return false;
		}
		if (!d_gameEngine.getPlayers().get(p_playerId).getOwnership().contains(d_targetCountry)) {
			// checking whether this advance is for attacking here
			// because the state of the game can change before execution
			setIsAttack(true);
		}
		return true;
	}
}