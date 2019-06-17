package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldValueSetterUnitTest extends AbstractWeldUnitTest {
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

	@Test
	public void setLong_2_long(){
		MyClass01 instance = new MyClass01();
		__inject__(FieldValueSetter.class).execute(instance,"longValue1",2);
		assertThat(instance.getLongValue1()).isEqualTo(4l);
	}
	
	@Test
	public void setMyProperty_2_object(){
		MyClass01 instance = new MyClass01();
		__inject__(FieldValueSetter.class).execute(instance,"myProperty",2);
		assertThat(instance.getMyProperty()).isEqualTo(2);
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
		private Object x;
		
		public MyClass01 setLongValue1(long value) {
			this.longValue1 = value * 2;
			return this;
		}
		
		public MyClass01 setMyProperty(Object value) {
			this.x = value;
			return this;
		}
		
		public Object getMyProperty() {
			return x;
		}
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
