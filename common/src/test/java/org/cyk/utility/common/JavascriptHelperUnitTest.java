package org.cyk.utility.common;

import org.cyk.utility.common.helper.JavaScriptHelper;
import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class JavascriptHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		UniformResourceLocatorHelper.Stringifier.Adapter.Default.DEFAULT_SCHEME = "http";
		UniformResourceLocatorHelper.Stringifier.Adapter.Default.DEFAULT_HOST = "localhost";
		UniformResourceLocatorHelper.Stringifier.Adapter.Default.DEFAULT_PORT = 8080;
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_CONTEXT = "mycontext";
	}
	
	@Test
	public void openWindow(){
		assertEquals("toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400",new MapHelper.Stringifier.Adapter.Default()
				.addKeyValue("toolbar","yes","scrollbars","yes","resizable","yes","top","500","left","500","width","400","height","400").execute());
		
		JavaScriptHelper.Script.Window.Open open = new JavaScriptHelper.Script.Window.Open.Adapter.Default();
		open.setIdentifier("thename");
		open.getUniformResourceLocatorHelperStringifier().addPathTokens("mypath");
		open.setShowable(JavaScriptHelper.Script.Window.Open.TOOL_BAR, Boolean.TRUE).setLeftIndex(500).setTopIndex(500).setWidth(400).setHeight(400);
		assertEquals("window.open('http://localhost:8080/mycontext/mypath','thename','toolbar=yes,scrollbars=no,resizable=no,status=no,location=no,menubar=no,top=500,left=500,width=400,height=400','no')"
				,open.execute());
	}
	
	
}
