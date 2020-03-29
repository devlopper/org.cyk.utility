package org.cyk.utility.__kernel__.controller;

public class ControllerQueryNotCachableUnitTestBenchmark extends AbstractControllerQueryUnitTestBenchmark {
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