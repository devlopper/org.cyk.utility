package org.cyk.utility.common;

import org.cyk.utility.common.builder.TextStringBuilder;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class TextStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Override
	protected void _execute_() {
		super._execute_();
		
	}
	
	@Test
	public void response(){
		assertEquals("yes", inject(TextStringBuilder.class).setResponse(Boolean.TRUE).build());
		assertEquals("no", inject(TextStringBuilder.class).setResponse(Boolean.FALSE).build());
		assertEquals("no", inject(TextStringBuilder.class).setResponse(null).build());
	}
	
}
