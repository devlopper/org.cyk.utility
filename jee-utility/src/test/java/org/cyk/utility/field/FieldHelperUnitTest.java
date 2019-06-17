package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void join(){
		assertThat(__inject__(FieldHelper.class).join("f1","f2.f3")).isEqualTo("f1.f2.f3");
	}	
	
	@Test
	public void disjoin(){
		assertThat(__inject__(FieldHelper.class).disjoin("f1","f2.f3").get()).containsExactly("f1","f2","f3");
	}	
	
	@Test
	public void getField_intField(){
		assertThat(__inject__(FieldHelper.class).getField(MyClass01.class, "intField")).isEqualTo(FieldUtils.getField(MyClass01.class, "intField",Boolean.TRUE));
	}	

	@Test
	public void getField_sub_sIntField(){
		assertThat(__inject__(FieldHelper.class).getField(MyClass01.class, "sub","sIntField")).isEqualTo(FieldUtils.getField(MyClass01Sub.class, "sIntField",Boolean.TRUE));
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
		private MyClass01Sub sub;
		
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
	public static class MyClass01Sub {
		
		private int sIntField;
		private Integer sIntegerField;
		private String sStringField;
		private long sLongValue1;
		private Long sLongValue2;
		private Date sDateField;
		private Object sX;
		
	}
}
