package game;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * This class is designed to do unit test of features in Player class
 */
public class PlayerTest {
	static Player d_player;

	/**
	 * This is the common setup for all test cases and will be run before each test
	 */
	@Before public void before() {
		d_player = new Player(Integer.toString(0));
		System.out.println("Test Player Setup completed");
	}

	/**
	 * test issue_order function of Player
	 *
	 * check whether issuance of an order successfully adds that order to the list of orders
	 */
	@Test public void testIssueOrder() {
		System.out.println("Testing issue_order method of Player class");
		System.out.println("Initial list of orders: " + d_player.getListOrders());
		Deploy l_deployOrder = new Deploy(4, "korea");  // player issued an order

		System.out.println("Order1 from the player");
		d_player.issue_order(l_deployOrder);
		assertEquals(1, d_player.getListOrders().size());

		l_deployOrder = new Deploy(3, "usa");  // player issued an order
		System.out.println("Order2 from the player");
		d_player.issue_order(l_deployOrder);
		assertEquals(2, d_player.getListOrders().size());

		System.out.println("Testing issue_order method PASSED!\n");
	}

	/**
	 * test next_order function of Player
	 *
	 * check whether next_order successfully extracts an order from the list of orders
	 */
	@Test public void testNextOrder() {
		System.out.println("Testing next_order method of Player class");
		Deploy l_deployOrder = new Deploy(4, "korea");  // player issued an order
		l_deployOrder.addOrderID(Integer.toString(1));
		d_player.issue_order(l_deployOrder);
		l_deployOrder = new Deploy(3, "usa");  // player issued an order
		l_deployOrder.addOrderID(Integer.toString(2));
		d_player.issue_order(l_deployOrder);
		System.out.println("Initial list of orders: " + d_player.getListOrders());
		Order nextOrder = d_player.next_order();
		System.out.println("Next Order for execution: " + nextOrder);
		assertEquals(1, d_player.getListOrders().size());

		System.out.println("Testing next_order method PASSED!\n");
	}
}