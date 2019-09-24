package org.cyk.utility.__kernel__.identifier.resource;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterNameUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		ParameterName.MAP.clear();
	}
	
	@Test
	public void stringify_null() {
		assertThat(ParameterName.stringify(null)).isEqualTo(null);
	}
	
	@Test
	public void stringify_empty() {
		assertThat(ParameterName.stringify("")).isEqualTo(null);
	}
	
	@Test
	public void stringify_blank() {
		assertThat(ParameterName.stringify(" ")).isEqualTo(null);
	}
	
	@Test
	public void stringify_illegal() {
		Assertions.assertThrows(RuntimeException.class, () -> {ParameterName.stringify("_");} );
	}
	
	@Test
	public void stringify_class() {
		assertThat(ParameterName.stringify(Class.class)).isEqualTo("class");
	}
	
	@Test
	public void stringify_class_as_klass() {
		ParameterName.MAP.put(Class.class, "klass");
		assertThat(ParameterName.stringify(Class.class)).isEqualTo("klass");
	}
	
	@Test
	public void stringify_string_Abc() {
		assertThat(ParameterName.stringify("Abc")).isEqualTo("abc");
	}
	
	@Test
	public void stringify_enum_V01() {
		assertThat(ParameterName.stringify(MyEnum.V01)).isEqualTo("v01");
	}
	
	@Test
	public void stringify_enum_parameterName_ENTITY_CLASS() {
		assertThat(ParameterName.stringify(ParameterName.ENTITY_CLASS)).isEqualTo("entityclass");
	}
	
	/**/
	
	public static enum MyEnum {
		V01
		;
	}
}
