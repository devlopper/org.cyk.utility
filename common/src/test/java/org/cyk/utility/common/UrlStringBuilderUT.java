package org.cyk.utility.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.common.builder.NameValueStringBuilder;
import org.cyk.utility.common.builder.UrlStringBuilder;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class UrlStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		NameValueStringBuilder.Listener.COLLECTION.add(new NameValueStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object getValueToProcessed(Object value) {
				if(value instanceof ClassA)
					return ((ClassA)value).getIdentifier();
				return super.getValueToProcessed(value);
			}
		});
		
		UrlStringBuilder.PathStringBuilder.PATH_NOT_FOUND_IDENTIFIER = "pathnotfound";
		UrlStringBuilder.PathStringBuilder.Listener.COLLECTION.add(new UrlStringBuilder.PathStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 7112717654641763443L;
			@Override
			public String getIdentifierMapping(String identifier) {
				if("pathid1".equals(identifier))
					return "/path_to_id1";
				if(UrlStringBuilder.PathStringBuilder.PATH_NOT_FOUND_IDENTIFIER.equals(identifier))
					return "path_to_unknown";
				return super.getIdentifierMapping(identifier);
			}
			
			@Override
			public Map<String, String> getTokenReplacementMap() {
				Map<String, String> map = new HashMap<>();
				map.put(".xhtml", ".jsf");
				return map;
			}
		});
		
		UrlStringBuilder.PathStringBuilder.IdentifierBuilder.Listener.COLLECTION.add(new UrlStringBuilder.PathStringBuilder.IdentifierBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 7112717654641763443L;
			@Override
			public String get(Object action, Object subject) {
				if("myaction".equals(action))
					if(subject instanceof ClassA)
						return "classa.myaction";
					else
						return "dynamicaction";
				
				return super.get(action, subject);
			}
		});
	}
	
	@Test
	public void path(){
		assertEquals("/",new UrlStringBuilder.PathStringBuilder().build());
		assertEquals("",new UrlStringBuilder.PathStringBuilder().setAddSeparatorAtBeginning(Boolean.FALSE).build());
		assertEquals("/mycontext",new UrlStringBuilder.PathStringBuilder().setContext("mycontext").build());
		assertEquals("?this is my custom path??",new UrlStringBuilder.PathStringBuilder().setInstance("?this is my custom path??").build());
		assertEquals("/d1",new UrlStringBuilder.PathStringBuilder().addTokens("d1").build());
		assertEquals("/d1/d2",new UrlStringBuilder.PathStringBuilder().addTokens("d1","d2").build());
		assertEquals("/d1/d2/page.jsf",new UrlStringBuilder.PathStringBuilder().addTokens("d1","d2","page.xhtml").build());
		
		assertEquals("/path_to_id1",new UrlStringBuilder.PathStringBuilder().setIdentifier("pathid1").build());
		assertEquals("/mycontext/path_to_id1",new UrlStringBuilder.PathStringBuilder().setContext("mycontext").setIdentifier("pathid1").build());
		assertEquals("/path_to_unknown",new UrlStringBuilder.PathStringBuilder().setIdentifier("pathid596").build());
		
		/*Identifier*/
		assertEquals(null,new UrlStringBuilder.PathStringBuilder.IdentifierBuilder().build());
		assertEquals("classa.myaction",new UrlStringBuilder.PathStringBuilder.IdentifierBuilder()
				.setAction("myaction").setSubject(new ClassA(15l)).build());
		
		
	}
	
	@Test
	public void query(){
		assertEquals("p1=a",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").build());
		assertEquals("p1=147",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", new ClassA(147l)).build());
		
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").build());
		
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, null).build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, "").build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("", null).build());
		
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("", "").build());
		
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, "2").build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("p3", null).build());
		
		assertEquals("p1=a&p2=b&p3=v3",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("p3", "v3").build());
		
		assertEquals("p1=a&p1=b&p1=v3",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p1", "b").addParameter("p1", "v3").build());
		
	}
	
	@Test
	public void url(){
		UrlStringBuilder urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).getPathStringBuilder().addTokens("mypath.extension").getUrlStringBuilder()
			.getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/mypath.extension?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).getPathStringBuilder().addTokens("mypath.extension").getUrlStringBuilder()
			.getQueryStringBuilder().addParameter("p1", "a").addParameter("w", "13");
		assertEquals("http://localhost:8080/mypath.extension?p1=a&w=13",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").getPathStringBuilder().addTokens("mypath.extension").getUrlStringBuilder()
			.getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost/mypath.extension?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).getPathStringBuilder().addTokens("mypath.extension").getUrlStringBuilder()
			.getQueryStringBuilder();
		assertEquals("http://localhost:8080/mypath.extension",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost/?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).getQueryStringBuilder().addParameter("p1", "a").addParameter("p1", "b");
		assertEquals("http://localhost:8080/?p1=a&p1=b",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).getPathStringBuilder().setContext("mycontext").addTokens("mp")
			.getUrlStringBuilder().getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/mycontext/mp?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).getPathStringBuilder().setContext("mycontext").addTokens("mp.xhtml")
			.getUrlStringBuilder().getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/mycontext/mp.jsf?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).getPathStringBuilder().setContext("mycontext").addTokens("mp.xhtml")
			.getUrlStringBuilder().setRelative(Boolean.TRUE).getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("/mycontext/mp.jsf?p1=a",urlStringBuilder.build());
		
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
}
