package game;

/**
 * The main driver class for the warzone game.
 */
public class GameDriver {

	/**
     * The main method that starts the warzone game.
     * 
     * @param args The command line arguments (not used).
     */
	public static void main(String[] args) {
		// Create a new game engine instance
		GameEngine l_gameEngine = new GameEngine();
		// Start the game
		l_gameEngine.start();
	}
}
