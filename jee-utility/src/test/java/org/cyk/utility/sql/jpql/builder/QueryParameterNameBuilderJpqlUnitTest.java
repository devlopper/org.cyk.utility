package org.cyk.utility.sql.jpql.builder;

import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryParameterNameBuilderJpqlUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildParameter(){
		assertionHelper.assertEquals(":param", JpqlQualifier.inject(QueryParameterNameBuilderJpql.class).setParameter("param").execute().getOutput());
	}
	
	
}
