package org.cyk.utility.client.controller.proxy;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.request.RequestGetter;
import org.cyk.utility.request.RequestGetterImpl;
import org.cyk.utility.request.RequestProperty;
import org.cyk.utility.request.RequestPropertyValueGetter;
import org.cyk.utility.request.RequestPropertyValueGetterImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ProxyClassUniformResourceIdentifierStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(FunctionRunnableMap.class).set(RequestGetterImpl.class, RequestGetterFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class,1);
	}
	
	@Test
	public void build() {
		assertionHelper.assertEquals("http://localhost:80/server/",__inject__(ProxyClassUniformResourceIdentifierStringBuilder.class).execute().getOutput());
		assertionHelper.assertEquals("http://localhost:80/server/",__inject__(ProxyClassUniformResourceIdentifierStringBuilder.class)
				.setRequest(new Request()).execute().getOutput());
		assertionHelper.assertEquals("http://localhost:8080/server/",__inject__(ProxyClassUniformResourceIdentifierStringBuilder.class)
				.setRequest(new Request().setPort(8080)).execute().getOutput());
		assertionHelper.assertEquals("http://localhost:8080/actor/server/",__inject__(ProxyClassUniformResourceIdentifierStringBuilder.class)
				.setRequest(new Request().setPort(8080).setContext("actor/client")).execute().getOutput());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	private static class Request {
		
		private Object scheme = "http",host="localhost",port="80",context="client";
		
	}
	
	public static class RequestGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<RequestGetter> {
		private static final long serialVersionUID = 1L;
		
		public RequestGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					setOutput(new Request().setScheme("http").setHost("localhost").setPort(80).setContext("client"));
				}
			});
		}
		
	}
	
	public static class RequestPropertyValueGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<RequestPropertyValueGetter> {
		private static final long serialVersionUID = 1L;
		
		public RequestPropertyValueGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					Request request = (Request) getFunction().getRequest();
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
			});
		}
		
	}
	
}
