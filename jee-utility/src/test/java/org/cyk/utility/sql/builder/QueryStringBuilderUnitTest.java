package org.cyk.utility.sql.builder;

import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	//@Test
	public void select(){
		assertionHelper.assertEquals("select", __inject__(QueryStringBuilderSelect.class).execute().getOutput());
	}
	
	@Test
	public void provideTestMethods(){
		//TODO provides test methods
	}
}
