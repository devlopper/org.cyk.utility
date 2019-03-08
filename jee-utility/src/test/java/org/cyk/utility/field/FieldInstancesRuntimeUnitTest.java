package org.cyk.utility.field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class FieldInstancesRuntimeUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Override
	public void listenBefore() {
		super.listenBefore();
		if(__inject__(FieldInstancesRuntime.class).getInstances()!=null)
			__inject__(FieldInstancesRuntime.class).getInstances().removeAll();
	}
	
	@Test
	public void getC1_f01_from_C01() {
		assertionHelper.assertEquals(FieldUtils.getDeclaredField(C1.class, "f01", Boolean.TRUE), __inject__(FieldInstancesRuntime.class).get(C1.class,"f01").getField());
	}
	
	@Test
	public void getC1_f01_many_call_add_field_only_once_to_runtime_collection() {
		assertionHelper.setIsLogAssertionEnable(Boolean.TRUE);
		for(Integer index = 0; index < 5; index = index + 1) {
			assertionHelper.assertEquals(FieldUtils.getDeclaredField(C1.class, "f01", Boolean.TRUE), __inject__(FieldInstancesRuntime.class).get(C1.class,"f01").getField());
			if(index == 0)
				assertionHelper.assertStartsWithLastLogEventMessage("field <<class org.cyk.utility.field.FieldInstancesRuntimeUnitTest$C1.f01>> added to runtime collection");
			else
				assertionHelper.assertStartsWithLastLogEventMessage("field <<class org.cyk.utility.field.FieldInstancesRuntimeUnitTest$C1.f01>> fetched from runtime collection");
		}
	}
	
	@Test
	public void getC1_sub1_f01_from_C01() {
		assertionHelper.assertEquals(FieldUtils.getDeclaredField(C1_Sub1.class, "f01", Boolean.TRUE), __inject__(FieldInstancesRuntime.class).get(C1.class,"sub1.f01").getField());
	}
	
	@Test
	public void getC1_sub1_f01_from_C01_many_call_add_field_only_once_to_runtime_collection() {
		assertionHelper.setIsLogAssertionEnable(Boolean.TRUE);
		for(Integer index = 0; index < 5; index = index + 1) {
			assertionHelper.assertEquals(FieldUtils.getDeclaredField(C1_Sub1.class, "f01", Boolean.TRUE), __inject__(FieldInstancesRuntime.class).get(C1.class,"sub1.f01").getField());
			if(index == 0)
				assertionHelper.assertStartsWithLastLogEventMessage("field <<class org.cyk.utility.field.FieldInstancesRuntimeUnitTest$C1.sub1.f01>> added to runtime collection");
			else
				assertionHelper.assertStartsWithLastLogEventMessage("field <<class org.cyk.utility.field.FieldInstancesRuntimeUnitTest$C1.sub1.f01>> fetched from runtime collection");
		}
	}
	
	@Test
	public void getC1_sub1_sub1_f01_from_C01() {
		assertionHelper.assertEquals(FieldUtils.getDeclaredField(C1_Sub1_Sub1.class, "f01", Boolean.TRUE), __inject__(FieldInstancesRuntime.class).get(C1.class,"sub1.sub1.f01").getField());
	}
	
	@Test
	public void getParameterized_f01_from_Parameterized() {
		assertionHelper.assertEquals(FieldUtils.getDeclaredField(Parameterized.class, "f01", Boolean.TRUE), __inject__(FieldInstancesRuntime.class).get(Parameterized.class,"f01").getField());
	}
	
	@Test
	public void isParameterized_f01_type_Object_from_Parameterized() {
		assertionHelper.assertEquals(Object.class, __inject__(FieldInstancesRuntime.class).get(Parameterized.class,"f01").getType());
	}
	
	//@Test
	public void isParameterizedLong_f01_type_Long_from_ParameterizedLong() {
		//TODO make it work
		assertionHelper.assertEquals(Long.class, __inject__(FieldInstancesRuntime.class).get(ParameterizedLong.class,"f01").getType());
	}
	
	/**/
	
	@Getter @Setter
	public static class C1 {
		private String f01;
		private C1_Sub1 sub1;
	}
	
	@Getter @Setter
	public static class C1_Sub1 {
		private String f01;
		private C1_Sub1_Sub1 sub1;
	}
	
	@Getter @Setter
	public static class C1_Sub1_Sub1 {
		private String f01;
	}
	
	@Getter @Setter
	public static class C1_Sub2 {
		
	}
	
	@Getter @Setter
	public static class Parameterized<T> {
		private T f01;
	}
	
	@Getter @Setter
	public static class ParameterizedLong extends Parameterized<Long> {
	
	}
}
