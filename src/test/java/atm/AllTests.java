package atm;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Test suite to run all test cases in the ATM system.
 */
@Suite
@SelectClasses({
        ATMMachineTest.class,
        AuthenticatorTest.class,
        BalanceTest.class,
        HistoryTest.class,
        SecurityTest.class
})
@TestInstance(Lifecycle.PER_CLASS)
public class AllTests {
}
