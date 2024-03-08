package utils;

import java.util.ArrayList;
import java.util.List;

public class LogEntryBuffer implements ObservableLogs {

	private List<Observer> d_observers = new ArrayList<>();

	// TODO: Add a state to change whenever we write some log

	@Override
	public void addObserver(Observer p_observer) {
		d_observers.add(p_observer);
	}

	@Override
	public void removeObserver(Observer p_observer) {
		d_observers.remove(p_observer);
	}

	@Override
	public void notifyAllObservers(String p_message) {
		for (Observer l_observer : d_observers) {
			l_observer.update(p_message);
		}
	}

	public void log(String p_message) {
		notifyAllObservers(p_message);
	}

}
