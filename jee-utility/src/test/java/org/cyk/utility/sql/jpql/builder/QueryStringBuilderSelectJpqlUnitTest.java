package org.cyk.utility.sql.jpql.builder;

import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilder;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryStringBuilderSelectJpqlUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void selectFromTuple(){
		Tuple tuple = new Tuple().setName("Tuple");
		
		QueryStringBuilderSelectJpql queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class).from(tuple);
		
		assertionHelper.assertEquals("SELECT tuple FROM Tuple tuple", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectFromTupleWhereColumn1Equal1(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) JpqlQualifier.inject(QueryWherePredicateStringBuilderEqualJpql.class)
				.addFormatArgumentObjects("column1","1");
		
		QueryStringBuilderSelectJpql queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class).from(tuple).where(predicateBuilder);
		
		assertionHelper.assertEquals("SELECT tuple FROM Tuple tuple WHERE column1=1", queryBuilder.execute().getOutput());
	}
	
}
