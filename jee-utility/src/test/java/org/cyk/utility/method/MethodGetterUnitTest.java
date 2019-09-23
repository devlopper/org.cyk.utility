package org.cyk.utility.method;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringLocation;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class MethodGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_setter_method_f1(){
		assertionHelper.assertEquals(MethodUtils.getMatchingMethod(MyClass.class, "setF1", String.class),CollectionHelper.getFirst(
				__inject__(MethodGetter.class).setClazz(MyClass.class).setToken("setF1").setTokenLocation(StringLocation.EXAT).execute().getOutput()));
	}
	
	@Test
	public void get_setter_method_f2(){
		assertionHelper.assertEquals(MethodUtils.getMatchingMethod(MyClass.class, "setF2", String.class),CollectionHelper.getFirst(
				__inject__(MethodGetter.class).setClazz(MyClass.class).setToken("setF2").setTokenLocation(StringLocation.EXAT).execute().getOutput()));
	}
	
	public static class MyClass {
		
		public String getF1() {
			return null;
		}
		
		public void setF1(String value) {
			
		}
		
		public String getF2() {
			return null;
		}
		
		public MyClass setF2(Object value) {
			return null;
		}
	}
	
}
