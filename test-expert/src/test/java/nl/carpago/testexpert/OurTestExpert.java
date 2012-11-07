package nl.carpago.testexpert;


public class OurTestExpert extends TestExpert {


	@Override
	public String getSourceFolder() {
		return "src/main/java";
	}

	@Override
	public Class<?> getFixture() {
		return FixturesForTst.class;
	}
	
	public OurTestExpert(){}

	@Override
	public boolean overwriteExistingFiles() {
		return true;
	}

	@Override
	public String getBinaryFolder() {
		return "bin";
	}

	@Override
	public String getOutputFolder() {
		
		return "src/test/generated-test";
	}

	@Override
	public String getNameOfTestsuite() {
		return "AllNewTests";
	}

}
