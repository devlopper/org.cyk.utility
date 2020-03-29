package org.cyk.utility.__kernel__.persistence.query;

public class EntityManagerNotCachableUnitTestBenchmark extends AbstractEntityManagerUnitTestBenchmark {
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