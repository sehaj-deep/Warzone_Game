package models;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite that runs all tests for the models package
 */
@RunWith(Suite.class)
@SuiteClasses({ AirliftTest.class, BombTest.class, DiplomacyTest.class })
public class ModelsTestSuite {

}
