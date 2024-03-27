package logger;

/**
 * Interface for Observer pattern.
 */
public interface Observer {
	
	/**
     * Method called by Observable objects when they are updated.
     *
     * @param p_observable The Observable object that is being updated.
     */
	public void update(Observable p_observable);
}