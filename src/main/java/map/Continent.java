package map;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Continent class represents a continent in the game map.
 */
public class Continent implements Serializable {

	private static final long serialVersionUID = 1L;

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
	 * @param p_continentID          The serial number of continent
	 * @param p_continentName        The name of the continent
	 * @param p_continentBonusArmies Number of bonus armies
	 */
	public Continent(int p_continentID, String p_continentName, int p_continentBonusArmies) {
		this.d_continentID = p_continentID;
		this.d_continentName = p_continentName;
		this.d_continentBonusArmies = p_continentBonusArmies;
	}

	public Continent(String p_continentName, int p_continentBonusArmies) {
		this.d_continentName = p_continentName;
		this.d_continentBonusArmies = p_continentBonusArmies;
	}

	/**
	 * 
	 * @return continent Id The serial number of continent
	 */
	public int getD_continentID() {
		return d_continentID;
	}

	/**
	 * set continent ID
	 * 
	 * @param p_continentID The serial number of continent
	 */
	public void setD_continentID(int p_continentID) {
		this.d_continentID = p_continentID;
	}

	/**
	 * Return a particular Continent Object
	 * 
	 * @return continent Name The name of the continent
	 */
	public String getD_continentName() {
		return d_continentName;
	}

	/**
	 * Set a particular continent
	 * 
	 * @param p_continentName The name of the continent
	 */
	public void setD_continentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}

	/**
	 * 
	 * @return Bonus Armies The number of armies associated with a continent
	 */
	public int getD_continentBonusArmies() {
		return d_continentBonusArmies;
	}

	/**
	 * Set bonus armies for a continent
	 * 
	 * @param p_continentValue The number of Bonus armies
	 */
	public void setD_continentBonusArmies(int p_continentValue) {
		this.d_continentBonusArmies = p_continentValue;
	}

	/**
	 * Get all countries
	 * 
	 * @return the set of countries corresponding to a continent
	 */
	public Set<Country> getD_countries() {
		return d_countries;
	}

	/**
	 * Add a country to the HashMap
	 * 
	 * @param p_country Country in a continent
	 */
	public void addD_countries(Country p_country) {
		if (!this.getD_countries().contains(p_country)) {
			this.d_countries.add(p_country);
		} else {
			System.out.println(this.getD_continentName() + " already contains " + p_country.getD_name());
		}
	}

}
