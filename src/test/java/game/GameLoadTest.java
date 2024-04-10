package game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class GameLoadTest {

	private GameEngine gameEngine;
	private GameLoad gameLoad;

	@Before
	public void setUp() {
		gameEngine = new GameEngine();
		gameLoad = new GameLoad(gameEngine);
	}

	@Test
	public void testLoadGame() {
		gameLoad.loadGame("test_game.txt");

		// Assert that players list, countries, and continents in game engine are not
		// null
		assertNotNull(gameEngine.getPlayers());
		assertNotNull(gameEngine.getD_countries());
		assertNotNull(gameEngine.getD_continents());
	}
}
