package game;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/*
 * This class is designed to do unit test of features in Player class
 */
public class TestPlayer {
	static Player d_player;
	
	/*
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before public void before() {
		d_player = new Player(0);
		System.out.println("Test Player Setup completed");
	}
	
	/*
	 * test issue_order function of Player
	 * 
	 * check whether issuance of an order successfully adds that order to the list of orders
	 */
	@Test public void testIssueOrder() {
		System.out.println("Testing issue_order method of Player class");
		System.out.println("Initial list of orders: " + d_player.getListOrders());
		Deploy deploy_order = new Deploy(4, 3);  // player issued an order
		
		System.out.println("Order1 from the player");
		d_player.issue_order(deploy_order);
		assertEquals(d_player.getListOrders().size(), 1);
		
		deploy_order = new Deploy(3, 4);  // player issued an order
		System.out.println("Order2 from the player");
		d_player.issue_order(deploy_order);
		assertEquals(d_player.getListOrders().size(), 2);
		
		System.out.println("Testing issue_order method PASSED!\n");
	}
	
	/*
	 * test next_order function of Player
	 * 
	 * check whether next_order successfully extracts an order from the list of orders
	 */
	@Test public void testNextOrder() {
		System.out.println("Testing next_order method of Player class");
		Deploy deploy_order = new Deploy(4, 3);  // player issued an order
		deploy_order.addOrderID(Integer.toString(1));
		d_player.issue_order(deploy_order);
		deploy_order = new Deploy(3, 4);  // player issued an order
		deploy_order.addOrderID(Integer.toString(2));
		d_player.issue_order(deploy_order);
		System.out.println("Initial list of orders: " + d_player.getListOrders());
		Order next_order = d_player.next_order();
		System.out.println("Next Order for execution: " + next_order);
		assertEquals(d_player.getListOrders().size(), 1);
		
		System.out.println("Testing next_order method PASSED!\n");
	}
}