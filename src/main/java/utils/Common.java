package utils;

import java.io.File;

import constants.GameConstants;

public class Common {
	
	
	/**
	 * 
	 * @param p_mapName
	 * @return path of the map
	 */
	public static String getMapPath(String p_mapName) {
		return GameConstants.SRC_MAIN_RESOURCES + File.pathSeparator + p_mapName;
	}

}
