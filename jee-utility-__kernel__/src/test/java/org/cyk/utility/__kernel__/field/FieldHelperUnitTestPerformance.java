package org.cyk.utility.__kernel__.field;

import static org.cyk.utility.__kernel__.field.FieldHelper.getByName;

import java.util.Date;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void getByName_(){
		execute("Get field by name",1000000,10,new Runnable() {
			@Override
			public void run() {
				getByName(Class.class, "intField");
			}
		});
	}	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longValue1;
		private Long longValue2;
		private Date dateField;
		private Object x;
	}
}
