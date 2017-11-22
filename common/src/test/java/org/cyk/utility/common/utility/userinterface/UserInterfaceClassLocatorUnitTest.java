package org.cyk.utility.common.utility.userinterface;

import org.cyk.system.test.model.myclass.MyClass001;
import org.cyk.system.test.model.myclass.MyClass002;
import org.cyk.system.test.ui.web.primefaces.page.myclass.MyClass001EditWindow;
import org.cyk.ui.web.primefaces.page.MyClass002EditWindow;
import org.cyk.utility.common.userinterface.container.window.EditWindow;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceClassLocatorUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void locateSystemEditWindowFormMaster(){
		assertEquals(MyClass001EditWindow.FormMaster.class, EditWindow.FormMasterClassLocator.getInstance().locate(MyClass001.class));
	}
	
	@Test
	public void locateUiEditWindowFormMaster(){
		assertEquals(MyClass002EditWindow.FormMaster.class, EditWindow.FormMasterClassLocator.getInstance().locate(MyClass002.class));
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
