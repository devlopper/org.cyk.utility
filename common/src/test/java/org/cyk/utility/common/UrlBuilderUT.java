package org.cyk.utility.common;

import org.cyk.utility.common.builder.UrlBuilder;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class UrlBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void query(){
		assertEquals("p1=a",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").build());
		assertEquals("p1=a&p2=b",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").build());
		assertEquals("p1=a&p2=b",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").build());
		
		assertEquals("p1=a&p2=b",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, null).build());
		assertEquals("p1=a&p2=b",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, "").build());
		assertEquals("p1=a&p2=b",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("", null).build());
		
		assertEquals("p1=a&p2=b",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("", "").build());
		
		assertEquals("p1=a&p2=b",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter(null, "2").build());
		assertEquals("p1=a&p2=b",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("p3", null).build());
		
		assertEquals("p1=a&p2=b&p3=v3",new UrlBuilder.QueryStringBuilder().addParameter("p1", "a").addParameter("p2", "b").addParameter("p3", "v3").build());
	
	}
	
	@Test
	public void url(){
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("mypath.extension").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/mypath.extension?p1=a",urlBuilder.build());
		
		urlBuilder = new UrlBuilder();
		urlBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("mypath.extension").getQueryStringBuilder().addParameter("p1", "a").addParameter("w", "13");
		assertEquals("http://localhost:8080/mypath.extension?p1=a&w=13",urlBuilder.build());
		
		urlBuilder = new UrlBuilder();
		urlBuilder.setScheme("http").setHost("localhost").setPath("mypath.extension").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost/mypath.extension?p1=a",urlBuilder.build());
		
		urlBuilder = new UrlBuilder();
		urlBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("mypath.extension").getQueryStringBuilder();
		assertEquals("http://localhost:8080/mypath.extension",urlBuilder.build());
		
		urlBuilder = new UrlBuilder();
		urlBuilder.setScheme("http").setHost("localhost").getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost/?p1=a",urlBuilder.build());
		
		urlBuilder = new UrlBuilder();
		urlBuilder.setScheme("http").setHost("localhost").setPort(8080).getQueryStringBuilder().addParameter("p1", "a");
		assertEquals("http://localhost:8080/?p1=a",urlBuilder.build());
	}
}
