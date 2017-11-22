package org.cyk.utility.common.utility.userinterface;

import org.cyk.system.test.model.myclass.MyClass001;
import org.cyk.system.test.model.myclass.MyClass002;
import org.cyk.system.test.ui.web.primefaces.page.myclass.MyClass001EditWindow;
import org.cyk.ui.web.primefaces.page.myclass.MyClass002EditWindow;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.window.EditWindow;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceClassLocatorUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		ClassHelper.getInstance().map(EditWindow.FormMaster.ClassLocator.class, EditFormMasterClassLocator.class);
	}
	
	@Test
	public void locateSystemEditWindowFormMaster(){
		assertEquals(MyClass001EditWindow.FormMaster.class, EditWindow.FormMaster.ClassLocator.getInstance().locate(MyClass001.class));
	}
	
	@Test
	public void locateUiEditWindowFormMaster(){
		assertEquals(MyClass002EditWindow.FormMaster.class, EditWindow.FormMaster.ClassLocator.getInstance().locate(MyClass002.class));
	}
	
	@Test
	public void locateCustomEditWindowFormMaster(){
		assertEquals(MyFormCreate.class, EditWindow.FormMaster.ClassLocator.getInstance().locate(Model1.class));
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
	
	public static class EditFormMasterClassLocator extends EditWindow.FormMaster.ClassLocator {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Class<?> locate(Class<?> basedClass) {
			if(Model1.class.equals(basedClass))
				return MyFormCreate.class;
			return super.locate(basedClass);
		}
		
	}
}
