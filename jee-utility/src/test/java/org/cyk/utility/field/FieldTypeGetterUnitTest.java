package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldTypeGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_Integer_classIsMyClassString_fieldIsF01(){
		FieldType fieldType = __inject__(FieldTypeGetter.class).execute(MyClassString.class, "f01").getOutput();
		assertThat(fieldType.getType()).isEqualTo(Integer.class);
		assertThat(fieldType.getParameterizedClasses()).isNull();
	}
	
	@Test
	public void get_String_classIsMyClassString_fieldIsF02(){
		FieldType fieldType = __inject__(FieldTypeGetter.class).execute(MyClassString.class, "f02").getOutput();
		assertThat(fieldType.getType()).isEqualTo(String.class);
		assertThat(fieldType.getParameterizedClasses()).isNull();
	}
	
	@Test
	public void get_String_classIsMyClass_fieldIsF02(){
		FieldType fieldType = __inject__(FieldTypeGetter.class).setFieldGetter(__inject__(FieldsGetter.class).setClazz(MyClassString.class).setToken("f02")).execute().getOutput();
		assertThat(fieldType.getType()).isEqualTo(String.class);
		assertThat(fieldType.getParameterizedClasses()).isNull();
	}
	
	@Test
	public void get_String_classIsMyClassString_fieldIsF03(){
		FieldType fieldType = __inject__(FieldTypeGetter.class).setFieldGetter(__inject__(FieldsGetter.class).setClazz(MyClassString.class).setToken("f03")).execute().getOutput();
		assertThat(fieldType.getType()).isEqualTo(Collection.class);
		assertThat(fieldType.getParameterizedClasses()).isNotNull();
		assertThat(fieldType.getParameterizedClasses().getMap()).isNotNull();
		assertThat(fieldType.getParameterizedClasses().getMap()).containsEntry(0, String.class);
	}
	
	@Test
	public void get_String_classIsMyClassString_fieldIsF04(){
		FieldType fieldType = __inject__(FieldTypeGetter.class).execute(MyClassString.class, "f04").getOutput();
		assertThat(fieldType.getType()).isEqualTo(Map.class);
		assertThat(fieldType.getParameterizedClasses()).isNotNull();
		assertThat(fieldType.getParameterizedClasses().getMap()).containsEntry(0, String.class);
		assertThat(fieldType.getParameterizedClasses().getMap()).containsEntry(1, String.class);
	}
	
	@Test
	public void get_Integer_classIsMyClassLong_fieldIsF01(){
		FieldType fieldType = __inject__(FieldTypeGetter.class).execute(MyClassString.class, "f01").getOutput();
		assertThat(fieldType.getType()).isEqualTo(Integer.class);
		assertThat(fieldType.getParameterizedClasses()).isNull();
	}
	
	@Test
	public void get_Long_classIsMyClassLong_fieldIsF02(){
		FieldType fieldType = __inject__(FieldTypeGetter.class).execute(MyClassLong.class, "f02").getOutput();
		assertThat(fieldType.getType()).isEqualTo(Long.class);
		assertThat(fieldType.getParameterizedClasses()).isNull();
	}
	
	@Test
	public void get_Long_classIsMyClassLong_fieldIsF03(){
		FieldType fieldType = __inject__(FieldTypeGetter.class).setFieldGetter(__inject__(FieldsGetter.class).setClazz(MyClassLong.class).setToken("f03")).execute().getOutput();
		assertThat(fieldType.getType()).isEqualTo(Collection.class);
		assertThat(fieldType.getParameterizedClasses()).isNotNull();
		assertThat(fieldType.getParameterizedClasses().getMap()).isNotNull();
		assertThat(fieldType.getParameterizedClasses().getMap()).containsEntry(0, Long.class);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass<T> {
		private Integer f01;
		private T f02;
		private Collection<T> f03;
		private Map<String,String> f04;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClassString extends MyClass<String> {
	
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClassLong extends MyClass<Long> {
	
	}
}
