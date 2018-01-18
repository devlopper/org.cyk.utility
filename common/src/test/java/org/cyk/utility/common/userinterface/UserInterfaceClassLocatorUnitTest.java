package org.cyk.utility.common.userinterface;

import org.cyk.system.test.model.myclass.MyClass001;
import org.cyk.system.test.model.myclass.MyClass002;
import org.cyk.system.test.model.myclass.MyClass003;
import org.cyk.system.test.ui.web.primefaces.page.myclass.MyClass001ConsultWindow;
import org.cyk.system.test.ui.web.primefaces.page.myclass.MyClass001EditWindow;
import org.cyk.system.test.ui.web.primefaces.page.myclass.MyClass001ListWindow;
import org.cyk.system.test.ui.web.primefaces.page.myclass.MyClass003EditWindow;
import org.cyk.ui.web.primefaces.page.myclass.MyClass002ConsultWindow;
import org.cyk.ui.web.primefaces.page.myclass.MyClass002EditWindow;
import org.cyk.ui.web.primefaces.page.myclass.MyClass002ListWindow;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.window.ConsultWindow;
import org.cyk.utility.common.userinterface.container.window.EditWindow;
import org.cyk.utility.common.userinterface.container.window.ListWindow;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceClassLocatorUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		ClassHelper.getInstance().map(EditWindow.FormMaster.ClassLocator.class, EditFormMasterClassLocator.class);
		ClassHelper.getInstance().map(ConsultWindow.FormMaster.ClassLocator.class, ConsultFormMasterClassLocator.class);
		ClassHelper.getInstance().map(ListWindow.DataTable.ClassLocator.class, ListDataTableClassLocator.class);
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
	
	@Test
	public void locateSystemConsultWindowFormMaster(){
		assertEquals(MyClass001ConsultWindow.FormMaster.class, ConsultWindow.FormMaster.ClassLocator.getInstance().locate(MyClass001.class));
	}
	
	@Test
	public void locateUiConsultWindowFormMaster(){
		assertEquals(MyClass002ConsultWindow.FormMaster.class, ConsultWindow.FormMaster.ClassLocator.getInstance().locate(MyClass002.class));
	}
	
	@Test
	public void locateCustomConsultWindowFormMaster(){
		assertEquals(MyFormConsult.class, ConsultWindow.FormMaster.ClassLocator.getInstance().locate(Model1.class));
	}
	
	@Test
	public void locateSystemEditWindowFormMasterWithOnlyEditForm(){
		assertEquals(MyClass003EditWindow.FormMaster.class, EditWindow.FormMaster.ClassLocator.getInstance().locate(MyClass003.class));
	}
	
	@Test
	public void locateSystemConsultWindowFormMasterWithOnlyEditForm(){
		assertEquals(MyClass003EditWindow.FormMaster.class, ConsultWindow.FormMaster.ClassLocator.getInstance().locate(MyClass003.class));
	}
	
	@Test
	public void locateSystemListWindowDataTable(){
		assertEquals(MyClass001ListWindow.DataTable.class, ListWindow.DataTable.ClassLocator.getInstance().locate(MyClass001.class));
	}
	
	@Test
	public void locateUiListWindowDataTable(){
		assertEquals(MyClass002ListWindow.DataTable.class, ListWindow.DataTable.ClassLocator.getInstance().locate(MyClass002.class));
	}
	
	@Test
	public void locateCustomListWindowDataTable(){
		assertEquals(MyDataTable1.class, ListWindow.DataTable.ClassLocator.getInstance().locate(Model1.class));
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
	
	public static class ConsultFormMasterClassLocator extends ConsultWindow.FormMaster.ClassLocator {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Class<?> locate(Class<?> basedClass) {
			if(Model1.class.equals(basedClass))
				return MyFormConsult.class;
			return super.locate(basedClass);
		}
		
	}
	
	public static class ListDataTableClassLocator extends ListWindow.DataTable.ClassLocator {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Class<?> locate(Class<?> basedClass) {
			if(Model1.class.equals(basedClass))
				return MyDataTable1.class;
			return super.locate(basedClass);
		}
		
	}
}
