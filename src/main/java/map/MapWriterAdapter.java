package map;

public class MapWriterAdapter implements DominationWriter {

	private ConquestMapWriter d_conquestWriter;

	public MapWriterAdapter(ConquestMapWriter p_mapWriter) {
		this.d_conquestWriter = p_mapWriter;
	}

	@Override
	public void writeDominationFile(String p_mapName) {
		d_conquestWriter.writeConquestFile(p_mapName);
	}

}
