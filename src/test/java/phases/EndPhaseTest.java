package phases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import game.Advance;
import game.GameEngine;
import game.GameState;
import game.Player;
import models.Country;

/**
 * Test the key functionalities of End phase
 */
public class EndPhaseTest {
	/**
	 * List of players
	 */
	static List<Player> d_players;
	/**
	 * Player id
	 */
	static int d_plyrId = 1;
	/**
	 * Opponent id
	 */
	static int d_opponentId = 1;
	/**
	 * GameState instance
	 */
	static GameState d_state;
	/**
	 * GameEngine instance
	 */
	GameEngine d_gameEngine;
	/**
	 * End Phase instance
	 */
	EndPhase d_endPhase;

	/**
	 * To initialize the EndPhaseTest
	 */
	@Before
	public void before() {
		// game engine set up
		d_gameEngine = new GameEngine();
		d_players = d_gameEngine.getGameState().getPlayers();
		d_players.add(new Player("0"));
		d_players.add(new Player("1"));
		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("korea"));
		Set<String> l_ownedCountries2 = new HashSet<>(Arrays.asList("usa"));
		d_players.get(0).setOwnership(l_ownedCountries);
		d_players.get(1).setOwnership(l_ownedCountries2);
		Country l_country1, l_country2;
		l_country1 = new Country(0, "korea");
		l_country2 = new Country(1, "usa");
		l_country2.addNeighbors(l_country1);
		HashMap<String, Country> l_countries = d_gameEngine.getD_countries();
		l_countries.put("korea", l_country1);
		l_countries.put("usa", l_country2);

		// game board set up
		d_state = d_gameEngine.getGameState();
		HashMap<String, Integer> l_board = new HashMap<String, Integer>(); // key: country name. val: num
		l_board.put("korea", 3);
		l_board.put("usa", 12);
		d_state.setGameBoard(l_board);
		d_endPhase = new EndPhase(d_gameEngine);
	}

	/**
	 * Test the kickOutPlayer method of End phase
	 */
	@Test
	public void testKickOutPlayer() {
		Advance d_advanceOrder = new Advance("usa", "korea", 9, d_gameEngine);
		d_advanceOrder.isValidExecute(d_state, d_plyrId);
		d_advanceOrder.execute(d_state, d_plyrId);
		d_endPhase.kickOutPlayer();
		int l_numPlayers = d_gameEngine.getGameState().getPlayers().size();
		assertEquals(1, l_numPlayers);
	}

	/**
	 * Test the end method of End phase
	 */
	@Test
	public void testEnd() {
		Advance d_advanceOrder = new Advance("usa", "korea", 9, d_gameEngine);
		d_advanceOrder.isValidExecute(d_state, d_plyrId);
		d_advanceOrder.execute(d_state, d_plyrId);
		d_endPhase.end();
		assertTrue(d_endPhase.getAnyWinner());
	}

}