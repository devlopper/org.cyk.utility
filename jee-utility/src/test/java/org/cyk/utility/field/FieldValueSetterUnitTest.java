package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldValueSetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void setInt_2_int(){
		MyClass01 instance = new MyClass01();
		__inject__(FieldValueSetter.class).execute(instance,"intField",2);
		assertThat(instance.getIntField()).isEqualTo(2);
	}
	
	@Test
	public void setInt_2_integer(){
		MyClass01 instance = new MyClass01();
		__inject__(FieldValueSetter.class).execute(instance,"intField",new Integer(2));
		assertThat(instance.getIntField()).isEqualTo(2);
	}
	
	@Test
	public void setInt_2_string(){
		MyClass01 instance = new MyClass01();
		__inject__(FieldValueSetter.class).execute(instance,"stringField",2);
		assertThat(instance.getStringField()).isEqualTo("2");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01 {
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longValue1;
		private Long longValue2;
		private Date dateField;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass02 {
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longValue1;
		private Long longValue2;
		private Date dateField;
	}
}
