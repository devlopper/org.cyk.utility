package org.cyk.utility.sql.jpql.builder;

import org.cyk.utility.sql.builder.Attribute;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryWherePredicateStringBuilderEqualJpqlUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void builFieldEqualParameter(){
		assertionHelper.assertEquals("tuple.name=:name", JpqlQualifier.inject(QueryWherePredicateStringBuilderEqualJpql.class)
				.setFirstOperandStringBuilder(JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setAttributeNameBuilder(
						JpqlQualifier.inject(QueryAttributeNameBuilderJpql.class).setAttribute(new Attribute().setName("name").setTuple(new Tuple().setName("Tuple")))))
				.setSecondOperandStringBuilder(JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setParameterStringBuilder(
						JpqlQualifier.inject(QueryParameterStringBuilderJpql.class).setParameterName("name"))).execute().getOutput())
				;
	}
	
	@Test
	public void builFieldEqualLiteralNumber(){
		assertionHelper.assertEquals("tuple.name=51", JpqlQualifier.inject(QueryWherePredicateStringBuilderEqualJpql.class)
				.setFirstOperandStringBuilder(JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setAttributeNameBuilder(
						JpqlQualifier.inject(QueryAttributeNameBuilderJpql.class).setAttribute(new Attribute().setName("name").setTuple(new Tuple().setName("Tuple")))))
				.setSecondOperandStringBuilder(JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setLiteral(51)).execute().getOutput())
				;
	}
	
	@Test
	public void builFieldEqualLiteralString(){
		assertionHelper.assertEquals("tuple.name='hello'", JpqlQualifier.inject(QueryWherePredicateStringBuilderEqualJpql.class)
				.setFirstOperandStringBuilder(JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setAttributeNameBuilder(
						JpqlQualifier.inject(QueryAttributeNameBuilderJpql.class).setAttribute(new Attribute().setName("name").setTuple(new Tuple().setName("Tuple")))))
				.setSecondOperandStringBuilder(JpqlQualifier.inject(QueryOperandStringBuilderJpql.class).setLiteral("'hello'")).execute().getOutput())
				;
	}
}
