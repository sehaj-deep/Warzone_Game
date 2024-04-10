package map;

/**
 * The MapWriterAdapter class adapts a ConquestMapWriter to implement the DominationWriter interface.
 */
public class MapWriterAdapter implements DominationWriter {

    /** 
     * The adapted ConquestMapWriter. 
	 */
	private ConquestMapWriter d_conquestWriter;

    /**
     * Constructs a new MapWriterAdapter with the specified ConquestMapWriter.
     *
     * @param p_mapWriter The ConquestMapWriter instance to be adapted.
     */
	public MapWriterAdapter(ConquestMapWriter p_mapWriter) {
		this.d_conquestWriter = p_mapWriter;
	}

    /**
     * Writes a Domination-style map using the adapted ConquestMapWriter.
     *
     * @param p_mapName The name of the map file to write.
     */
	@Override
	public void writeDominationFile(String p_mapName) {
		d_conquestWriter.writeConquestFile(p_mapName);
	}

}
