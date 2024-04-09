package players;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PlayerTest.class, AggressivePlayerStrategyTest.class, BenevolentPlayerStrategyTest.class })
public class PlayerTestSuite {

}
