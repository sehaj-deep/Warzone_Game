package utils;

import constants.GameConstants;

public class Common {

	/**
	 * Retrieves the path of the map based on its name.
	 *
	 * @param p_mapName The name of the map.
	 * @return The path of the map.
	 */
	public static String getMapPath(String p_mapName) {
		return GameConstants.SRC_MAIN_RESOURCES + p_mapName;
	}

}
