package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;
import java.net.URI;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ProxyClassUniformResourceIdentifierGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		System.setProperty("org.cyk.utility.client.controller.proxy.ProxyClassUniformResourceIdentifierGetterUnitTest$MyClass02.server.uri", "MyClass02Server");
	}
	
	@Test
	public void isMyClass01UriNull() {
		assertionHelper.assertNull(__inject__(ProxyClassUniformResourceIdentifierGetter.class).setClazz(MyClass01.class).execute().getOutput());
	}
	
	//@Test
	public void isMyClass02UriNotNull() {
		assertionHelper.assertNotNull(__inject__(ProxyClassUniformResourceIdentifierGetter.class).setClazz(MyClass02.class).execute().getOutput());
		assertionHelper.assertEquals(URI.create("MyClass02Server"),__inject__(ProxyClassUniformResourceIdentifierGetter.class).setClazz(MyClass02.class).execute().getOutput());
	}
	
	/**/
	
	private static class MyClass01 implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	private static class MyClass02 implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
