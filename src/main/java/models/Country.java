package models;

import java.util.HashSet;

/**
 * Country class represents a country in the game map.
 */
public class Country {

	/**
	 * store id of the country
	 */
	private int d_id;

	/**
	 * store name of the country
	 */
	private String d_name;

	/**
	 * store name of neighbors
	 */
	private HashSet<Country> d_neighbors = new HashSet<>();

	/**
	 * 
	 * @return the id of the country
	 */
	public int getD_id() {
		return d_id;
	}

	/**
	 * 
	 * @return the name of the country
	 */
	public String getD_name() {
		return d_name;
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param p_id   the id of the country
	 * @param p_name the name of the country
	 */
	public Country(int p_id, String p_name) {
		this.d_id = p_id;
		this.d_name = p_name;
	}

	/**
	 * @return the list of all neighbors corresponding
	 */
	public HashSet<Country> getNeighbors() {
		return d_neighbors;
	}

	/**
	 * Adds a neighboring country to the set of neighbors.
	 *
	 * @param p_name The neighboring country to add.
	 */
	public void addNeighbors(Country p_name) {
		if (d_neighbors.contains(p_name)) {
			throw new IllegalArgumentException("Neighbor already exists.");
		}

		d_neighbors.add(p_name);
	}

}
