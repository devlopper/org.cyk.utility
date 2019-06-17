package org.cyk.utility.sql.jpql.builder;

import org.cyk.utility.sql.builder.Attribute;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryAttributeNameBuilderJpqlUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildNameWithoutTuple(){
		assertionHelper.assertEquals("attr01", JpqlQualifier.inject(QueryAttributeNameBuilderJpql.class).setAttribute(new Attribute().setName("attr01")
				.setIsPrefixedWithTuple(Boolean.FALSE).setTuple(new Tuple().setName("Tuple"))).execute().getOutput());
	}
	
	@Test
	public void buildNameWithTupleName(){
		assertionHelper.assertEquals("tuple.attr01", JpqlQualifier.inject(QueryAttributeNameBuilderJpql.class).setAttribute(new Attribute().setName("attr01")
				.setTuple(new Tuple().setName("Tuple"))).execute().getOutput());
	}
	
	@Test
	public void buildNameWithTupleAlias(){
		assertionHelper.assertEquals("t.attr01", JpqlQualifier.inject(QueryAttributeNameBuilderJpql.class).setAttribute(new Attribute().setName("attr01")
				.setIsPrefixedWithTuple(Boolean.TRUE).setTuple(new Tuple().setName("Tuple").setAlias("t"))).execute().getOutput());
	}
}
