//package game;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import map.MapEditor;
//import models.Continent;
//import models.Country;
//
///**
// * Reinforcements class represents the reinforcement phase in the game.
// */
//public class Reinforcements {
//
//	private int d_reinforcementArmies;
//	private Map<String, Country> d_countriesOwned; // A HashMap to store countries owned by the player
//	private static MapEditor d_mapEditor; // A reference to the game map editor
//	Player d_player; // player Instance
//
//	/**
//	 * Constructor for Reinforcements class.
//	 * @param p_MapEditor The game map object.
//	 * @param p_player The player instance.
//	 */
//	public Reinforcements(MapEditor p_MapEditor, Player p_player) {
//		d_mapEditor = p_MapEditor;
//		this.d_player = p_player;
//		this.d_countriesOwned = new HashMap<>(); // Initialize the countries owned map
//	}
//
//	/**
//	 * Method to calculate reinforcement armies for the player.
//	 */
//	public void calculateReinforcementArmies() {
//		d_reinforcementArmies = d_player.getOwnership().size() / 3; // Basic calculation based on territories owned
//
//		// Add bonus armies from continents owned
//		for (HashMap.Entry<String, Continent> l_continent : d_mapEditor.getD_continents().entrySet()) {
//
//			Set<Country> l_countries = l_continent.getValue().getD_countries();
//			boolean l_OwnsAllCountries = false;
//			for (Country country : l_countries) {
//				if (!d_player.getOwnership().contains(country.getD_name())) {
//					l_OwnsAllCountries = true;
//					break;
//				}
//			}
//			if (l_OwnsAllCountries) {
//				// Get the bonus armies for the continent from the map editor
//				int bonusArmies = l_continent.getValue().getD_continentBonusArmies();
//				d_reinforcementArmies += bonusArmies;
//			}
//		}
//
//		// Ensure minimum reinforcement armies
//		if (d_reinforcementArmies < 3) {
//			d_reinforcementArmies = 3;
//		}
//	}
//
//	/**
//	 * Method to get reinforcement armies for the player.
//	 * 
//	 * @return The number of reinforcement armies for the player.
//	 */
//	public int getReinforcementArmies() {
//		return d_reinforcementArmies;
//	}
//}
