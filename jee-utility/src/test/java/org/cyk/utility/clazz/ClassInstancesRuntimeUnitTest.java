package org.cyk.utility.clazz;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Deprecated @Disabled
public class ClassInstancesRuntimeUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		if(__inject__(ClassInstancesRuntime.class).getInstances()!=null)
			__inject__(ClassInstancesRuntime.class).getInstances().removeAll();
	}
	
	@Test
	public void getC1() {
		assertionHelper.assertEquals(C1.class, __inject__(ClassInstancesRuntime.class).get(C1.class).getClazz());
	}
	
	@Test
	public void getC1_many_call_add_class_only_once_to_runtime_collection() {
		for(Integer index = 0; index < 5; index = index + 1) {
			Integer count = CollectionHelper.getSize(__inject__(ClassInstancesRuntime.class).getInstances());
			if(index == 0)
				assertionHelper.assertEqualsNumber(0, count);
			else
				assertionHelper.assertNotEqualsNumber(0, count);
			assertionHelper.assertEquals(C1.class, __inject__(ClassInstancesRuntime.class).get(C1.class).getClazz());
			assertionHelper.assertEqualsNumber(1, CollectionHelper.getSize(__inject__(ClassInstancesRuntime.class).getInstances()));
		}
	}
	
	/**/
	
	public static class C1 {
		
	}
}
