package orders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.GameEngine;
import game.GameState;
import map.Country;
import orders.Airlift;
import players.Player;

/**
 * The AirliftTest class contains unit tests for the Airlift order in the game.
 */
public class AirliftTest {

	/**
	 * The instance of Airlift order being tested.
	 */
	private Airlift d_AirliftOrder;

	/**
	 * The instance of GameEngine used for testing.
	 */
	private GameEngine d_gameEngine;

	/**
	 * The instance of GameState used for testing.
	 */
	private GameState d_gameState;

	/**
	 * The ID of the first player used for testing.
	 */
	private int d_playerId1;

	/**
	 * The ID of the second player used for testing.
	 */
	private int d_playerId2;

	/**
     * Sets up the test environment before each test method execution.
     */
	@Before
	public void setup() {
		d_gameEngine = new GameEngine();
		// add countries to the list
		d_gameEngine.getD_countries().put("1", new Country(1, "1"));
		d_gameEngine.getD_countries().put("2", new Country(2, "2"));
		d_gameEngine.getD_countries().put("3", new Country(3, "3"));
		d_gameEngine.getD_countries().put("4", new Country(4, "4"));
		d_gameEngine.getD_countries().put("5", new Country(5, "5"));

		// add neighbors of the countries
		d_gameEngine.getD_countries().get("1").getNeighbors().add(d_gameEngine.getD_countries().get("2"));
		d_gameEngine.getD_countries().get("1").getNeighbors().add(d_gameEngine.getD_countries().get("3"));

		d_gameEngine.getD_countries().get("2").getNeighbors().add(d_gameEngine.getD_countries().get("3"));
		d_gameEngine.getD_countries().get("2").getNeighbors().add(d_gameEngine.getD_countries().get("1"));

		d_gameEngine.getD_countries().get("3").getNeighbors().add(d_gameEngine.getD_countries().get("1"));
		d_gameEngine.getD_countries().get("3").getNeighbors().add(d_gameEngine.getD_countries().get("4"));

		d_gameEngine.getD_countries().get("4").getNeighbors().add(d_gameEngine.getD_countries().get("5"));
		d_gameEngine.getD_countries().get("5").getNeighbors().add(d_gameEngine.getD_countries().get("1"));

		// add players to the game
		List<Player> l_players = new ArrayList<>();
		l_players.add(new Player("0"));
		l_players.add(new Player("1"));

		// assign countries to players
		l_players.get(0).getOwnership().add("1");
		l_players.get(0).getOwnership().add("3");

		l_players.get(1).getOwnership().add("2");
		l_players.get(1).getOwnership().add("4");
		l_players.get(1).getOwnership().add("5");

		// call Bomb Order
		d_gameState = new GameState();
		d_gameState.setPlayers(l_players);

		// to set gameBoard
		d_gameState.getGameBoard().put("1", 0);
		d_gameState.getGameBoard().put("2", 7);
		d_gameState.getGameBoard().put("3", 8);
		d_gameState.getGameBoard().put("4", 10);
		d_gameState.getGameBoard().put("5", 9);

		d_playerId1 = 0;
		d_playerId2 = 1;
	}

	/**
     * Test to check the validity of issuing the Airlift order when there are no armies available.
     */
	@Test
	public void isValidIssueWithNoArmies() {
		d_AirliftOrder = new Airlift("1", "3", 7, d_gameEngine);
		assertFalse(d_AirliftOrder.isValidIssue(d_gameState, d_playerId1));
	}

    /**
     * Test to check the validity of issuing the Airlift order with armies in adjacent countries.
     */
	@Test
	public void isValidIssueWithArmiesAdjacent() {
		d_AirliftOrder = new Airlift("4", "5", 10, d_gameEngine);
		assertTrue(d_AirliftOrder.isValidIssue(d_gameState, d_playerId2));
	}

	/**
     * Test to check the validity of issuing the Airlift order with armies in non-adjacent countries.
     */
	@Test
	public void isValidIssueWithArmiesNonAdjacent() {
		d_AirliftOrder = new Airlift("2", "5", 7, d_gameEngine);
		assertTrue(d_AirliftOrder.isValidIssue(d_gameState, d_playerId2));
	}

	/**
     * Test to check the validity of issuing the Airlift order when countries belong to different players.
     */
	@Test
	public void isValidIssueWithArmiesDifferentPlayerCountries() {
		d_AirliftOrder = new Airlift("2", "3", 7, d_gameEngine);
		assertFalse(d_AirliftOrder.isValidIssue(d_gameState, d_playerId2));
	}

	/**
     * Test to check the validity of issuing the Airlift order with more armies than available.
     */
	@Test
	public void isValidIssueWithMoreArmies() {
		d_AirliftOrder = new Airlift("2", "4", 8, d_gameEngine);
		assertFalse(d_AirliftOrder.isValidIssue(d_gameState, d_playerId2));
	}

	/**
     * Test to check the validity of executing the Airlift order with the Airlift card.
     */
	@Test
	public void isValidExecuteWithCard() {
		d_AirliftOrder = new Airlift("2", "4", 7, d_gameEngine);
		d_gameState.getPlayers().get(d_playerId2).increaseCardCount("Airlift");
		assertTrue(d_AirliftOrder.isValidExecute(d_gameState, d_playerId2));
	}

	/**
     * Test to check the validity of executing the Airlift order without the Airlift card.
     */
	@Test
	public void isValidExecuteWithoutCard() {
		d_AirliftOrder = new Airlift("2", "4", 7, d_gameEngine);
		assertFalse(d_AirliftOrder.isValidExecute(d_gameState, d_playerId2));
	}

	/**
     * Test to execute the Airlift order.
     */
	@Test
	public void executeTest() {
		d_AirliftOrder = new Airlift("2", "4", 7, d_gameEngine);
		d_AirliftOrder.execute(d_gameState, d_playerId2);
		int valueSource = d_gameState.getGameBoard().get("2");
		int valueTarget = d_gameState.getGameBoard().get("4");
		assertEquals(0, valueSource);
		assertEquals(17, valueTarget);
	}

}
