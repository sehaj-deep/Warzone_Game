package map;

public class MapReaderAdapter implements DominationReader {
	private ConquestMapReader d_conquestReader;

	public MapReaderAdapter(ConquestMapReader p_mapReader) {
		this.d_conquestReader = p_mapReader;
	}

	@Override
	public void readDominationMap(String p_fileName, boolean p_createNew) {
		d_conquestReader.readConquestMap(p_fileName, p_createNew);
	}
}
