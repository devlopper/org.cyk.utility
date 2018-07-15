package org.cyk.utility.sql.builder;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryAttributeNameBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildNameWithoutTuple(){
		assertionHelper.assertEquals("attr01", __inject__(QueryAttributeNameBuilder.class).execute("attr01").getOutput());
	}
	
	@Test
	public void buildNameWithTupleName(){
		Tuple tuple = new Tuple().setName("Tuple").addAttributes(new Attribute().setName("attr01").setIsPrefixedWithTuple(Boolean.TRUE));
		assertionHelper.assertEquals("tuple.attr01", __inject__(QueryAttributeNameBuilder.class).execute("attr01", tuple).getOutput());
	}
	
	@Test
	public void buildNameWithTupleAlias(){
		Tuple tuple = new Tuple().setName("Tuple").setAlias("t").addAttributes(new Attribute().setName("attr01").setIsPrefixedWithTuple(Boolean.TRUE));
		assertionHelper.assertEquals("t.attr01", __inject__(QueryAttributeNameBuilder.class).execute("attr01",tuple).getOutput());
	}
}
