package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class InvokeDTOTest extends TestCase {
	
	private InvokeDTO invoke;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testConstructorAndProcess() {
		
		//String result = persoonDao.getSofi(aNumber);
		
		invoke = new InvokeDTO("String result = persoonDao.getSofi(aNumber);");
		
		String expectedCollab = "persoonDao";
		String collab = invoke.getCollab();
		
		String expectedCollabMethodParams = "persoonDao.getSofi(aNumber)";
		String collabMethodParams = invoke.getCollabMethodParams();
		
		String expectedConstruction = expectedCollabMethodParams;
		String construction = invoke.getConstruction();
		
		List <String> expectedParams = new ArrayList<String>();
		expectedParams.add("aNumber");
		List <String> params = invoke.getParams();
		
		
		String expectedMethod = "getSofi";
		String method = invoke.getMethod();
		
		Assert.assertTrue(expectedCollab.equals(collab));
		Assert.assertTrue(collabMethodParams+" not equal to "+expectedCollabMethodParams, expectedCollabMethodParams.equals(collabMethodParams));
		Assert.assertTrue(construction+" not equal to "+expectedConstruction, expectedConstruction.equals(construction));
		Assert.assertTrue(expectedParams.equals(params));

		Assert.assertTrue(expectedMethod.equals(method));
		
		String line = "int result = this.persoonDao.getSofi(aNumber, aSecondNumber);";
		expectedCollab = "this";
		expectedCollabMethodParams = "this.persoonDao.getSofi(aNumber, aSecondNumber)";
		expectedConstruction = "this.persoonDao.getSofi(aNumber, aSecondNumber)";
		expectedParams = new ArrayList<String>();
		expectedParams.add("aNumber");
		expectedParams.add("aSecondNumber");
		expectedMethod = "persoonDao.getSofi";
		this.validateTestCase(line, expectedCollab, expectedCollabMethodParams, expectedConstruction, expectedParams, expectedMethod);
		
		line = "if(onderhoudenVliegVelddao.add(aNumber) == 3)";
		x hier verder
		
		
	}
	
	private void validateTestCase(String line, String expectedCollab, String expectedCollabMethodParams, String expectedConstruction, List<String> expectedParams, String expectedMethod) {
		invoke = new InvokeDTO(line);
		Assert.assertEquals(expectedCollab, invoke.getCollab());
		Assert.assertEquals(expectedCollabMethodParams, invoke.getCollabMethodParams());
		Assert.assertEquals(expectedConstruction, invoke.getConstruction());
		Assert.assertEquals(expectedParams, invoke.getParams());
		Assert.assertEquals(expectedMethod, invoke.getMethod());
	}
}
