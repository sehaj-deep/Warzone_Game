package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import map.Preload;
import models.Continent;
import models.Country;
import phases.Phase;
import phases.PlaySetup;

public class GameEngineNew {

	protected Phase d_gamePhase;

	/**
	 * A hashmap to store the continents
	 */
	protected HashMap<String, Continent> d_continents = new HashMap<>();

	/**
	 * To map continent id with continent name
	 */
	protected HashMap<Integer, Continent> d_continentId = new HashMap<>();

	/**
	 * A hashmap to store the countries
	 */
	protected HashMap<String, Country> d_countries = new HashMap<>();

	/**
	 * To map countries id with country name
	 */
	protected HashMap<Integer, Country> d_countriesId = new HashMap<>();

	/**
	 * The list of the player name
	 */
	protected List<String> d_playerNameList = new ArrayList<>();

	/**
	 * to get the value of player names
	 *
	 * @return return the value of player name
	 */
	public List<String> getD_playerNameList() {
		return d_playerNameList;
	}

	/**
	 * to assign the name of the players
	 *
	 * @param p_playerNameList to assign the name of the players
	 */
	public void setD_playerNameList(String p_playerNameList, Boolean p_operation) {
		if (p_operation) this.d_playerNameList.add(p_playerNameList);
		else this.d_playerNameList.remove(p_playerNameList);
	}

	/**
	 * The name of the map.
	 */
	protected String d_mapName;

	/**
	 * To change the phase
	 * 
	 * @param p_phase The phase to be set
	 */
	public void setPhase(Phase p_phase) {
		d_gamePhase = p_phase;
	}

	/**
	 * To get the current phase
	 * 
	 * @return The current Phase
	 */
	public Phase getPhase() {
		return d_gamePhase;
	}

	/**
	 * 
	 * @return the list of continents
	 */
	public HashMap<String, Continent> getD_continents() {
		return d_continents;
	}

	/**
	 * 
	 * @param p_continentName Unique name of the continent
	 * @return the required continent mapped to continent ID
	 */
	public Continent getD_continents(String p_continentName) {
		return d_continents.get(p_continentName);
	}

	public Continent removeD_continent(String p_continentName) {
		return d_continents.remove(p_continentName);
	}

	/**
	 * 
	 * @return the list of continents mapped to corresponding ID
	 */
	public HashMap<Integer, Continent> getD_continentId() {
		return d_continentId;
	}

	/**
	 * 
	 * @param p_continentId The Id of the continent
	 * @return return the Object of the continent corresponding to an Id.
	 */
	public Continent getD_continentId(int p_continentId) {
		return d_continentId.get(p_continentId);
	}

	public Continent removeD_continentID(int p_continentId) {
		return d_continentId.remove(p_continentId);
	}

	/**
	 * Retrieves the hashmap containing the countries.
	 *
	 * @return The hashmap containing the countries.
	 */
	public HashMap<String, Country> getD_countries() {
		return d_countries;
	}

	/**
	 * Retrieves the country object with the specified country name.
	 *
	 * @param p_countryName The name of the country to retrieve.
	 * @return The country object corresponding to the given country name.
	 */
	public Country getD_countries(String p_countryName) {
		return d_countries.get(p_countryName);
	}

	/**
	 * 
	 * @return the name of the country corresponding to the id.
	 */
	public HashMap<Integer, Country> getD_countriesId() {
		return d_countriesId;
	}

	/**
	 * 
	 * @return name of the map
	 */
	public String getD_mapName() {
		return d_mapName;
	}

	/**
	 * set the name of the map
	 * 
	 * @param d_mapName Name of the map file
	 */
	public void setD_mapName(String d_mapName) {
		this.d_mapName = d_mapName;
	}

	public void start() {
		try (Scanner l_scanner = new Scanner(System.in)) {
			System.out.println("1. Edit Map");
			System.out.println("2. Start Game");
			System.out.print("Enter your choice: ");
			String choice = l_scanner.nextLine();

			switch (choice) {
			case "1":
				setPhase(new Preload(this));

				break;
			case "2":
				setPhase(new PlaySetup(this));
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}

}
