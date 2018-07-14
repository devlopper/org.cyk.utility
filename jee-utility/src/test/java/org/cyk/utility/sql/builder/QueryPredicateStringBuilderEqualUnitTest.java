package org.cyk.utility.sql.builder;

import org.cyk.utility.computation.LogicalOperator;
import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilderEqual;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryPredicateStringBuilderEqualUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isColumn1Equal1(){
		assertionHelper.assertEquals("column1=1", __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArguments("column1","=","1").execute().getOutput());
	}
	
	@Test
	public void isColumn1Equala(){
		assertionHelper.assertEquals("column1=a", __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArguments("column1","=","a").execute().getOutput());
	}
	
	@Test
	public void isColumn1EqualParameterMarker(){
		assertionHelper.assertEquals("column1=?", __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArguments("column1","=","?").execute().getOutput());
	}
	
	@Test
	public void isColumn1Equal1AndColumn2Equal2(){
		QueryWherePredicateStringBuilder predicate1 = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArguments("column1","=","1");
		QueryWherePredicateStringBuilder predicate2 = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArguments("column2","=","2");
		assertionHelper.assertEquals("column1=1 AND column2=2", __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addChild(predicate1,LogicalOperator.AND,predicate2).execute().getOutput());
	}
}
