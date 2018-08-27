package org.cyk.utility.instance;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class InstanceBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void build_isNotNull(){
		assertionHelper.assertNotNull(__inject__(InstanceBuilder.class).setClazz(MyClass02.class).setFieldsValuesObject(new MyClass01()).execute().getOutput());
	}
	
	@Test
	public void build_isNull(){
		assertionHelper.assertNull(__inject__(InstanceBuilder.class).setClazz(MyClass02.class).setFieldsValuesObject(null).execute().getOutput());
	}
	
	/**/
	
	public static class MyClass01 {
		
	}
	
	public static class MyClass02 {
		
	}
}
