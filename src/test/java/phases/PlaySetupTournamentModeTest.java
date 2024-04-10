package phases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.GameEngine;

/**
 * This class is designed to do unit test of features in GameState class
 */
public class PlaySetupTournamentModeTest {

	/**
	 * The GameState instance used for testing various features of the GameState
	 * class.
	 */
	GameEngine d_gameEngine;

	/**
	 * Tournament object that simulates tournament games
	 */
	PlaySetupTournamentMode d_tournamentSimulator;

	/**
	 * list of map files given by user
	 */
	private List<String> d_mapFiles;

	/**
	 * list of player strategies given by user
	 */
	private List<String> d_playerStrategies;

	/**
	 * number of games to be played on each map given by user
	 */
	private int d_gamesToBePlayed;

	/**
	 * p_maxNumberOfTurns maximum number of turns per game
	 */
	private int d_maxNumberOfTurns;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before
	public void before() {
		System.out.println("Test GameState");
		d_gameEngine = new GameEngine();
		d_mapFiles = Arrays.asList("canada.map", "solar.map");
		d_playerStrategies = Arrays.asList("aggressive", "benevolent");
		d_gamesToBePlayed = 4;
		d_maxNumberOfTurns = 15;
		d_tournamentSimulator = new PlaySetupTournamentMode(d_gameEngine);
	}

	/**
	 * This tests setupTournament method. Check whether game board has been filled
	 * out with countries in the map and players are added for a game in the
	 * tournament
	 */
	@Test
	public void testSetupTournament() {
		assertEquals(0, d_gameEngine.getGameBoard().size());
		assertEquals(0, d_gameEngine.getPlayers().size());
		d_tournamentSimulator.setupTournament(d_mapFiles.get(0), d_playerStrategies);
		assertEquals(2, d_gameEngine.getPlayers().size());
		assertTrue(d_gameEngine.getPlayers().get(0).getOwnership().size() > 0);
		assertTrue(d_gameEngine.getPlayers().get(1).getOwnership().size() > 0);
		assertTrue(d_gameEngine.getGameBoard().size() > 0);
	}

	/**
	 * This tests startTournament method. Check whether the tournament result array
	 * is filled out. i.e., no null value in each entry
	 */
	@Test
	public void testStartTournament() {
		d_tournamentSimulator.startTournament(d_mapFiles, d_playerStrategies, d_gamesToBePlayed, d_maxNumberOfTurns);
		String[][] l_results = d_tournamentSimulator.getD_results();
		for (int i = 0; i < d_mapFiles.size(); i++) {
			for (int j = 0; j < d_gamesToBePlayed; j++) {
				assertTrue(l_results[i][j] != null);
			}
		}
	}

	/**
	 * This tests reportTournamentResult method. Check whether the result is not an
	 * empty string
	 */
	@Test
	public void testReportTournamentResult() {
		String[][] l_results = new String[3][4];
		for (int i = 0; i < l_results.length; i++) {
			for (int j = 0; j < l_results[i].length; j++) {
				l_results[i][j] = Integer.toString(i + j);
			}
		}
		List<String> l_mapNames = new ArrayList<>();
		l_mapNames.add("A");
		l_mapNames.add("B");
		l_mapNames.add("C");
		String l_resultTable = d_tournamentSimulator.reportTournamentResult(l_results, l_mapNames);
		System.out.println(l_resultTable);
		assertTrue(l_resultTable.length() > 0);
	}
}
