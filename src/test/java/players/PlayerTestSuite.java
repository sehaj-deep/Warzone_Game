package players;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite that runs all tests for the players package.
 */
@RunWith(Suite.class)
@SuiteClasses({ PlayerTest.class, AggressivePlayerStrategyTest.class, BenevolentPlayerStrategyTest.class })
public class PlayerTestSuite {

}
