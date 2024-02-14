package game;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.After;

/*
 * This class is designed to do unit test of features in Deploy class (inherits from Order)
 */
public class TestDeploy {
	static Deploy d_deploy_order;
	
	/*
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before public void before() {
		System.out.println("Testing Deploy class Setup: 4 armies to country 3");
		d_deploy_order = new Deploy(4, 3);
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
		State state = new State(owned_countries, 8);
		assertTrue(d_deploy_order.isValid(state));
		
		// Invalid case 1: country not occupied by the player
		System.out.println("Testing invalid deployment: deploy to country not owned");
		Set<Integer> owned_countries2 = new HashSet<>(Arrays.asList(7, 9));
		state = new State(owned_countries2, 8);
		assertFalse(d_deploy_order.isValid(state));
		
		// Invalid case 2: negative number of armies for deploy
		System.out.println("Testing invalid deployment: negative number of armies for deply");
		state = new State(owned_countries, 8);
		assertFalse(new Deploy(-1, 3).isValid(state));
		
		// Invalid case 3: more armies to be deployed than the armies available for the player
		System.out.println("Testing invalid deployment: deploying more armies than availabe for the player");
		state = new State(owned_countries, 1);
		assertFalse(d_deploy_order.isValid(state));
		
		System.out.println("Testing isValid method PASSED!");
	}
}