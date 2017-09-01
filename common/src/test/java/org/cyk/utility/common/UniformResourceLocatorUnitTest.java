package org.cyk.utility.common;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class UniformResourceLocatorUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		UniformResourceLocatorHelper.Stringifier.Adapter.Default.DEFAULT_SCHEME = "http";
		UniformResourceLocatorHelper.Stringifier.Adapter.Default.DEFAULT_HOST = "localhost";
		UniformResourceLocatorHelper.Stringifier.Adapter.Default.DEFAULT_PORT = 8080;
		
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP = new LinkedHashMap<>();
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP.put(".xhtml", ".jsf");
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_IDENTIFIER_MAPPING_CLASS = IdentifierMapping.class;
		
		MapHelper.Stringifier.Entry.Adapter.Default.GET_VALUE_CLASS = Mapping.class;
	}
	
	@Test
	public void path(){
		assertEquals("/",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().execute());		
		assertEquals("",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().setAddSeparatorAtBeginning(Boolean.FALSE).execute());
		assertEquals("/mycontext",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().setContext("mycontext").execute());
		//assertEquals("?this is my custom path??",new UniformResourceLocatorHelper.PathStringBuilder.Adapter.Default().setOutput("?this is my custom path??").execute());
		assertEquals("/d1",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().addTokens("d1").execute());
		assertEquals("/d1/d2",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().addTokens("d1","d2").execute());
		assertEquals("/d1/d2/page.jsf",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().addTokens("d1","d2","page.xhtml").execute());
		
		assertEquals("/path_to_id1",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().setIdentifier("pathid1").execute());
		assertEquals("/mycontext/path_to_id1",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().setContext("mycontext").setIdentifier("pathid1").execute());
		assertEquals("/path_to_unknown",new UniformResourceLocatorHelper.PathStringifier.Adapter.Default().setIdentifier("pathid596").execute());
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
				.addQueryKeyValue("p1", "a","w",13).setPort(null).execute());
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
	
	public static class IdentifierMapping extends StringHelper.Mapping.Adapter.Default {
		
		@Override
		protected String __execute__() {
			if("pathid1".equals(getInput()))
				return "/path_to_id1";
			//if(UniformResourceLocatorHelper.PathStringBuilder.Adapter.Default.IDENTIFIER_UNKNOWN.equals(getInput()))
				return "path_to_unknown";
			//return super.__execute__();
		}
		
	}
	
	public static class Mapping extends InstanceHelper.Mapping.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected Object __execute__() {
			if(getInput() instanceof ClassA)
				return ((ClassA)getInput()).getIdentifier();
			return super.__execute__();
		}
		
	}
}
