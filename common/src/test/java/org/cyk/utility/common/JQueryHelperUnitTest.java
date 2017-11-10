package org.cyk.utility.common;

import org.cyk.utility.common.helper.JQueryHelper;
import org.cyk.utility.common.helper.JavaScriptHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class JQueryHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		JQueryHelper.JQUERY = "$";
	}
	
	@Test
	public void getHide(){
		assertEquals("$(this).hide();", JQueryHelper.getInstance().getHide(JavaScriptHelper.OBJECT_THIS));
		assertEquals("$(this).show();", JQueryHelper.getInstance().getShow(JavaScriptHelper.OBJECT_THIS));
	}
	
	
}
