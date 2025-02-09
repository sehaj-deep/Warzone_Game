package game;

import java.io.Serializable;

/**
 * The main driver class for the warzone game.
 */
public class GameDriver implements Serializable {

	/**
	 * The main method that starts the warzone game.
	 * 
	 * @param p_args The command line arguments (not used).
	 */
	public static void main(String[] p_args) {
		// Create a new game engine instance
		GameEngine l_gameEngine = new GameEngine();
		// Start the game
		l_gameEngine.start();
	}
}
