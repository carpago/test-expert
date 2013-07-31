package nl.carpago.testexpert;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class AbstractTestExpertTest extends TestCase {
	
	private AbstractTestExpert testExpert;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.testExpert = new AbstractTestExpert();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.testExpert = null;
	}
	
	@Test
	public void testCloneMeAndCheckForDeepEquality() {
		Person p1 = new Person("John Doe", 45);
		
		Person p2 = (Person) testExpert.cloneMe(p1);
		
		
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
		
		Assert.assertTrue(testExpert.checkForDeepEquality(p1, p2));
	}
	
	@Test
	public void testRemoveAllTagsAroundLiterals() {
		String in = "1<byte>2</byte>3<short>4</short>5<int>6</int>7<long>8</long>9<float>10</float>11<double>12</double>"+
				"13<char>14</char>15<boolean>16</boolean>17<string>18</string>";
		
		String expected = "123456789101112131415161718";
		String actual = this.testExpert.removeAllTagsAroundLiterals(in);
		
		Assert.assertTrue(expected.equals(actual));
		
		in = new String("the quick brown fox jumps over the lazy dog.");
		
		expected =  new String("the quick brown fox jumps over the lazy dog.");
		
		Assert.assertTrue(expected.equals(this.testExpert.removeAllTagsAroundLiterals(in)));
		
		in = "7527502457203458720345704";
		
		expected = "7527502457203458720345704";
		
		Assert.assertTrue(expected.equals(this.testExpert.removeAllTagsAroundLiterals(in)));
		
		
		in = new String("the <int>quick</int> brown fox jumps over <string>the lazy dog.");
		
		expected =  new String("the quick brown fox jumps over the lazy dog.");
		
		Assert.assertTrue(expected.equals(this.testExpert.removeAllTagsAroundLiterals(in)));
		
	}
	
	@Test
	public void testSetFieldThroughReflection() {
		Person p1 = new Person("John Doe", 45);
		
		Person p2 = (Person) this.testExpert.setFieldThroughReflection(p1, "name", "Jane Doe");
		
		Assert.assertEquals("Jane Doe", p2.getName());
	}
	
	@Test
	public void testgetFieldvalueThroughReflection1(){
		Person p1 = new Person("John Doe", 45);
		Object o = this.testExpert.getFieldvalueThroughReflection(p1, "age");
		Assert.assertEquals(45, o);
		
	}

	@Test
	public void testgetFieldvalueThroughReflection2(){
		Person p1 = new Person("John Doe", 45);
		Object o = this.testExpert.getFieldvalueThroughReflection(p1, "sofi");
		Assert.assertEquals(127788, o);
		
	}
	
	@Test
	public void testgetFieldvalueThroughReflection3(){
		Person p1 = new Person("John Doe", 45);
		Object o = this.testExpert.getFieldvalueThroughReflection(p1, "name");
		Assert.assertEquals("John Doe", o);
		
	}
}
