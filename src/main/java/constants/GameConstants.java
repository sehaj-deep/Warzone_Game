package constants;

/**
 * Constant class
 */
public class GameConstants {

	/**
	 * To get the path of files in src/main/resources
	 */
	public static final String SRC_MAIN_RESOURCES = System.getProperty("user.dir").replaceAll("\\\\", "/")
			+ "/src/main/resources/";

	/**
	 * To get the path of files in src/test/resources
	 */
	public static final String SRC_TEST_RESOURCES = System.getProperty("user.dir").replaceAll("\\\\", "/")
			+ "/src/test/resources/";
}
