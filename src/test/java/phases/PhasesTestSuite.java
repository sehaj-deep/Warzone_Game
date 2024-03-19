package phases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EndPhaseTest.class, ExecuteOrdersPhaseTest.class, PhaseTest.class, PlaySetupTest.class,
		ReinforcementTest.class })
public class PhasesTestSuite {

}
