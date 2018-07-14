package org.cyk.utility.sql.builder;

import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void select(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addFormatArguments("column1","=","1");
		
		assertionHelper.assertEquals("SELECT * FROM Tuple tuple WHERE column1=1", __inject__(QueryStringBuilderSelect.class)
				.setSelectBuilder((QueryClauseStringBuilderSelect) __inject__(QueryClauseStringBuilderSelect.class).addTuples(tuple))
				.setFromBuilder((QueryClauseStringBuilderFrom) __inject__(QueryClauseStringBuilderFrom.class).addTuples(tuple))
				.setWhereBuilder(__inject__(QueryClauseStringBuilderWhere.class).setPredicateBuilder(predicateBuilder))
				.execute().getOutput());
	}
	
}
