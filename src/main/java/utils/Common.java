package utils;

import java.io.Serializable;

import constants.GameConstants;

/**
 * Class to have common functionalities
 */
public class Common implements Serializable {

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
