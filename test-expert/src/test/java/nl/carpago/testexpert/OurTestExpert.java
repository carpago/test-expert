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

}
