package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;
import java.net.URI;

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

public class ProxyClassUniformResourceIdentifierGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(FunctionRunnableMap.class).set(RequestGetterImpl.class, RequestGetterFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(ProxyClassUniformResourceIdentifierStringProviderImpl.class, ProxyClassUniformResourceIdentifierStringProviderFunctionRunnableImpl.class,2);
	}
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		System.setProperty("org.cyk.utility.client.controller.proxy.ProxyClassUniformResourceIdentifierGetterUnitTest$MyClass02.server.uri", "http:localhost:123");
	}
	
	@Test
	public void isMyClass01DerivedFromRequest() {
		assertionHelper.assertEquals(URI.create("http://localhost:80/server/"),__inject__(ProxyClassUniformResourceIdentifierGetter.class).setClazz(MyClass01.class).execute().getOutput());
	}
	
	@Test
	public void isMyClass02DerivedFromProperty() {
		assertionHelper.assertEquals(URI.create("http:localhost:123"),__inject__(ProxyClassUniformResourceIdentifierGetter.class).setClazz(MyClass02.class).execute().getOutput());
	}
	
	@Test
	public void isMyClass03DerivedFromApplication() {
		assertionHelper.assertEquals(URI.create("http://localhost:8888/my-server"),__inject__(ProxyClassUniformResourceIdentifierGetter.class).setClazz(MyClass03.class).execute().getOutput());
	}
	
	/**/
	
	private static class MyClass01 implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	private static class MyClass02 implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	private static class MyClass03 implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
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
	
	public static class ProxyClassUniformResourceIdentifierStringProviderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ProxyClassUniformResourceIdentifierStringProvider> {
		private static final long serialVersionUID = 1L;
		
		public ProxyClassUniformResourceIdentifierStringProviderFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(MyClass03.class.equals(getFunction().getClazz()))
						setOutput("http://localhost:8888/my-server");
				}
			});
		}
		
	}
}
