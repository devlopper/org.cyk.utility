package org.cyk.utility.sql.jpql.builder;

import org.cyk.utility.sql.builder.Attribute;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryOperandStringBuilderJpqlUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildOperandUsingAttribute(){
		assertionHelper.assertEquals("tuple.attr01", JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setAttributeNameBuilder(
				JpqlQualifier.inject(QueryAttributeNameBuilderJpql.class).setAttribute(new Attribute().setName("attr01")
				.setTuple(new Tuple().setName("Tuple")))).execute().getOutput());
	}
	
	@Test
	public void buildOperandUsingLiteral(){
		assertionHelper.assertEquals("3", JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setLiteral(3).execute().getOutput());
	}
	
	@Test
	public void buildOperandUsingParameter(){
		assertionHelper.assertEquals(":myparam", JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setParameterStringBuilder(
				JpqlQualifier.inject(QueryParameterStringBuilderJpql.class).setParameterName("myparam")).execute().getOutput());
	}
}
