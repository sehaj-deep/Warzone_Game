package map;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import phases.PreloadTest;

/**
 * Test suite that runs all tests for the map package
 */
@RunWith(Suite.class)
@SuiteClasses({ PreloadTest.class })
public class MapTestSuite {

}
