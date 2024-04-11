package map;

import java.io.Serializable;

/**
 * The MapReaderAdapter class adapts a ConquestMapReader to implement the
 * DominationReader interface.
 */
public class MapReaderAdapter implements DominationReader, Serializable {

	/**
	 * The adapted ConquestMapReader
	 */
	private ConquestMapReader d_conquestReader;

	/**
	 * Constructs a MapReaderAdapter with the specified ConquestMapReader.
	 *
	 * @param p_mapReader The ConquestMapReader to be adapted.
	 */
	public MapReaderAdapter(ConquestMapReader p_mapReader) {
		this.d_conquestReader = p_mapReader;
	}

	/**
	 * Reads a Domination-style map using the adapted ConquestMapReader.
	 *
	 * @param p_fileName  The name of the map file to read.
	 * @param p_createNew Whether to create a new map if the file doesn't exist.
	 */
	@Override
	public void readDominationMap(String p_fileName, boolean p_createNew) {
		d_conquestReader.readConquestMap(p_fileName, p_createNew);
	}
}
