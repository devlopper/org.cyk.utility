package org.cyk.utility;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.field.FieldsGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class UnitTestPerformance extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_field_value_ClassWithGetterSetter_f01_10000_apache(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			try {
				FieldUtils.readField(new ClassWithGetterSetter(), "f01", Boolean.TRUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		System.out.println("UnitTestPerformance.get_field_value_ClassWithGetterSetter_f01_10000_apache() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test
	public void get_field_value_ClassWithGetterSetter_f01_10000_fieldValueGetter(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			__inject__(FieldValueGetter.class).setField(FieldUtils.getField(ClassWithGetterSetter.class, "f01")).execute().getOutput();
		System.out.println("UnitTestPerformance.get_field_value_ClassWithGetterSetter_f01_10000_fieldValueGetter() : "+(System.currentTimeMillis() - timestamp));
	}

	@Test
	public void get_field_value_ClassWithoutGetterSetterPrivate_f01_10000_apache(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			try {
				FieldUtils.readField(new ClassWithoutGetterSetterPrivate(), "f01", Boolean.TRUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		System.out.println("UnitTestPerformance.get_field_value_ClassWithoutGetterSetterPrivate_f01_10000_apache() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test
	public void get_field_value_ClassWithoutGetterSetterPrivate_f01_10000_fieldValueGetter(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			__inject__(FieldValueGetter.class).setField(FieldUtils.getField(ClassWithoutGetterSetterPrivate.class, "f01")).execute().getOutput();
		System.out.println("UnitTestPerformance.get_field_value_ClassWithoutGetterSetterPrivate_f01_10000_fieldValueGetter() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test
	public void get_field_value_ClassWithoutGetterSetterPublic_f01_10000_apache(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			try {
				FieldUtils.readField(new ClassWithoutGetterSetterPublic(), "f01", Boolean.TRUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		System.out.println("UnitTestPerformance.get_field_value_ClassWithoutGetterSetterPublic_f01_10000_apache() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test
	public void get_field_value_ClassWithoutGetterSetterPublic_f01_10000_fieldValueGetter(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			__inject__(FieldValueGetter.class).setField(FieldUtils.getField(ClassWithoutGetterSetterPublic.class, "f01")).execute().getOutput();
		System.out.println("UnitTestPerformance.get_field_value_ClassWithoutGetterSetterPublic_f01_10000_fieldValueGetter() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test
	public void get_field_1(){
		Long timestamp = System.currentTimeMillis();
		__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("UnitTestPerformance.get_field_1() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test
	public void get_field_100(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 100 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("UnitTestPerformance.get_field_100() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test
	public void get_field_10000(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("UnitTestPerformance.get_field_10000() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test 
	public void get_field_1000(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 1000 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("UnitTestPerformance.get_field_1000() : "+(System.currentTimeMillis() - timestamp));
	}
	
	@Test
	public void get_field_100000(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 100000 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("UnitTestPerformance.get_field_100000() : "+(System.currentTimeMillis() - timestamp));
	}
	
	/**/
	
	public static interface I01 {
		String F01 = "v01";
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ClassWithGetterSetter {
		private String f01;
	}
	
	public static class ClassWithoutGetterSetterPrivate {
		@SuppressWarnings("unused")
		private String f01;
	}
	
	public static class ClassWithoutGetterSetterPublic {
		public String f01;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Fields {
		public String f01;
	}
}
