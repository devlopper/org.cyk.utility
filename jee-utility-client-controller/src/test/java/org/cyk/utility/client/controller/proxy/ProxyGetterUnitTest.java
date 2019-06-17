package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;

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

public class ProxyGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(FunctionRunnableMap.class).set(RequestGetterImpl.class, RequestGetterFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(ProxyGetterImpl.class, ProxyGetterFunctionRunnableImpl.class,1);
	}
	
	@Test
	public void get() {
		Object proxy = __inject__(ProxyGetter.class).setClazz(MyObjectClient01.class).execute().getOutput();
		assertionHelper.assertTrue(proxy instanceof MyObjectProxy01);
		proxy = __inject__(ProxyGetter.class).setClazz(MyObjectClient02.class).execute().getOutput();
		assertionHelper.assertTrue(proxy instanceof MyObjectProxy02);
		
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
	
	public static class ProxyGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ProxyGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public ProxyGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					Class<?> aClass = getFunction().getClazz();
					ProxyClassUniformResourceIdentifierStringBuilder classUniformResourceIdentifierStringBuilder = getFunction().getClassUniformResourceIdentifierString();
					if(classUniformResourceIdentifierStringBuilder == null)
						classUniformResourceIdentifierStringBuilder = __inject__(ProxyClassUniformResourceIdentifierStringBuilder.class).setClazz(aClass);
					String uri = classUniformResourceIdentifierStringBuilder.execute().getOutput();
					Object proxy = null;
					if("http://localhost:80/server/".equals(uri)) {
						if(MyObjectClient01.class.equals(aClass))
							proxy = new MyObjectProxy01();
						else if(MyObjectClient02.class.equals(aClass))
							proxy = new MyObjectProxy02();
					}
					setOutput(proxy);
				}
			});
		}
		
	}
	
	public static class MyObjectClient01 {
		
	}
	
	public static class MyObjectProxy01 {
		
	}
	
	public static class MyObjectClient02 {
		
	}
	
	public static class MyObjectProxy02 {
		
	}
	
}
