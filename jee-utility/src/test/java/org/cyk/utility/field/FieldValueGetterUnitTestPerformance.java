package org.cyk.utility.field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldValueGetterUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_10(){
		execute(10,20);
	}
	
	@Test
	public void get_100(){
		execute(100,40);
	}
	
	@Test
	public void get_1000(){
		execute(1000,100);
	}
	
	@Test
	public void get_10000(){
		execute(10000,700);
	}
	
	@Test
	public void get_100000(){
		execute(100000,4000);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		
		private String f01;
	
	}

	@Override
	protected void __execute__() {
		Class object = new Class().setF01("Hello");
		String fieldName = "f01";
		//__inject__(FieldValueGetter.class).setObject(object).setFieldName(fieldName).execute();
		try {
			FieldUtils.readField(object, fieldName, Boolean.TRUE);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
