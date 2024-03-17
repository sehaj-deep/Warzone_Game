package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.GameEngine;
import game.GameState;
import game.Player;

public class BombTest {

	private Bomb d_bombOrder;
	private GameEngine d_gameEngine;
	private GameState d_gameState;
	private int d_playerId1;
	private int d_playerId2;

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
//		List<Player> l_players = new ArrayList<>();
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
		d_gameState = new GameState(l_players);
		// to set gameBoard
		d_gameState.getGameBoard().put("1", 5);
		d_gameState.getGameBoard().put("2", 7);
		d_gameState.getGameBoard().put("3", 8);
		d_gameState.getGameBoard().put("4", 10);
		d_gameState.getGameBoard().put("5", 9);

		d_playerId1 = 0;
		d_playerId2 = 1;
	}

	@Test
	public void isValidIssueWithAdjacentTargetCountry() {
		d_bombOrder = new Bomb("4", d_gameEngine);
		assertTrue(d_bombOrder.isValidIssue(d_gameState, d_playerId1));
	}

	@Test
	public void isValidIssueWithNonAdjacentTargetCountry() {
		d_bombOrder = new Bomb("5", d_gameEngine);
		assertFalse(d_bombOrder.isValidIssue(d_gameState, d_playerId1));
	}

	@Test
	public void isValidExecuteWithNoCard() {
		d_bombOrder = new Bomb("4", d_gameEngine);
		assertFalse(d_bombOrder.isValidExecute(d_gameState, d_playerId1));
	}

	@Test
	public void isValidExecuteWithCard() {
		d_bombOrder = new Bomb("4", d_gameEngine);
		d_gameState.getPlayers().get(d_playerId1).increaseCardCount("Bomb");
		assertTrue(d_bombOrder.isValidExecute(d_gameState, d_playerId1));
	}

	@Test
	public void executeTestEven() {
		d_bombOrder = new Bomb("4", d_gameEngine);
		d_gameState.getPlayers().get(d_playerId1).increaseCardCount("Bomb");
		d_bombOrder.execute(d_gameState, d_playerId1);
		int numArmies = d_gameState.getGameBoard().get("4");
		assertEquals(5, numArmies);
	}

	@Test
	public void executeTestOdd() {
		d_bombOrder = new Bomb("5", d_gameEngine);
		d_gameState.getPlayers().get(d_playerId1).increaseCardCount("Bomb");
		d_bombOrder.execute(d_gameState, d_playerId1);
		int numArmies = d_gameState.getGameBoard().get("5");
		assertEquals(4, numArmies);
	}

}
