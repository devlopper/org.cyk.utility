package org.cyk.utility.common;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.MapHelper.EntryComponent;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper.PathStringifier;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper.TokenName;
import org.cyk.utility.common.userinterface.RequestHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class UniformResourceLocatorHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		ClassHelper.getInstance().map(UniformResourceLocatorHelper.Listener.class, UniformResourceLocatorHelperListener.class);
		ClassHelper.getInstance().map(RequestHelper.Listener.class, RequestHelperListener.class);
		
		PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP = new LinkedHashMap<>();
		PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP.put(".xhtml", ".jsf");
		PathStringifier.Adapter.Default.DEFAULT_UNIFORM_RESOURCE_LOCATOR_LISTENER_CLASS = UniformResourceLocatorHelperListener.class;
		
		MapHelper.Stringifier.Adapter.Default.DEFAULT_MAP_LISTENER_CLASS = MapListener.class;
		MapHelper.Stringifier.Entry.Adapter.Default.DEFAULT_MAP_LISTENER_CLASS = MapListener.class;
		
		UniformResourceLocatorHelper.TOKEN_DEFAULT_VALUE_MAP.put(TokenName.PORT, "8080");
	}
	
	@Test
	public void token(){
		assertEquals("http",UniformResourceLocatorHelper.getInstance().getToken(TokenName.SCHEME,"http://myhost:8080/mycontext/mypath?myquery#myfragment"));	
		assertEquals("myhost",UniformResourceLocatorHelper.getInstance().getToken(TokenName.HOST,"http://myhost:8080/mycontext/mypath?myquery#myfragment"));
		assertEquals("8080",UniformResourceLocatorHelper.getInstance().getToken(TokenName.PORT,"http://myhost:8080/mycontext/mypath?myquery#myfragment"));
		assertEquals("-1",UniformResourceLocatorHelper.getInstance().getToken(TokenName.PORT,"http://myhost/mycontext/mypath?myquery#myfragment"));
		assertEquals("/mycontext/mypath",UniformResourceLocatorHelper.getInstance().getToken(TokenName.PATH,"http://myhost:8080/mycontext/mypath?myquery#myfragment"));
		assertEquals("myquery",UniformResourceLocatorHelper.getInstance().getToken(TokenName.QUERY,"http://myhost:8080/mycontext/mypath?myquery#myfragment"));
		assertEquals("myfragment",UniformResourceLocatorHelper.getInstance().getToken(TokenName.FRAGMENT,"http://myhost:8080/mycontext/mypath?myquery#myfragment"));
		
		assertEquals("http",UniformResourceLocatorHelper.getInstance().getToken(TokenName.SCHEME));	
		assertEquals("localhost",UniformResourceLocatorHelper.getInstance().getToken(TokenName.HOST));
		assertEquals("8080",UniformResourceLocatorHelper.getInstance().getToken(TokenName.PORT));
	}
	
	@Test
	public void path(){
		assertEquals("/",new PathStringifier.Adapter.Default().execute());		
		assertEquals("",new PathStringifier.Adapter.Default().setAddSeparatorAtBeginning(Boolean.FALSE).execute());
		assertEquals("/mycontext",new PathStringifier.Adapter.Default().setContext("mycontext").execute());
		//assertEquals("?this is my custom path??",new UniformResourceLocatorHelper.PathStringBuilder.Adapter.Default().setOutput("?this is my custom path??").execute());
		assertEquals("/d1",new PathStringifier.Adapter.Default().addTokens("d1").execute());
		assertEquals("/d1/d2",new PathStringifier.Adapter.Default().addTokens("d1","d2").execute());
		assertEquals("/d1/d2/page.jsf",new PathStringifier.Adapter.Default().addTokens("d1","d2","page.xhtml").execute());
		
		assertEquals("/path_to_id1",new PathStringifier.Adapter.Default().setIdentifier("pathid1").execute());
		assertEquals("/mycontext/path_to_id1",new PathStringifier.Adapter.Default().setContext("mycontext").setIdentifier("pathid1").execute());
		assertEquals("/path_to_unknown",new PathStringifier.Adapter.Default().setIdentifier("pathid596").execute());
		
		assertEquals("/classa/edit.jsf",new PathStringifier.Adapter.Default().setProperty(PathStringifier.PROPERTY_NAME_ACTION, Constant.Action.CREATE)
				.setProperty(PathStringifier.PROPERTY_NAME_CLASS, ClassA.class).execute());
		assertEquals("/classa/consult.jsf",new PathStringifier.Adapter.Default().setProperty(PathStringifier.PROPERTY_NAME_ACTION, Constant.Action.CONSULT)
				.setProperty(PathStringifier.PROPERTY_NAME_CLASS, ClassA.class).execute());
		assertEquals("/classa/edit.jsf",new PathStringifier.Adapter.Default().setProperty(PathStringifier.PROPERTY_NAME_ACTION, Constant.Action.UPDATE)
				.setProperty(PathStringifier.PROPERTY_NAME_CLASS, ClassA.class).execute());
		assertEquals("/classa/edit.jsf",new PathStringifier.Adapter.Default().setProperty(PathStringifier.PROPERTY_NAME_ACTION, Constant.Action.DELETE)
				.setProperty(PathStringifier.PROPERTY_NAME_CLASS, ClassA.class).execute());
		
		assertEquals("/classa/consult.jsf",new PathStringifier.Adapter.Default().setProperty(PathStringifier.PROPERTY_NAME_ACTION, Constant.Action.CONSULT)
				.setProperty(PathStringifier.PROPERTY_NAME_INSTANCE, new ClassA(152l)).execute());
		assertEquals("/classa/edit.jsf",new PathStringifier.Adapter.Default().setProperty(PathStringifier.PROPERTY_NAME_ACTION, Constant.Action.UPDATE)
				.setProperty(PathStringifier.PROPERTY_NAME_INSTANCE, new ClassA(152l)).execute());
		assertEquals("/classa/edit.jsf",new PathStringifier.Adapter.Default().setProperty(PathStringifier.PROPERTY_NAME_ACTION, Constant.Action.DELETE)
				.setProperty(PathStringifier.PROPERTY_NAME_INSTANCE, new ClassA(152l)).execute());
	}
	
	@Test
	public void query(){
		assertEquals("p1=a",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a").execute());
		assertEquals("p1=147",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", new ClassA(147l)).execute());
		
		assertEquals("p1=a&p2=b",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b").execute());
		assertEquals("p1=a&p2=b",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b").execute());
		
		assertEquals("p1=a&p2=b",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b",null, null).execute());
		assertEquals("p1=a&p2=b",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b",null, "").execute());
		assertEquals("p1=a&p2=b",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b","", null).execute());
		
		assertEquals("p1=a&p2=b",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b","", "").execute());
		
		assertEquals("p1=a&p2=b",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b",null, "2").execute());
		assertEquals("p1=a&p2=b",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b","p3", null).execute());
		
		assertEquals("p1=a&p2=b&p3=v3",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", "a","p2", "b","p3", "v3").execute());
		
		assertEquals("p1=a&p1=b&p1=v3",new UniformResourceLocatorHelper.QueryStringifier.Adapter.Default().addKeyValue("p1", new Object[]{"a", "b", "v3"}).execute());
		
	}
	
	@Test
	public void url(){
		assertEquals("http://localhost:8080",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().execute());
		assertEquals("http://localhost:8080/",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().getPathStringifier(Boolean.TRUE)
				.getUniformResourceLocatorStringifier().execute());
		assertEquals("http://localhost:8080/mypath.extension?p1=a",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().addPathTokens("mypath.extension")
				.addQueryKeyValue("p1", "a").execute());
		assertEquals("http://localhost:8080/mypath.extension?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().addPathTokens("mypath.extension")
				.addQueryKeyValue("p1", "a","w",13).execute());
		assertEquals("http://localhost/mypath.extension?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().addPathTokens("mypath.extension")
				.addQueryKeyValue("p1", "a","w",13).setPort(80).execute());
		assertEquals("http://localhost/mypath.extension?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().addPathTokens("mypath.extension")
				.addQueryKeyValue("p1", "a","w",13).setPort(80).execute());
		assertEquals("http://localhost:8080/mypath.extension?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().addPathTokens("mypath.extension")
				.addQueryKeyValue("p1", "a","w",13).setPort(8080).execute());
		assertEquals("http://localhost:8080/mypath.extension",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().addPathTokens("mypath.extension")
				.execute());
		assertEquals("http://localhost:8080/?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default()
				.addQueryKeyValue("p1", "a","w",13).setPort(8080).execute());
		assertEquals("http://localhost:8080/mycontext/?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.addQueryKeyValue("p1", "a","w",13).setPort(8080).execute());
		assertEquals("http://localhost:8080/mycontext/mp?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.addPathTokens("mp").addQueryKeyValue("p1", "a","w",13).setPort(8080).execute());
		assertEquals("http://localhost:8080/mycontext/mp.jsf?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.addPathTokens("mp.xhtml").addQueryKeyValue("p1", "a","w",13).setPort(8080).execute());
		assertEquals("/mycontext/mp.jsf?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.addPathTokens("mp.xhtml").addQueryKeyValue("p1", "a","w",13).setPort(8080).setRelative(Boolean.TRUE).execute());
		
		assertEquals("http://localhost:8080/mycontext/path_to_id1?p1=a&w=13",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.setPathIdentifier("pathid1").addQueryKeyValue("p1", "a","w",13).setPort(8080).execute());
		
		assertEquals("http://localhost:8080/mycontext/path_to_id1?action=create&clazz=classa",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.setPathIdentifier("pathid1").addQueryParameterAction(Constant.Action.CREATE).addQueryParameterClass(ClassA.class).setPort(8080).execute());
		
		assertEquals("http://localhost:8080/mycontext/path_to_id1?action=update&identifiable=159",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.setPathIdentifier("pathid1").addQueryParameterAction(Constant.Action.UPDATE).addQueryParameterIdentifiable(new ClassA(159l)).setPort(8080).execute());
		
		assertEquals("http://localhost:8080/mycontext/classa/edit.jsf?action=create&clazz=classa",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.addQueryParameterAction(Constant.Action.CREATE).addQueryParameterClass(ClassA.class).setPort(8080).execute());
		
		assertEquals("http://localhost:8080/mycontext/classa/consult.jsf?action=consult&identifiable=1594",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.addQueryParameterAction(Constant.Action.CONSULT).addQueryParameterIdentifiable(new ClassA(1594l)).setPort(8080).execute());
		
		assertEquals("http://localhost:8080/mycontext/classa/consult.jsf?action=consult&identifiable=1594",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
				.addQueryParameterAction(Constant.Action.CONSULT).addQueryParameterIdentifiable(new ClassA(1594l)).setPort(8080).execute());
		
		//assertEquals("http://localhost:8080/mycontext/classa/consult.jsf?action=consult&identifier=1594",new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathContext("mycontext")
		//		.addQueryParameterAction(Constant.Action.CONSULT).addQueryParameterIdentifier(new ClassA(1594l)).setPort(8080).execute());
		
		assertEquals("http://localhost:8080", UniformResourceLocatorHelper.getInstance().getDefault());
	}
	
	@Test
	public void getPathIdentifier(){
		assertEquals("classAEdit", UniformResourceLocatorHelper.getInstance().getPathIdentifier(Constant.Action.CREATE, ClassA.class));
		assertEquals("classARead", UniformResourceLocatorHelper.getInstance().getPathIdentifier(Constant.Action.READ, ClassA.class));
		assertEquals("classAEdit", UniformResourceLocatorHelper.getInstance().getPathIdentifier(Constant.Action.UPDATE, ClassA.class));
		assertEquals("classAEdit", UniformResourceLocatorHelper.getInstance().getPathIdentifier(Constant.Action.DELETE, ClassA.class));
	}
	
	@Test
	public void stringify(){
		assertEquals("http://localhost:8080/classa/edit.jsf?action=create&clazz=classa", UniformResourceLocatorHelper.getInstance().stringify(Constant.Action.CREATE,ClassA.class));
		assertEquals("http://localhost:8080/classa/consult.jsf?action=read&identifiable=3", UniformResourceLocatorHelper.getInstance().stringify(Constant.Action.READ,(Object)new ClassA(3l)));
	}
	
	/**/
	
	@Getter @Setter
	public static class ClassA implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long identifier;
		
		public ClassA(Long identifier) {
			this.identifier = identifier;
		}
		
	}
	
	public static class PathStringifierListener extends PathStringifier.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

	}
	
	public static class MapListener extends MapHelper.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		@Override
		public Object getAs(EntryComponent entryComponent, Object object) {
			if(object instanceof ClassA)
				return ((ClassA)object).getIdentifier();
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
	
	public static class UniformResourceLocatorHelperListener extends UniformResourceLocatorHelper.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getPathIdentifierMapping(String identifier) {
			if("pathid1".equals(identifier))
				return "/path_to_id1";
			if("classAEdit".equals(identifier) || "classADelete".equals(identifier))
				return "/classa/edit.jsf";
			if("classAConsult".equals(identifier))
				return "/classa/consult.jsf";
			if("classARead".equals(identifier))
				return "/classa/consult.jsf";
			//if(UniformResourceLocatorHelper.PathStringBuilder.Adapter.Default.IDENTIFIER_UNKNOWN.equals(getInput()))
				return "path_to_unknown";
			//return super.__execute__();
		}
			
	}
	
	public static class RequestHelperListener extends RequestHelper.Listener.Adapter.Default {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Object get() {
			return new Object();
		}
		
		@Override
		public String getUniformResourceLocator(Object request) {
			return "http://localhost:8080/mycontext_and_path";
		}
	}
}
