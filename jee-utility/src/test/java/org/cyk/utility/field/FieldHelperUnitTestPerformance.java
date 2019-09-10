package org.cyk.utility.field;

import java.util.Date;

import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_100(){
		execute("Get field",100,10,new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__getByName__(MyClass01.class, "intField");
			}
		});
	}	
	
	@Test
	public void get_1000(){
		execute("Get field",1000,20,new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__getByName__(MyClass01.class, "intField");
			}
		});
	}	
	
	@Test
	public void get_10000(){
		execute("Get field",10000,30,new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__getByName__(MyClass01.class, "intField");
			}
		});
	}	
	
	@Test
	public void get_100000(){
		execute("Get field",100000,40,new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__getByName__(MyClass01.class, "intField");
			}
		});
	}	
	
	@Test
	public void get_1000000(){
		execute("Get field",1000000,50,new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__getByName__(MyClass01.class, "intField");
			}
		});
	}	
	
	@Test
	public void get_10000000(){
		execute("Get field",10000000,150,new Runnable() {
			@Override
			public void run() {
				FieldHelperImpl.__getByName__(MyClass01.class, "intField");
			}
		});
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
