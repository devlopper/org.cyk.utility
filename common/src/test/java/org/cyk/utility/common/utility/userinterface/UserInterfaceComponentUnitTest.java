package org.cyk.utility.common.utility.userinterface;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceComponentUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void getClazz(){
		Component.clearClasses();
		Window window = new Window();
		
		assertEquals(null, Component.getClass(window,Constant.Action.CREATE, Model1.class, null,null));
		assertEquals(MyForm.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		
		Component.setClass(null,Constant.Action.CREATE, Model1.class, null, MyFormCreate.class);
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		
	}
	
	/**/
	
	public static class Model1{}
	public static class Model2{}
	public static class Model3{}
	
	public static class MyForm{}
	
	public static class MyFormCreate{}
	public static class MyFormRead{}
	public static class MyFormUpdate{}
	public static class MyFormDelete{}
	public static class MyFormConsult{}
	
	public static class MyDataTable1{}
	public static class MyDataTable2{}
	public static class MyDataTable3{}
	
}
