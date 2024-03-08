package utils;

public interface ObservableLogs {

	public void addObserver(Observer p_observer);

	public void removeObserver(Observer p_observer);

	public void notifyAllObservers(String p_message);

}
