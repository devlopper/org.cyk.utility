package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.request.RequestProperty;
import org.cyk.utility.request.RequestPropertyValueGetter;
import org.cyk.utility.request.RequestPropertyValueGetterImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public class UniformResourceIdentifierStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class);
	}
	
	@Test
	public void format_arguments_http_localhost_8080(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/", builder.setScheme("http").setHost("localhost").setPort(8080).execute().getOutput());
	}
	
	@Test
	public void string_http_localhost_8080(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/", builder.setString("http://localhost:8080/").execute().getOutput());
	}
	
	@Test
	public void string_http_localhost(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost/", builder.setString("http://localhost/").execute().getOutput());
	}
	
	@Test
	public void string_http_localhost_query_a_equal_b(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost/?a=b", builder.setString("http://localhost/?a=b").execute().getOutput());
	}
	
	@Test
	public void string_https_www_google_com(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("https://www.google.com/", builder.setString("https://www.google.com/").execute().getOutput());
	}
	
	@Test
	public void format_arguments_http_localhost_8080_slash(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/", builder.setScheme("http").setHost("localhost").setPort(8080).setPath("/").execute().getOutput());
	}
	
	@Test
	public void format_arguments_http_localhost_8080_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/folder", builder.setScheme("http").setHost("localhost").setPort(8080).setPath("folder").execute().getOutput());
	}
	
	@Test
	public void format_arguments_http_localhost_8080_slash_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/folder", builder.setScheme("http").setHost("localhost").setPort(8080).setPath("/folder").execute().getOutput());
	}
	
	@Test
	public void format_arguments_http_localhost_8080_app_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/app/folder", builder.setScheme("http").setHost("localhost").setPort(8080).setContext("app").setPath("folder").execute().getOutput());
	}
	
	@Test
	public void format_arguments_http_localhost_8080_app_slash_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/app/folder", builder.setScheme("http").setHost("localhost").setPort(8080).setContext("app").setPath("/folder").execute().getOutput());
	}
	
	@Test
	public void format_arguments_http_localhost_8080_slash_app_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/app/folder", builder.setScheme("http").setHost("localhost").setPort(8080).setContext("/app").setPath("folder").execute().getOutput());
	}
	
	@Test
	public void format_arguments_http_localhost_8080_slash_app_slash_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/app/folder", builder.setScheme("http").setHost("localhost").setPort(8080).setContext("/app").setPath("/folder").execute().getOutput());
	}
	
	@Test
	public void request_http_localhost_8080_slash_app_slash_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/app/folder", builder.setRequest(new Request("http","localhost","8080","app")).setPath("/folder").execute().getOutput());
	}
	
	@Test
	public void request_ftp_wwwCykDotCom_app_slash_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("ftp://www.cyk.com:80/app/folder", builder.setRequest(new Request("ftp","www.cyk.com","80","app")).setPath("/folder").execute().getOutput());
	}
	
	@Test
	public void string_ftp_wwwCykDotCom_app_slash_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("ftp://www.cyk.com:80/app/folder", builder.setString("ftp://www.cyk.com:80/app/folder").execute().getOutput());
	}
	
	@Test
	public void string_ftp_wwwCykDotCom_app_slash_folder_query_p1V1(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("ftp://www.cyk.com:80/app/folder?p1=v1", builder.setString("ftp://www.cyk.com:80/app/folder").setParameters("p1","v1").execute().getOutput());
	}
	
	/**/
	
	@Getter @Setter @Accessors @AllArgsConstructor @NoArgsConstructor
	public static class Request {
		private String scheme,host,port,context;
	}
	
	public static class RequestPropertyValueGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<RequestPropertyValueGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public RequestPropertyValueGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					Request request = (Request) getFunction().getRequest();
					if(request!=null) {
						RequestProperty property = getFunction().getProperty();
						if(RequestProperty.SCHEME.equals(property))
							setOutput(request.getScheme());
						else if(RequestProperty.HOST.equals(property))
							setOutput(request.getHost());
						if(RequestProperty.PORT.equals(property))
							setOutput(request.getPort());
						if(RequestProperty.CONTEXT.equals(property))
							setOutput(request.getContext());
					}
				}
			});
		}
		
	}
}
