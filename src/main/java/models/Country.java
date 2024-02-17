package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Country {
	
	/**
	 * store id of the country
	 */
	private int d_id;
	
	/**
	 * store name of the country
	 */
	private String d_name;

	private HashSet<Country> neighbors = new HashSet<>();
	
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
	 * @param d_id the id of the country
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
		return neighbors;
	}
	
	public void addNeighbors(Country name) {
		//TODO: handle the case if already present, throw an exception
		neighbors.add(name);
	}
	
	

}
