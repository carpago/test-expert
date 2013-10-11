package nl.carpago.testexpert;

import nl.carpago.testexpert.fixtures.FixtureForDemos;

public class Runner extends TestExpert {

	@Override
	public String getSourceFolder() {
		return "src/main/demo";
	}

	@Override
	public Class<?> getFixture() {
		return FixtureForDemos.class;
	}

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
	public String getTestsuiteName() {
		return "ArbitraryName";
	}

	@Override
	public MockFramework getMockFramework() {
		return MockFramework.EASYMOCK;
	}

}
