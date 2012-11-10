import nl.carpago.testexpert.AbstractTestExpertTest;
import nl.carpago.testexpert.InvokeDTOTest;
import nl.carpago.testexpert.TestExpertTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite();

		suite.addTest(new JUnit4TestAdapter(TestExpertTest.class));
		suite.addTest(new JUnit4TestAdapter(InvokeDTOTest.class));
		suite.addTest(new JUnit4TestAdapter(AbstractTestExpertTest.class));

		return suite;
	}

}
