package org.cyk.utility.sql.builder;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryPredicateStringBuilderEqualUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isColumn1Equal1(){
		assertionHelper.assertEquals("column1=1", __inject__(QueryPredicateStringBuilderEqual.class).addFormatArguments("column1","1").execute().getOutput());
	}
	
	@Test
	public void isColumn1Equala(){
		assertionHelper.assertEquals("column1=a", __inject__(QueryPredicateStringBuilderEqual.class).addFormatArguments("column1","a").execute().getOutput());
	}
	
	@Test
	public void isColumn1EqualParameterMarker(){
		assertionHelper.assertEquals("column1=?", __inject__(QueryPredicateStringBuilderEqual.class).addFormatArguments("column1","?").execute().getOutput());
	}
}
