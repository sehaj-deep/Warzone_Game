package utils;

/**
 * User defined Exception to handle exceptions of the game
 */
public class ValidationException extends Exception {

	/**
	 * Default constructor
	 */
	public ValidationException() {
		super("Validation Exception occurred");
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param p_message The message to display
	 */
	public ValidationException(String p_message) {
		super(p_message);
	}

}
