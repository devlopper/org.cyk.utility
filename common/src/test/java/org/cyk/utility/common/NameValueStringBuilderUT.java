package org.cyk.utility.common;

import org.cyk.utility.common.builder.NameValueStringBuilder;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class NameValueStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		
	}
	
	@Test
	public void nameValue(){
		assertEquals(Constant.EMPTY_STRING,new NameValueStringBuilder().set(null, null).build());
		assertEquals(Constant.EMPTY_STRING,new NameValueStringBuilder().set("p1", null).build());
		assertEquals(Constant.EMPTY_STRING,new NameValueStringBuilder().set(null, "a").build());
		assertEquals("p1=a",new NameValueStringBuilder().set("p1", "a").build());
		
		assertEquals("p1=a",new NameValueStringBuilder().set("p1", "a").set("p1", null).build());
		assertEquals("p1=b",new NameValueStringBuilder().set("p1", null).set("p1", "b").build());
		
		assertEquals("p1=a&p1=b&p1=v3",new NameValueStringBuilder().set("p1", "a").set("p1", "b").set("p1", "v3").build());
		
		assertEquals("p1=2_f_0",new NameValueStringBuilder().set("p1", "15").setEncoded(Boolean.TRUE).build());
	}
	
}
