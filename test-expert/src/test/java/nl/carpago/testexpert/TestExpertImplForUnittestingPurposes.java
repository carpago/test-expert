package nl.carpago.testexpert;


public class TestExpertImplForUnittestingPurposes extends TestExpert {

	@Override
	public String getSourceFolder() {
		return "src/main/java";
	}

	@Override
	public Class<?> getFixture() {
		return FixturesForTst.class;
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
		return "AllGeneratedTests";
	}

	@Override
	public MockFramework getMockFramework() {
		if(this.mockFramework != null) {
			return this.mockFramework;
		}
		else {
			return MockFramework.EASYMOCK; // for convenience while running this test in file generating mode ...
		}
	}
}
