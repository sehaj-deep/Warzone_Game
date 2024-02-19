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
	 * 
	 * @param d_id   the id of the country
	 * @param d_name the name of the country
	 */
	public Country(int d_id, String d_name) {
		this.d_id = d_id;
		this.d_name = d_name;
	}

	/**
	 * return the list of all neighbors corresponding
	 */
	public HashSet<Country> getNeighbors() {
		return d_neighbors;
	}

	/**
	 * Adds a neighboring country to the set of neighbors.
	 *
	 * @param name The neighboring country to add.
	 */
	public void addNeighbors(Country name) {
		if (d_neighbors.contains(name)) {
			throw new IllegalArgumentException("Neighbor already exists.");
		}

		d_neighbors.add(name);
	}

}
