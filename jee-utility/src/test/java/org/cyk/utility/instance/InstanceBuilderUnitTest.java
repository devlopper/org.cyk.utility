package org.cyk.utility.instance;

import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.field.FieldValueCopyFunctionRunnableImpl;
import org.cyk.utility.field.FieldValueCopyImpl;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class InstanceBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void build_isNotNull(){
		assertionHelper.assertNotNull(__inject__(InstanceBuilder.class).setClazz(MyClass02.class).setFieldsValuesObject(new MyClass01()).execute().getOutput());
	}
	
	@Test
	public void build_isNull(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		assertionHelper.assertNull(__inject__(InstanceBuilder.class).setClazz(MyClass02.class).setFieldsValuesObject(null).execute().getOutput());
	}
	
	@Test
	public void fieldWithSameName(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyClass01 o1 = new MyClass01().setCode("c01");
		MyClass02 o2 = (MyClass02) __inject__(InstanceBuilder.class).setClazz(MyClass02.class).setFieldsValuesObject(o1).execute().getOutput();
		assertionHelper.assertEquals("c01", o2.getCode());
	}
	
	//@Test
	public void fieldWithDifferentName(){
		MyClass01 o1 = new MyClass01().setName("n01");
		MyClass02 o2 = (MyClass02) __inject__(InstanceBuilder.class).setClazz(MyClass02.class).setFieldsValuesObject(o1).execute().getOutput();
		assertionHelper.assertEquals("n01", o2.getMyName());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01 {
		
		private String code;
		private String name;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass02 {
		
		private String code;
		private String myName;
		
	}
}
