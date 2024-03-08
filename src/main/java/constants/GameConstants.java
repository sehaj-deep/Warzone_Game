package constants;

/**
 * Constant class
 */
public class GameConstants {

	/**
	 * Private constructor to stop the class from instantiating
	 */
	private GameConstants() {

	}

	/**
	 * To get the path of files in src/main/resources
	 */
	public static final String SRC_MAIN_RESOURCES = System.getProperty("user.dir").replace("\\\\", "/")
			+ "/src/main/resources/";

	/**
	 * To get the path of files in src/test/resources
	 */
	public static final String SRC_TEST_RESOURCES = System.getProperty("user.dir").replace("\\\\", "/")
			+ "/src/test/resources/";

	/**
	 * Name of the file to store logs
	 */
	public static final String LOG_FILE = "logs.txt";
}
