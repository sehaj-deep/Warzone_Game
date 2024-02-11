package game;

import java.util.*;

/*
 * This is a class representing the state of the game for a player at the moment
 */
public class State {
	public Set<Integer> d_ownership = new HashSet<Integer>();  // set of countries owned by the player
	public int d_available_army;  // number of available armies for the player
	
	/*
	 * Parameterized construction for State class
	 * 
	 * @param p_ownership is a set of countries owned by a player
	 * @param p_availabe_army is the number of army available for a player for a move/order
	 */
	State(Set<Integer> p_ownership, int p_available_army) {
		d_ownership = p_ownership;
		d_available_army = p_available_army; 
	}
}
