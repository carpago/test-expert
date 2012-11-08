package nl.carpago.testexpert;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class AbstractTestExpertTest extends TestCase {
	
	private AbstractTestExpert testExpertTools;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.testExpertTools = new AbstractTestExpert();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.testExpertTools = null;
	}
	
	@Test
	public void testCloneMeAndCheckForDeepEquality() {
		Person p1 = new Person("John Doe", 45);
		
		Person p2 = (Person) testExpertTools.cloneMe(p1);
		
		
		Assert.assertTrue(p1.getName().equals(p2.getName()));
		Assert.assertTrue(p1.getAge() == p2.getAge());
		Assert.assertFalse(p1 == p2);
		Assert.assertFalse(p1.getName() == p2.getName());
		
		Bike bike1 = p1.getBike();
		Bike bike2 = p2.getBike();
		
		Assert.assertTrue(bike1.getMake().equals(bike2.getMake()));
		Assert.assertTrue(bike2.getProductionyear() == bike2.getProductionyear());
		
		Assert.assertFalse(bike1.getMake() == bike2.getMake());
		Assert.assertFalse(bike1 == bike2);
		
		Assert.assertTrue(testExpertTools.checkForDeepEquality(p1, p2));
	}
	
	@Test
	public void testRemoveAllTagsAroundLiterals() {
		String[] basicTypes = new String[]{"byte", "short", "int", "long", "float", "double","char", "boolean","string"};
		final String in = "1<byte>2</byte>3<short>4</short>5<int>6</int>7<long>8</long>9<float>10</float>11<double>12</double>"+
				"13<char>14</char>15<boolean>16</boolean>17<string>18</string>";
		
		String expected = "123456789101112131415161718";
		String actual = this.testExpertTools.removeAllTagsAroundLiterals(in);
		System.out.println(actual);
		
		Assert.assertTrue(expected.equals(actual));
		
		
	}
	

}
