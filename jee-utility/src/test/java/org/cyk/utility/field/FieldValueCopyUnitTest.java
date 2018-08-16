package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Date;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldValueCopyUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void one_2_int_string(){
		MyClass01 instance01 = new MyClass01().setIntField(2);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"intField");
		assertThat(instance02.getIntField()).isEqualTo("2");
	}
	
	@Test
	public void one_5_long_string(){
		MyClass01 instance01 = new MyClass01().setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"longField1");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	@Test
	public void many_copy_2_int_and_5_long(){
		MyClass01 instance01 = new MyClass01().setIntField(2).setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).setFieldName("intField").setFieldName("longField1").execute();
		assertThat(instance02.getIntField()).isEqualTo("2");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	@Test
	public void many_copy_all(){
		MyClass01 instance01 = new MyClass01().setIntField(2).setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).execute();
		assertThat(instance02.getIntField()).isEqualTo("2");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01 implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longField1;
		private Long longField2;
		private Date dateField;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass02 implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String intField;
		private String integerField;
		private String stringField;
		private String longField1;
		private String longField2;
		private String dateField;
	}
}
