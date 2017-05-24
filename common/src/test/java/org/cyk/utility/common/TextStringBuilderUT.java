package org.cyk.utility.common;

import org.cyk.utility.common.builder.TextStringBuilder;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class TextStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	static {
		TextStringBuilder.Listener.COLLECTION.add(new TextStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;

			@Override
			public String getIdentifierMapping(String identifier) {
				if("good.morning".equals(identifier))
					return "bonjour";
				return super.getIdentifierMapping(identifier);
			}
		});
	}
	
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
	
	@Test
	public void identifier(){
		assertEquals("bonjour", inject(TextStringBuilder.class).setIdentifier("good.morning").build());
		
	}
}
