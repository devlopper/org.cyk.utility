package org.cyk.utility.clazz;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ClassInstancesRuntimeUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getC1() {
		assertionHelper.assertEquals(C1.class, __inject__(ClassInstancesRuntime.class).get(C1.class).getClazz());
	}
	
	@Test
	public void getC1_many_call_add_class_only_once_to_runtime_collection() {
		assertionHelper.setIsLogAssertionEnable(Boolean.TRUE);
		if(__inject__(ClassInstancesRuntime.class).getInstances()!=null)
			__inject__(ClassInstancesRuntime.class).getInstances().removeAll();
		for(Integer index = 0; index < 5; index = index + 1) {
			assertionHelper.assertEquals(C1.class, __inject__(ClassInstancesRuntime.class).get(C1.class).getClazz());
			if(index == 0)
				assertionHelper.assertStartsWithLastLogEventMessage("class <<class org.cyk.utility.clazz.ClassInstancesRuntimeUnitTest$C1>> added to runtime collection");
			else
				assertionHelper.assertStartsWithLastLogEventMessage("class <<class org.cyk.utility.clazz.ClassInstancesRuntimeUnitTest$C1>> fetched from runtime collection");
		}
	}
	
	/**/
	
	public static class C1 {
		
	}
}
