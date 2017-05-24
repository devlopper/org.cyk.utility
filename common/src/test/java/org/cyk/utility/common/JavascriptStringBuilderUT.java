package org.cyk.utility.common;

import org.cyk.utility.common.builder.NameValueCollectionStringBuilder;
import org.cyk.utility.common.builder.javascript.OpenWindowStringBuilder;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class JavascriptStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		NameValueCollectionStringBuilder.Listener.COLLECTION.add(new NameValueCollectionStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
			
			
		});
	}

	@Test
	public void openWindow(){
		assertEquals("toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400",new NameValueCollectionStringBuilder()
				.setSeparator(",").addNamesValues("toolbar","yes","scrollbars","yes","resizable","yes","top","500","left","500","width","400","height","400").build());
		
		assertEquals("window.open('theurl','thename','toolbar=yes,scrollbars=no,resizable=no,status=no,location=no,menubar=no,top=500,left=500,width=400,height=400','no')",new OpenWindowStringBuilder("thename","theurl")
				.setShowToolBar(Boolean.TRUE).setLeftIndex(500).setTopIndex(500).setWidth(400).setHeight(400).build());
	}
	
	
}
