package org.cyk.utility.sql.builder;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryClauseStringBuilderWhereUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void build(){
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArguments("column1","=","1");
		assertionHelper.assertEquals("WHERE column1=1", __inject__(QueryClauseStringBuilderWhere.class).setPredicateBuilder(predicateBuilder).execute().getOutput());
	}
	
}
