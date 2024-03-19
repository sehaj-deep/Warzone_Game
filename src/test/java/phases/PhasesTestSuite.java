package phases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite that runs all tests for the phases package
 */
@RunWith(Suite.class)
@SuiteClasses({ EndPhaseTest.class, ExecuteOrdersPhaseTest.class, PhaseTest.class, PlaySetupTest.class,
		ReinforcementTest.class })
public class PhasesTestSuite {

}
