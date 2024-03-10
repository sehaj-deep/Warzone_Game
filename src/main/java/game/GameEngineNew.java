package game;

import java.util.HashMap;
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
	 * The name of the map.
	 */
	protected String d_mapName;

	public void setPhase(Phase p_phase) {
		d_gamePhase = p_phase;
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
