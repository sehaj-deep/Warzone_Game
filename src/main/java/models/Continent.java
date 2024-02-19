package models;

import java.util.HashSet;
import java.util.Set;

/**
 * Continent class represents a continent in the game map.
 */
public class Continent {

	/**
	 * continent ID.
	 */
	private int d_continentID;

	/**
	 * continent name.
	 */
	private String d_continentName;

	/**
	 * continent value.
	 */
	private int d_continentBonusArmies;

	/**
	 * All the countries in a continent
	 */
	private Set<Country> d_countries = new HashSet<>();

	/**
	 * constructor of continent class
	 * 
	 * @param p_continentID
	 * @param p_continentName
	 * @param p_continentBonusArmies
	 */
	public Continent(int p_continentID, String p_continentName, int p_continentBonusArmies) {
		this.d_continentID = p_continentID;
		this.d_continentName = p_continentName;
		this.d_continentBonusArmies = p_continentBonusArmies;
	}

	/**
	 * 
	 * @return continent Id
	 */
	public int getD_continentID() {
		return d_continentID;
	}

	/**
	 * set continent ID
	 * 
	 * @param d_continentID
	 */
	public void setD_continentID(int p_continentID) {
		this.d_continentID = p_continentID;
	}

	/**
	 * 
	 * @return continent Name
	 */
	public String getD_continentName() {
		return d_continentName;
	}

	/**
	 * 
	 * @param p_continentName
	 */
	public void setD_continentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}

	/**
	 * 
	 * @return continent Value
	 */
	public int getD_continentBonusArmies() {
		return d_continentBonusArmies;
	}

	/**
	 * 
	 * @param p_continentValue
	 */
	public void setD_continentBonusArmies(int p_continentValue) {
		this.d_continentBonusArmies = p_continentValue;
	}

	/**
	 * 
	 * @return the set of countries corresponding to a continent
	 */
	public Set<Country> getD_countries() {
		return d_countries;
	}

	/**
	 * 
	 * @param country Country in a continent
	 */
	public void addD_countries(Country country) {
		if (!this.getD_countries().contains(country)) {
			this.d_countries.add(country);
		} else {
			System.out.println(this.getD_continentName() + " already contains " + country.getD_name());
		}
	}

}
