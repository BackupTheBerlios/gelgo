package test.game;

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
		TestSuite suite= new TestSuite("Game Tests");
		suite.addTest(BoardTest.suite());
		suite.addTest(CoordinatesTest.suite());
		suite.addTest(AssociatedCoordinatesTest.suite());
		return suite;
	}
}
