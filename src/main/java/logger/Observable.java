package logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an observable object in the Observer pattern.
 */
public class Observable {
	private List<Observer> d_observers = new ArrayList<Observer>();

	/**
     * Attaches an observer to this observable object.
     *
     * @param p_observer The observer to attach.
     */
	public void attach(Observer p_observer) {
		d_observers.add(p_observer);
	}

	/**
     * Detaches an observer from this observable object.
     *
     * @param p_observer The observer to detach.
     */
	public void detach(Observer p_observer) {
		if (!d_observers.isEmpty()) {
			d_observers.remove(p_observer);
		}
	}

	/**
     * Notifies all attached observers that this observable object has been updated.
     */
	public void notifyObservers() {
		for (Observer l_observer : d_observers) {
			l_observer.update(this);
		}
	}
}
