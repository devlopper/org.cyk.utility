package org.cyk.utility.common;

import org.cyk.utility.common.builder.UrlStringBuilder;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class UrlStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void query(){
		/*assertEquals("p1=a",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").build());
		
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, null).build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, "").build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("", null).build());
		
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("", "").build());
		
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, "2").build());
		assertEquals("p1=a&p2=b",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("p3", null).build());
		
		assertEquals("p1=a&p2=b&p3=v3",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("p3", "v3").build());
		*/
		assertEquals("p1=a&p1=b&p1=v3",new UrlStringBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p1", "b").addParameter("p1", "v3").build());
	}
	
	//@Test
	public void url(){
		UrlStringBuilder urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("mypath.extension").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/mypath.extension?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("mypath.extension").getQueryStringBuilder().addParameter("p1", "a").addParameter("w", "13");
		assertEquals("http://localhost:8080/mypath.extension?p1=a&w=13",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPath("mypath.extension").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost/mypath.extension?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("mypath.extension").getQueryStringBuilder();
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
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).setContext("mycontext").setPath("mp").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/mycontext/mp?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).setContext("mycontext").setPath("mp.xhtml").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/mycontext/mp.xhtml?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).setContext("mycontext").setPath("mp.xhtml")
			.addPathTokenReplacement(".xhtml", ".jsf").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/mycontext/mp.jsf?p1=a",urlStringBuilder.build());
		
		urlStringBuilder = new UrlStringBuilder();
		urlStringBuilder.setScheme("http").setHost("localhost").setPort(8080).setContext("mycontext").setPath("mp.xhtml")
			.addPathTokenReplacement(".xhtml", ".jsf").setRelative(Boolean.TRUE).getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("/mycontext/mp.jsf?p1=a",urlStringBuilder.build());
	}
}
