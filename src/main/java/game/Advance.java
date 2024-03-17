package game;

import java.util.List;
import java.util.Random;
import models.Country;
import utils.ValidationException;

/**
 * Concrete command class for Advance order which inherits Abstract concrete
 * class Order
 * 
 */
public class Advance extends Order {
	private int d_numArmy;
	private String d_sourceCountry;

	/**
	 * Parameterized constructor of Advance Order
	 * 
	 * @param p_numArmy       a number of armies for advancement
	 * @param p_sourceCountry the country where armies will be moving from
	 * @param p_targetCountry the country where armies will be placed
	 * @param p_gameEngineNew the game engine of the game
	 */
	public Advance(int p_numArmy, String p_sourceCountry, String p_targetCountry, GameEngine p_gameEngine) {
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
	 * @param p_state    is the game state at the current moment
	 * @param p_playerId the id of player gave this advance order
	 */
	@Override
	public void execute(GameState p_state, int p_playerId) {
		int l_currSourceNumArmy = p_state.getGameBoard().get(d_sourceCountry);
		// decrease the source army
		p_state.getGameBoard().put(d_sourceCountry, l_currSourceNumArmy - d_numArmy);
		if (!getIsAttack()) { // not an attacking advance
			int l_currTargetNumArmy = p_state.getGameBoard().get(d_targetCountry);
			// increase the target army
			p_state.getGameBoard().put(d_targetCountry, l_currTargetNumArmy + d_numArmy);
		}
		else { // attacking advance
			attack(p_state, p_playerId);
		}
	}

	/**
	 * Execute attacking move of advance order
	 * 
	 * 
	 * @param p_state    the game state at the current moment
	 * @param p_playerId the id of player gave this advance order
	 */
	public void attack(GameState p_state, int p_playerId) {
		// find the owner of the target country
		List<Player> l_players = d_gameEngine.getD_players();
		int l_prevOwnerId;
		for (l_prevOwnerId = 0; l_prevOwnerId < l_players.size(); l_prevOwnerId++) {
			if (l_players.get(l_prevOwnerId).getOwnership().contains(d_targetCountry)) {
				// found the owner
				break;
			}
		}
		// get the number of defending armies in the target country
		int l_numArmyDefence = p_state.getGameBoard().get(d_targetCountry);
		System.out.println("Country being attacked has " + l_numArmyDefence + " of armies");
		int l_numArmyAttack = d_numArmy;
		System.out.println("Attacker has " + l_numArmyAttack + " of armies advancing");
		int l_killDefence, l_killAttack; // chance of killing defending and attacking army
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
					continue;
				}
			}
		}
		if (l_numArmyDefence == 0) { // conquer successful
			// add the target country to owner countries of the player who gave the advance
			d_gameEngine.getD_players().get(p_playerId).conquerCountry(d_targetCountry);
			// remove the target country from the previous owner
			d_gameEngine.getD_players().get(l_prevOwnerId).removeCountry(d_targetCountry);
			p_state.getGameBoard().put(d_targetCountry, l_numArmyAttack);
		}
		else { // no conquer. previous owner still has the target country
			p_state.getGameBoard().put(d_targetCountry, l_numArmyDefence);
		}
	}

	/**
	 * Validate whether the given advance order is legal valid if source country
	 * owned by the player and number of armies < number of armies in the source
	 * country
	 * 
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
			if (!d_gameEngine.getD_players().get(p_playerId).getOwnership().contains(d_sourceCountry)) {
				// check if source country in the player's ownership
				l_errMessage = "Country where army is from is not owned by the player issued the order";
				throw new ValidationException(l_errMessage);
			}
			if (d_numArmy >= p_state.getGameBoard().get(d_sourceCountry)) {
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
	 * @param p_state    is the game state for the requesting player at the current
	 *                   moment
	 * @param p_playerId the id of player gave this deploy order
	 * @return true if the order is valid. false if not a valid move
	 */
	@Override
	public boolean isValidExecute(GameState p_state, int p_playerId) {
		// TODO Auto-generated method stub
		if (!isValidIssue(p_state, p_playerId)) {
			return false;
		}
		if (!d_gameEngine.getD_players().get(p_playerId).getOwnership().contains(d_targetCountry)) {
			// checking whether this advance is for attacking here
			// because the state of the game can change before execution
			setIsAttack(true);
		}
		return true;
	}

	@Override
	public void changeGameState(GameState p_state, int p_playerId) {
		// not utilized
	}

}