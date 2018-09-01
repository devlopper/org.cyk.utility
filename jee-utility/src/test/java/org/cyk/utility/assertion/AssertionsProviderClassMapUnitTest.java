package org.cyk.utility.assertion;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class AssertionsProviderClassMapUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void mapC01() {
		assertionHelper.assertNull(__inject__(AssertionsProviderClassMap.class).get(C01.class));
		__inject__(AssertionsProviderClassMap.class).set(C01.class, C01Assertions.class);
		Class<AssertionsProvider> assertionsProviderClass = __inject__(AssertionsProviderClassMap.class).get(C01.class);
		assertionHelper.assertEquals(C01Assertions.class,assertionsProviderClass);
		AssertionsProvider assertionsProvider = __inject__(assertionsProviderClass);
		assertionHelper.assertNotNull(assertionsProvider);
		Collection<Assertion> assertions = assertionsProvider.execute().getOutput();
		assertionHelper.assertNull(assertions);
	}
	
	@Test
	public void mapC02() {
		assertionHelper.assertNull(__inject__(AssertionsProviderClassMap.class).get(C02.class));
		__inject__(AssertionsProviderClassMap.class).set(C02.class, C02Assertions.class);
		Class<AssertionsProvider> assertionsProviderClass = __inject__(AssertionsProviderClassMap.class).get(C02.class);
		assertionHelper.assertEquals(C02Assertions.class,assertionsProviderClass);
		AssertionsProvider assertionsProvider = __inject__(assertionsProviderClass);
		assertionHelper.assertNotNull(assertionsProvider);
		Collection<Assertion> assertions = assertionsProvider.execute().getOutput();
		assertionHelper.assertNull(assertions);
		
		assertionsProvider = __inject__(assertionsProviderClass);
		assertionHelper.assertNotNull(assertionsProvider);
		assertions = assertionsProvider.setFilter("create").execute().getOutput();
		assertionHelper.assertNotNull(assertions);
		assertionHelper.assertEquals(1, assertions.size());
		
		assertionsProvider = __inject__(assertionsProviderClass);
		assertionHelper.assertNotNull(assertionsProvider);
		assertions = assertionsProvider.setFilter("update").execute().getOutput();
		assertionHelper.assertNotNull(assertions);
		assertionHelper.assertEquals(2, assertions.size());
	}
	
	/**/
	
	public static class C01 {}
	
	public static class C02 {}
	
	public static class C03 {}
	
	public static class C01Assertions extends AbstractAssertionsProviderImpl {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Collection<Assertion> __execute__() throws Exception {
			return null;
		}
		
	}
	
	public static class C02Assertions extends AbstractAssertionsProviderImpl {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Collection<Assertion> __execute__() throws Exception {
			Collection<Assertion> assertions = null;
			if(getFilter()!=null && getFilter().equals("create")) {
				assertions = new ArrayList<>();
				assertions.add(__inject__(Assertion.class));
			}else if(getFilter()!=null && getFilter().equals("update")) {
				assertions = new ArrayList<>();
				assertions.add(__inject__(Assertion.class));
				assertions.add(__inject__(Assertion.class));
			}
			return assertions;
		}
		
	}
}
