package test;

import junit.framework.*;

/**
 * TestSuite that runs all the JUnit tests
 *
 */
public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	
	public static Test suite() {
		TestSuite suite= new TestSuite("Jello Go Tests");
		suite.addTest(test.game.AllTests.suite());
		return suite;
	}
}
