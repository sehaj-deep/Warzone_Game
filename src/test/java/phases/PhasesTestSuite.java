package phases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import orders.AdvanceTest;
import orders.BlockadeTest;
import orders.DeployTest;

/**
 * Test suite that runs all tests for the phases package
 */
@RunWith(Suite.class)
@SuiteClasses({ EndPhaseTest.class, ExecuteOrdersPhaseTest.class, PhaseTest.class, PlaySetupTest.class,
		ReinforcePhaseTest.class, AdvanceTest.class, BlockadeTest.class, DeployTest.class,
		PlaySetupTournamentModeTest.class })
public class PhasesTestSuite {

}
