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

	/**
	 * Save id to track different versions of save game
	 */
	public static int d_saveId = 0;

	/**
	 * Name of the file to store player objects
	 */
	public static final String PLAYERS_FILE = SRC_MAIN_RESOURCES + "players" + d_saveId + ".ser";

	/**
	 * Name of the file to store continents objects
	 */
	public static final String CONTINENTS_FILE = SRC_MAIN_RESOURCES + "continents" + d_saveId + ".ser";

	/**
	 * Name of the file to store countries objects
	 */
	public static final String COUNTRIES_FILE = SRC_MAIN_RESOURCES + "countries" + d_saveId + ".ser";

	/**
	 * Name of the file to store issue orders
	 */
	public static final String ISSUE_ORDERS_FILE = SRC_MAIN_RESOURCES + "issueOrders" + d_saveId + ".ser";

}
