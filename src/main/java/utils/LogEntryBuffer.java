package utils;

/**
 * A buffer for log entries with observable functionality.
 */
public class LogEntryBuffer extends Observable {
	private StringBuilder d_buffer;
	private String d_currentPhase;
	private String d_effectOfAction;

	/**
     * Constructs a new LogEntryBuffer.
     */
	public LogEntryBuffer() {
		d_buffer = new StringBuilder();
		d_currentPhase = "";
		d_effectOfAction = "";
	}

	
    /**
     * Gets the effect of the action.
     * 
     * @return the effect of the action
     */
	public String getD_effectOfAction() {
		return d_effectOfAction;
	}

	
    /**
     * Gets the current phase.
     * 
     * @return the current phase
     */
	public String getD_currentPhase() {
		return d_currentPhase;
	}

	/**
     * Gets the buffer.
     * 
     * @return the buffer
     */
	public StringBuilder getD_buffer() {
		return d_buffer;
	}

	 /**
     * Sets the effect of the action.
     * 
     * @param p_effectOfAction the effect of the action to set
     */
	public void setD_effectOfAction(String p_effectOfAction) {
		d_effectOfAction = p_effectOfAction;

		d_buffer.append("Action: " + d_effectOfAction);

		notifyObservers();
		clearBuffer();
	}

	/**
     * Sets the current phase.
     * 
     * @param p_currentPhase the current phase to set
     */
	public void setD_currentPhase(String p_currentPhase) {
		d_currentPhase = p_currentPhase;

		d_buffer.append("Phase: " + d_currentPhase);
		d_buffer.append("\n");
		d_buffer.append("-------------------------");

		notifyObservers();
		clearBuffer();
	}

	/**
     * Clears the buffer.
     */
	private void clearBuffer() {
		d_buffer.setLength(0);
	}
}