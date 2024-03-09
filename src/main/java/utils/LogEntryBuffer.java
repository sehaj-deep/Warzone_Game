package utils;

public class LogEntryBuffer extends Observable {
	private StringBuilder d_buffer;
	private String d_currentPhase;
	private String d_effectOfAction;

	public LogEntryBuffer() {
		d_buffer = new StringBuilder();
		d_currentPhase = "";
		d_effectOfAction = "";
	}

	public String getD_effectOfAction() {
		return d_effectOfAction;
	}

	public String getD_currentPhase() {
		return d_currentPhase;
	}

	public StringBuilder getD_buffer() {
		return d_buffer;
	}

	public void setD_effectOfAction(String p_effectOfAction) {
		d_effectOfAction = p_effectOfAction;

		d_buffer.append("Action: " + d_effectOfAction);

		notifyObservers();
		clearBuffer();
	}

	public void setD_currentPhase(String p_currentPhase) {
		d_currentPhase = p_currentPhase;

		d_buffer.append("Phase: " + d_currentPhase);
		d_buffer.append("\n");
		d_buffer.append("-------------------------");

		notifyObservers();
		clearBuffer();
	}

	private void clearBuffer() {
		d_buffer.setLength(0);
	}
}