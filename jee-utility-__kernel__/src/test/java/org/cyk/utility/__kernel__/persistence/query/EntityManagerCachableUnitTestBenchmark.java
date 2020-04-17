package org.cyk.utility.__kernel__.persistence.query;

public class EntityManagerCachableUnitTestBenchmark extends AbstractEntityManagerUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getPersistenceUnitName__() {
		return "pu_cachable";
	}

	@Override
	protected Boolean __isResultCachable__() {
		return Boolean.TRUE;
	}

}