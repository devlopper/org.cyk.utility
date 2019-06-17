package org.cyk.utility.sql.builder;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryOperandStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildOperandUsingAttribute(){
		assertionHelper.assertEquals("tuple.attr01", __inject__(QueryOperandStringBuilder.class).setAttributeNameBuilder(
				__inject__(QueryAttributeNameBuilder.class).setAttribute(new Attribute().setName("attr01")
				.setTuple(new Tuple().setName("Tuple")))).execute().getOutput());
	}
	
	@Test
	public void buildOperandUsingAttributeShortcut(){
		Tuple tuple = new Tuple().setName("Tuple").addAttributes(new Attribute().setName("attr01"));
		assertionHelper.assertEquals("tuple.attr01", __inject__(QueryOperandStringBuilder.class).executeAttribute("attr01", tuple).getOutput());
	}
	
	@Test
	public void buildOperandUsingLiteral(){
		assertionHelper.assertEquals("3", __inject__(QueryOperandStringBuilder.class).setLiteral(3).execute().getOutput());
	}
	
	@Test
	public void buildOperandUsingLiteralShortcut(){
		assertionHelper.assertEquals("3", __inject__(QueryOperandStringBuilder.class).executeLiteral(3).getOutput());
	}
	
	@Test
	public void buildOperandUsingParameter(){
		assertionHelper.assertEquals("@myparam", __inject__(QueryOperandStringBuilder.class).setParameterNameBuilder(
				__inject__(QueryParameterNameBuilder.class).setParameter("myparam")).execute().getOutput());
	}
	
	@Test
	public void buildOperandUsingParameterShortcut(){
		assertionHelper.assertEquals("@myparam", __inject__(QueryOperandStringBuilder.class).executeParameter("myparam").getOutput());
	}
}
