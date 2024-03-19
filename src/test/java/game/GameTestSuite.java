package game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AdvanceTest.class, BlockadeTest.class, DeployTest.class, GameStateTest.class, PlayerTest.class })
public class GameTestSuite {

}
