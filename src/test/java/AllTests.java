import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import game.GameTestSuite;
import map.MapTestSuite;
import orders.OrdersTestSuite;
import phases.PhasesTestSuite;
import players.PlayerTestSuite;

/**
 * Test suite that runs all tests for the program
 */
@RunWith(Suite.class)
@SuiteClasses({ GameTestSuite.class, MapTestSuite.class, OrdersTestSuite.class, PhasesTestSuite.class,
		PlayerTestSuite.class })
public class AllTests {

}
