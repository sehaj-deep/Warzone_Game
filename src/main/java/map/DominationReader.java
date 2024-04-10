package map;

/**
 * The DominationReader interface represents a reader for Domination-style maps.
 */
public interface DominationReader {

    /**
     * Reads a Domination-style map from a file with the specified name.
     *
     * @param p_fileName  The name of the file containing the map.
     * @param p_createNew Indicates whether to create a new file if it does not exist.
     */
	public void readDominationMap(String p_fileName, boolean p_createNew);

}
