package org.cyk.utility.sql.builder;

import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryStringBuilderSelectUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void selectAllAttributeFromTuple(){
		Tuple tuple = new Tuple().setName("Tuple");
	
		QueryStringBuilderSelect queryBuilder = __inject__(QueryStringBuilderSelect.class).from(tuple);
		
		assertionHelper.assertEquals("SELECT * FROM Tuple tuple", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectAllAttributeFromTupleWhereColumn1Equal1(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addFormatArguments("column1","=","1");
		
		QueryStringBuilderSelect queryBuilder = __inject__(QueryStringBuilderSelect.class).from(tuple).where(predicateBuilder);
		
		assertionHelper.assertEquals("SELECT * FROM Tuple tuple WHERE column1=1", queryBuilder.execute().getOutput());
	}
	
}
