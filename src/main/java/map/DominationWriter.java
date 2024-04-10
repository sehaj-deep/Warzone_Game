package map;

/**
 * The DominationWriter interface represents a writer for Domination-style maps.
 */
public interface DominationWriter {

    /**
     * Writes a Domination-style map to a file with the specified name.
     *
     * @param p_mapName The name of the file to write the map to.
     */
	public void writeDominationFile(String p_mapName);

}
