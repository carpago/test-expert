package nl.carpago.testexpert;

public class OurTestExpert extends TestExpert {

	public OurTestExpert(Class<?> class1, Class<?> class2) {
		super(class1, class2);
	}

	@Override
	public String getSourceFolder() {
		return "src/main/java";
	}

	@Override
	public Class<?> getFixture() {
		return FixturesForTst.class;
	}

}
