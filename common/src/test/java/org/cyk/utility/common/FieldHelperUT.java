package org.cyk.utility.common;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper.Location;
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
	public void get(){
		assertEquals(4, fieldHelper.get(MyClass.class).size());
		assertEquals(4, fieldHelper.get(MyClass.class, null, null).size());
		assertEquals(4, fieldHelper.get(MyClass.class, "", null).size());
		assertEquals(4, fieldHelper.get(MyClass.class, null, Location.START).size());
		assertEquals(4, fieldHelper.get(MyClass.class, "", Location.START).size());
		assertEquals(1, fieldHelper.get(MyClass.class, "f", Location.START).size());
		assertEquals(3, fieldHelper.get(MyClass.class, "S", Location.START).size());
		assertEquals(2, fieldHelper.get(MyClass.class, "1", Location.END).size());
		assertEquals(1, fieldHelper.get(MyClass.class, "F2", Location.INSIDE).size());
		assertEquals(0, fieldHelper.get(MyClass.class, "F", Location.EXAT).size());
	}
	
	@Test
	public void setStatic(){
		fieldHelper.set(MyClass.class, "SF1",5);
		assertEquals("SF1 value cannot be set", 5, MyClass.getSF1());
	}
	
	@Test
	public void copy(){
		MyClass source = new MyClass();
		source.setF1("the val");
		assertEquals("the val", source.getF1());
		
		MyClass destination = new MyClass();
		assertThat("f1 is not null",destination.getF1()==null);
		
		fieldHelper.copy(source, destination, "f1");
		
		assertEquals("the val", source.getF1());
		assertEquals("the val", destination.getF1());
		assertEquals(source.getF1(),destination.getF1());
		
		
	}
	
	/**/
	@Getter @Setter
	public static class MyClass {
		
		private String f1;
		
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
