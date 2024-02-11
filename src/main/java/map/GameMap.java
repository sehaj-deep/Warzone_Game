package map;

import java.util.HashMap;

import models.Continent;
import models.Country;

public class GameMap {
	
	/**
     * A hashmap to store the continents
     */
    private HashMap<String, Continent> d_continents = new HashMap<>();
    
    /**
     * A hashmap to store the countries
     */
    private HashMap<String, Country> d_countries = new HashMap<>();
    
    /**
     * To store the name
     */
    private String d_mapName;
    
    /**
     * Default constructor of the GameMap class
     */
    public GameMap() {
    	
    }

    /**
     * 
     * @return the list of continents
     */
	public HashMap<String, Continent> getD_Continents() {
		return d_continents;
	}
	
	/**
	 * 
	 * @param p_continentID Unique ID of the continent
	 * @return the required continent mapped to continent ID
	 */
	public Continent getContinent(String p_continentID) {
		return d_continents.get(p_continentID);
	}

	/**
	 * 
	 * @return return the name of the map
	 */
	public String getD_mapName() {
		return d_mapName;
	}

	/**
	 * set the name of the map
	 * @param d_mapName
	 */
	public void setD_mapName(String d_mapName) {
		this.d_mapName = d_mapName;
	}
	
	
	
	
	
	
	

    
}
