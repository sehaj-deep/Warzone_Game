package game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite that runs all tests for the game package
 */
@RunWith(Suite.class)
@SuiteClasses({ GameEngineTest.class, GameLoadTest.class, GameSaveTest.class })
public class GameTestSuite {

}
