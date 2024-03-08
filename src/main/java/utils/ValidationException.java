package utils;

/**
 * User defined Exception to handle exceptions of the game
 */
public class ValidationException extends Exception {

	/**
	 * Default constructor
	 */
	public ValidationException() {
		super("Validation Exception occured");
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param message The message to display
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * Method to display the message
	 */
	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
