import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import game.GameTestSuite;
import map.MapTestSuite;
import models.ModelsTestSuite;
import phases.PhasesTestSuite;

/**
 * Test suite that runs all tests for the program
 */
@RunWith(Suite.class)
@SuiteClasses({ GameTestSuite.class, MapTestSuite.class, ModelsTestSuite.class, PhasesTestSuite.class })
public class AllTests {

}
