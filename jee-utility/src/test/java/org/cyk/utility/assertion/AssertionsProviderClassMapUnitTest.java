package org.cyk.utility.assertion;

import java.util.Collection;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class AssertionsProviderClassMapUnitTest extends AbstractWeldUnitTest {
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
	
	@Test
	public void mapC03() {
		assertionHelper.assertNull(__inject__(AssertionsProviderClassMap.class).get(C03.class));
		__inject__(AssertionsProviderClassMap.class).set(C03.class, C03Assertions.class);
		Class<AssertionsProvider> assertionsProviderClass = __inject__(AssertionsProviderClassMap.class).get(C03.class);
		assertionHelper.assertEquals(C03Assertions.class,assertionsProviderClass);
		AssertionsProvider assertionsProvider = __inject__(assertionsProviderClass);
		assertionHelper.assertNotNull(assertionsProvider);
		assertionsProvider =  __inject__(AssertionsProviderClassMap.class).inject(C03.class);
		assertionHelper.assertNotNull(assertionsProvider);
		Collection<Assertion> assertions = assertionsProvider.execute().getOutput();
		assertionHelper.assertNotNull(assertions);
		assertionHelper.assertEquals(5, assertions.size());
		
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
	
	public static class C01Assertions extends AbstractAssertionsProviderForImpl<C01> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void ____execute____(Function<?, ?> function,Object filter,C01 c01) {
			
		}
		
	}
	
	public static class C02Assertions extends AbstractAssertionsProviderForImpl<C02> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void ____execute____(Function<?, ?> function,Object filter,C02 c02) {
			if(filter!=null && getFilter().equals("create")) {
				__add__(__inject__(Assertion.class));
			}else if(filter!=null && getFilter().equals("update")) {
				__add__(__inject__(Assertion.class),__inject__(Assertion.class));
			}
		}
	}
	
	public static class C03Assertions extends AbstractAssertionsProviderForImpl<C03> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void ____execute____(Function<?, ?> function,Object filter,C03 c03) {
			if(filter!=null && getFilter().equals("create")) {
				__add__(__inject__(Assertion.class));
			}else if(filter!=null && getFilter().equals("update")) {
				__add__(__inject__(Assertion.class),__inject__(Assertion.class));
			}else{
				__add__(__inject__(Assertion.class),__inject__(Assertion.class),__inject__(Assertion.class),__inject__(Assertion.class),__inject__(Assertion.class));
			}
		}
	}
}
