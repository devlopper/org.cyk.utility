package org.cyk.utility.common;

import org.cyk.utility.test.unit.AbstractUnitTest;

public class TextHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	/*static {
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
	
	@Test
	public void response(){
		assertEquals("yes", new TextHelper.Get.Adapter.Default("yes").execute());
		
		assertEquals("yes", inject(TextStringBuilder.class).setResponse(Boolean.TRUE).build());
		assertEquals("no", inject(TextStringBuilder.class).setResponse(Boolean.FALSE).build());
		assertEquals("no", inject(TextStringBuilder.class).setResponse(null).build());
	}
	
	@Test
	public void identifier(){
		assertEquals("bonjour", inject(TextStringBuilder.class).setIdentifier("good.morning").build());
		
	}*/
}
