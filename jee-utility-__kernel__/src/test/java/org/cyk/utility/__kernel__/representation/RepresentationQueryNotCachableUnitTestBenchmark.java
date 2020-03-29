package org.cyk.utility.__kernel__.representation;

public class RepresentationQueryNotCachableUnitTestBenchmark extends AbstractRepresentationQueryUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getPersistenceUnitName__() {
		return "pu";
	}

	@Override
	protected Boolean __isResultCachable__() {
		return Boolean.FALSE;
	}

}