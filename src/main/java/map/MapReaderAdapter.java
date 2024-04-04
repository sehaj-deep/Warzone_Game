package map;

public class MapReaderAdapter implements DominationReader {
//	private DominationMapReader d_dominationReader;
	private ConquestMapReader d_conquestReader;

//	public MapReaderAdapter(DominationMapReader p_mapReader) {
//		this.d_dominationReader = p_mapReader;
//	}

	public MapReaderAdapter(ConquestMapReader p_mapReader) {
		this.d_conquestReader = p_mapReader;
	}

//	@Override
//	public void readConquestMap(String p_fileName, boolean p_createNew) {
//		d_dominationReader.readDominationMap(p_fileName, p_createNew);
//
//	}

	@Override
	public void readDominationMap(String p_fileName, boolean p_createNew) {
//		d_dominationReader.readDominationMap(p_fileName, p_createNew);
		d_conquestReader.readConquestMap(p_fileName, p_createNew);

	}

}
