package org.cyk.utility.common;

import java.io.Serializable;

import org.cyk.utility.common.helper.JavaScriptHelper;
import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.MapHelper.EntryComponent;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper.PathStringifier;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class JavascriptHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		
		UniformResourceLocatorHelper.DEFAULT_LISTENER_CLASS = UniformResourceLocatorHelperListener.class;
		PathStringifier.Adapter.Default.DEFAULT_CONTEXT = "mycontext";		
		PathStringifier.Adapter.Default.DEFAULT_UNIFORM_RESOURCE_LOCATOR_LISTENER_CLASS = UniformResourceLocatorHelperListener.class;
		
		MapHelper.Stringifier.Adapter.Default.DEFAULT_MAP_LISTENER_CLASS = MapListener.class;
		MapHelper.Stringifier.Entry.Adapter.Default.DEFAULT_MAP_LISTENER_CLASS = MapListener.class;
	}
	
	@Test
	public void navigateWindow(){
		assertEquals("window.location.href='http://localhost:8080'", new JavaScriptHelper.Script.Window.Navigate.Adapter.Default().execute());
		assertEquals("window.location.href='http://localhost:8080/mycontext/entity/edit.jsf?action=create&clazz=entity'", new JavaScriptHelper.Script.Window.Navigate.Adapter
				.Default().setUniformResourceLocatorStringifier(Constant.Action.CREATE, Entity.class).execute());
		assertEquals("window.location.href='http://localhost:8080/mycontext/entity/consult.jsf?action=consult&identifiable=15'", new JavaScriptHelper.Script.Window.Navigate.Adapter
				.Default().setUniformResourceLocatorStringifier(Constant.Action.CONSULT, new Entity(15l,"a")).execute());
	}
	
	@Test
	public void openWindow(){
		assertEquals("toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400",new MapHelper.Stringifier.Adapter.Default()
				.addKeyValue("toolbar","yes","scrollbars","yes","resizable","yes","top","500","left","500","width","400","height","400").setSeparator(",").execute());
		
		JavaScriptHelper.Script.Window.Open open = new JavaScriptHelper.Script.Window.Open.Adapter.Default();
		open.setIdentifier("thename");
		open.getUniformResourceLocatorStringifier().addPathTokens("mypath");
		open.setShowable(JavaScriptHelper.Script.Window.Open.TOOL_BAR, Boolean.TRUE).setLeftIndex(500).setTopIndex(500).setWidth(400).setHeight(400);
		assertEquals("window.open('http://localhost:8080/mycontext/mypath','thename','toolbar=yes,scrollbars=no,resizable=no,status=no,location=no,menubar=no,top=500,left=500,width=400,height=400','no')"
				,open.execute());
	}
	
	/**/
	
	public static class UniformResourceLocatorHelperListener extends UniformResourceLocatorHelper.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		@Override
		public String getRequestUniformResourceLocator(Object request) {
			return "http://localhost:8080";
		}
		
		@Override
		public String getPathIdentifierMapping(String identifier) {
			if("pathid1".equals(identifier))
				return "/path_to_id1";
			if("entityEdit".equals(identifier) || "entityDelete".equals(identifier))
				return "/entity/edit.jsf";
			if("entityConsult".equals(identifier))
				return "/entity/consult.jsf";
			//if(UniformResourceLocatorHelper.PathStringBuilder.Adapter.Default.IDENTIFIER_UNKNOWN.equals(getInput()))
				return "path_to_unknown";
			//return super.__execute__();
		}
			
	}
	
	public static class PathStringifierListener extends PathStringifier.Listener.Adapter.Default {		
		private static final long serialVersionUID = 1L;

	}
	
	public static class MapListener extends MapHelper.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		@Override
		public Object getAs(EntryComponent entryComponent, Object object) {
			if(object instanceof Entity)
				return ((Entity)object).getIdentifier();
			return super.getAs(entryComponent, object);
		}
		
		@Override
		public String getSeparatorOfValue() {
			return "&";
		}
		
		@Override
		public String getSeparatorOfKeyValue() {
			return "&";
		}
			
	}
	
	@Getter @Setter @AllArgsConstructor
	public static class Entity implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long identifier;
		private String name;
		
	}
}
