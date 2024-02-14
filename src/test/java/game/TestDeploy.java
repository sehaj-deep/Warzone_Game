package game;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;

/*
 * This class is designed to do unit test of features in Deploy class (inherits from Order)
 */
public class TestDeploy {
	// Used one player for players list to test the deploy order class for simplicity
	static Deploy d_deploy_order;
	static List<Player> d_players = new ArrayList<>();
	static List<Integer> d_reinforcements  = new ArrayList<>();
	static int d_plyr_id = 0;
	static GameState d_state;
	
	/*
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before public void before() {
		System.out.println("Testing Deploy class Setup: 4 armies to country 3");
		d_deploy_order = new Deploy(4, 3);
		d_players.add(new Player(d_plyr_id));
		d_state = new GameState(d_players);
	}
	
	/*
	 * test isValid method of Deploy class
	 * test one valid case where expecting output true from isValid
	 * test three invalid cases where expecting output false from isValid
	 */
	@Test public void testIsValid() {
		System.out.println("Testing isValid method of Deploy class");
		
	  // own countries 3, 7, and 9, and 8 armies available for the player
		System.out.println("Testing valid deployment");
		Set<Integer> owned_countries = new HashSet<>(Arrays.asList(3, 7, 9));
		d_players.get(0).setOwnership(owned_countries);
		d_reinforcements = Collections.singletonList(8);
		System.out.println("first" + d_reinforcements);
		System.out.println("first" + d_players.get(0).getOwnership());
		d_state.setReinforcements(d_reinforcements);
		assertTrue(d_deploy_order.isValid(d_state, d_plyr_id));
		
		// Invalid case 1: country not occupied by the player
		System.out.println("Testing invalid deployment: deploy to country not owned");
		Set<Integer> owned_countries2 = new HashSet<>(Arrays.asList(7, 9));
		d_players.get(0).setOwnership(owned_countries2);
		System.out.println(d_reinforcements);
		System.out.println(d_players.get(0).getOwnership());
		assertFalse(d_deploy_order.isValid(d_state, d_plyr_id));
		
		// Invalid case 2: negative number of armies for deploy
		System.out.println("Testing invalid deployment: negative number of armies for deply");
		d_players.get(0).setOwnership(owned_countries);
		System.out.println(d_reinforcements);
		System.out.println(d_players.get(0).getOwnership());
		assertFalse(new Deploy(-1, 3).isValid(d_state, d_plyr_id));
		
		// Invalid case 3: more armies to be deployed than the armies available for the player
		System.out.println("Testing invalid deployment: deploying more armies than availabe for the player");
		System.out.println(d_reinforcements);
		System.out.println(d_players.get(0).getOwnership());
		d_reinforcements = Collections.singletonList(1);
		d_state.setReinforcements(d_reinforcements);
		assertFalse(d_deploy_order.isValid(d_state, d_plyr_id));
		
		System.out.println("Testing isValid method PASSED!");
	}
}