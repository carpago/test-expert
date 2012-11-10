package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class InvokeDTOTest extends TestCase {
	
	private InvokeDTO invoke;
	private Set <String> collabs;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		collabs = new HashSet<String>();
		collabs.add("thisisjustfortesting");
		collabs.add("thisIsJustForTestingAgain");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testConstructorAndProcess() {
		
		String line = null;
		
		
		line = "String result = persoonDao.getSofi(aNumber);";
		String expectedCollab = "persoonDao";
		String expectedCollabMethodParams = "persoonDao.getSofi(aNumber)";
		String expectedConstruction = expectedCollabMethodParams +";";
		List <String> expectedParams = new ArrayList<String>();
		expectedParams.add("aNumber");
		String expectedMethod = "getSofi";
		validate(line, expectedCollab, expectedCollabMethodParams, expectedConstruction, expectedParams, expectedMethod);
		
		line = "if(onderhoudenVliegveldDao.add(aNumber) == 3)";
		expectedCollab = "onderhoudenVliegveldDao";
		expectedCollabMethodParams = "onderhoudenVliegveldDao.add(aNumber)";
		expectedConstruction = "onderhoudenVliegveldDao.add(aNumber);";
		expectedParams = new ArrayList<String>();
		expectedParams.add("aNumber");
		expectedMethod = "add";
		validate(line, expectedCollab, expectedCollabMethodParams, expectedConstruction, expectedParams, expectedMethod);
		
		
		line = "if(onderhoudenVliegveldDao.add(aNumber, anotherNumber) == 3)";
		expectedCollab = "onderhoudenVliegveldDao";
		expectedCollabMethodParams = "onderhoudenVliegveldDao.add(aNumber, anotherNumber)";
		expectedConstruction = "onderhoudenVliegveldDao.add(aNumber, anotherNumber)";
		expectedParams = new ArrayList<String>();
		expectedParams.add("aNumber");
		expectedParams.add("anotherNumber");
		expectedMethod = "add";
		validate(line, expectedCollab, expectedCollabMethodParams, expectedConstruction, expectedParams, expectedMethod);
		
		line = "if(onderhoudenVliegveldDao.add(aNumber, anotherNumber).toString().equals(\"3\");";
		expectedCollab = "onderhoudenVliegveldDao";
		expectedCollabMethodParams = "onderhoudenVliegveldDao.add(aNumber, anotherNumber)";
		expectedConstruction = "onderhoudenVliegveldDao.add(aNumber, anotherNumber)";
		expectedParams = new ArrayList<String>();
		expectedParams.add("aNumber");
		expectedParams.add("anotherNumber");
		expectedMethod = "add";
		validate(line, expectedCollab, expectedCollabMethodParams, expectedConstruction, expectedParams, expectedMethod);
		
		line = "if(onderhoudenVliegveldDao.add(aNumber, new String()).toString().equals(\"3\");";
		expectedCollab = "onderhoudenVliegveldDao";
		expectedCollabMethodParams = "onderhoudenVliegveldDao.add(aNumber, new String())";
		expectedConstruction = "onderhoudenVliegveldDao.add(aNumber, new String())";
		expectedParams = new ArrayList<String>();
		expectedParams.add("aNumber");
		expectedParams.add("new String()");
		expectedMethod = "add";
		validate(line, expectedCollab, expectedCollabMethodParams, expectedConstruction, expectedParams, expectedMethod);
		
	}
	
	private void validate(String line, String expectedCollab, String expectedCollabMethodParams, String expectedConstruction, List<String> expectedParams, String expectedMethod) {
		this.collabs.add(expectedCollab);
		invoke = new InvokeDTO(line, collabs);
		Assert.assertEquals(expectedCollab, invoke.getCollab());
		Assert.assertEquals(expectedCollabMethodParams, invoke.getCollabMethodParams());
		Assert.assertEquals(expectedParams, invoke.getParams());
		Assert.assertEquals(expectedMethod, invoke.getMethod());
	}
}
