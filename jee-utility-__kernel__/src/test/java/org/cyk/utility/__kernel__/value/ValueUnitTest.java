package org.cyk.utility.__kernel__.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.field.Field;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ValueUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_field_isNull() {
		assertThat(new Value().setObject(new Class()).setField(new Field().setPath("f01")).get()).isNull();
	}
	
	@Test
	public void get_field_isNotNull() {
		assertThat(new Value().setObject(new Class().setF01("v01")).setField(new Field().setPath("f01")).get()).isEqualTo("v01");
	}
	
	@Test
	public void get_literal_isNull() {
		assertThat(new Value().setObject(new Class()).get()).isNull();
	}
	
	@Test
	public void get_literal_isNotNull() {
		assertThat(new Value().setObject(new Class()).set("v01").get()).isEqualTo("v01");
	}
	
	@Test
	public void get_configuration_isNull() {
		assertThat(new Value().setConfigurationValueName("env01").get()).isNull();
	}
	
	@Test
	public void get_configuration_isNotNull() {
		System.setProperty("myprop01", "v001");
		assertThat(new Value().setConfigurationValueName("myprop01").get()).isEqualTo("v001");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		
		private String f01;
		private Sub sub;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Sub {
		
		private String f01;
		
	}
	
}
