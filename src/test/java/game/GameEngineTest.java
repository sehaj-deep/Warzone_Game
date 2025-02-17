package game;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is designed to do unit test of features in GameState class
 */
public class GameEngineTest {

	/**
	 * The GameState instance used for testing various features of the GameState
	 * class.
	 */
	GameEngine d_gameEngine;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before
	public void before() {
		System.out.println("Test GameState");
		d_gameEngine = new GameEngine();
	}

	/**
	 * This tests the setReinforcements and getReinforcements methods of GameState
	 * case1: test if setter performs correctly case2: test if the data member
	 * updated correctly after access through getter
	 */
	@Test
	public void testSetGetReinforcements() {
		System.out.println("Test setReinforcements and getReinforcements methods");
		// Test setter for reinforcements
		System.out.println("Test setter function for reinforcements in GameState");
		List<Integer> l_reinforcements = new ArrayList<>();
		d_gameEngine.setReinforcements(l_reinforcements);
		List<Integer> l_stateReinforcements = d_gameEngine.getReinforcements();
		assertEquals(0, l_stateReinforcements.size());

		// Test getter for reinforcements
		System.out.println("Test getter function for reinforcements in GameState");
		l_stateReinforcements = d_gameEngine.getReinforcements();
		l_stateReinforcements.add(1);
		assertEquals(1, l_stateReinforcements.size());
	}

	/**
	 * This tests the setGameBoard and getGameBoard methods of GameState case1: test
	 * if setter performs correctly case2: test if the data member updated correctly
	 * after access through getter
	 */
	@Test
	public void testSetGetGameBoard() {
		System.out.println("Test setGameBoard and getGameBoard methods");
		HashMap<String, Integer> l_board = new HashMap<String, Integer>();
		l_board.put("a", 0);
		// Test if setter has performed correctly
		System.out.println("Test setter function for game board in GameState");
		d_gameEngine.setGameBoard(l_board);
		Map<String, Integer> l_stateBoard = d_gameEngine.getGameBoard();
		assertEquals(1, l_stateBoard.size());

		// Test if modifying the data member after accessing with getter function
		// updates the data member correctly
		System.out.println("Test getter function for game board in GameState");
		l_stateBoard = d_gameEngine.getGameBoard();
		l_stateBoard.put("b", 0);
		assertEquals(2, l_stateBoard.size());
	}
}