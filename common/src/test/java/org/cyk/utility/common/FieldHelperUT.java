package org.cyk.utility.common;

import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class FieldHelperUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@InjectMocks private FieldHelper fieldHelper;
	
	@Override
	protected void _execute_() {
		super._execute_();
		
	}
	
	@Test
	public void setStatic(){
		fieldHelper.set(MyClass.class, "SF1",5);
		assertEquals("SF1 value cannot be set", 5, MyClass.getSF1());
	}
	
	/**/
	
	public static class MyClass {
		
		private static Integer SF1;
		private static String SF2;
		private static MyClass SF3;
		
		public static Integer getSF1() {
			return SF1;
		}
		
		public static String getSF2() {
			return SF2;
		}
		
		public static MyClass getSF3() {
			return SF3;
		}
	}
	
}
