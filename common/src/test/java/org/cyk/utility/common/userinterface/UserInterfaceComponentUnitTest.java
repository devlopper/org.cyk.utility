package org.cyk.utility.common.userinterface;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceComponentUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.testmsg", StringHelper.class.getClassLoader());
	}
	
	@Test
	public void getPropertyStringValueFromIdentifier(){
		assertEquals("##userinterface.window.home.title##", Component.getPropertyStringValueFromIdentifier("window", "home", "title"));
		assertEquals(Boolean.FALSE, StringHelper.getInstance().isIdentified(Component.getPropertyStringValueFromIdentifier("window", "home", "title")));
		
		assertEquals("Déconnection de compte", Component.getPropertyStringValueFromIdentifier("window", "logout", "title"));
		assertEquals(Boolean.TRUE, StringHelper.getInstance().isIdentified(Component.getPropertyStringValueFromIdentifier("window", "logout", "title")));
	}
	
	@Test
	public void getActionsSharingClass(){
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.CREATE}, null, null, null),Constant.Action.CREATE);
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.CREATE}, null, null, null,Boolean.TRUE));
		
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.READ}, null, null, null)
				, Constant.Action.READ,Constant.Action.CONSULT,Constant.Action.CREATE,Constant.Action.UPDATE,Constant.Action.DELETE);
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.READ}, null, null, null,Boolean.TRUE)
				,Constant.Action.CONSULT,Constant.Action.CREATE,Constant.Action.UPDATE,Constant.Action.DELETE);
		
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.UPDATE}, null, null, null)
				, Constant.Action.UPDATE,Constant.Action.CREATE);
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.UPDATE}, null, null, null,Boolean.TRUE)
				,Constant.Action.CREATE);
		
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.DELETE}, null, null, null)
				, Constant.Action.DELETE,Constant.Action.READ,Constant.Action.CREATE,Constant.Action.UPDATE);
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.DELETE}, null, null, null,Boolean.TRUE)
				,Constant.Action.READ,Constant.Action.CREATE,Constant.Action.UPDATE);
		
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.CONSULT}, null, null, null)
				, Constant.Action.CONSULT,Constant.Action.READ,Constant.Action.CREATE,Constant.Action.UPDATE,Constant.Action.DELETE);
		assertArray(Component.getActionsSharingClass(null, new Constant.Action[]{Constant.Action.CONSULT}, null, null, null,Boolean.TRUE)
				,Constant.Action.READ,Constant.Action.CREATE,Constant.Action.UPDATE,Constant.Action.DELETE);
	}
	
	@Test
	public void getClazz01(){
		Component.clearClasses();
		Window window = new Window();
		
		assertEquals(null, Component.getClass(window,Constant.Action.CREATE, Model1.class, null,null));
		assertEquals(MyForm.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		assertEquals(MyForm.class, Component.getClass(window,Constant.Action.READ, Model1.class,MyForm.class));
		assertEquals(MyForm.class, Component.getClass(window,Constant.Action.UPDATE, Model1.class,MyForm.class));
		assertEquals(MyForm.class, Component.getClass(window,Constant.Action.DELETE, Model1.class,MyForm.class));
		assertEquals(MyForm.class, Component.getClass(window,Constant.Action.CONSULT, Model1.class,MyForm.class));
		
		Component.__setClass__(null,Constant.Action.CREATE, Model1.class, null, MyFormCreate.class);
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.READ, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.UPDATE, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.DELETE, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CONSULT, Model1.class,MyForm.class));
		
		Component.__setClass__(null,Constant.Action.READ, Model1.class, null, MyFormRead.class);
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.READ, Model1.class,MyFormRead.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.UPDATE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.DELETE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.CONSULT, Model1.class,MyForm.class));
		
		Component.__setClass__(null,Constant.Action.UPDATE, Model1.class, null, MyFormUpdate.class);
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.READ, Model1.class,MyFormRead.class));
		assertEquals(MyFormUpdate.class, Component.getClass(window,Constant.Action.UPDATE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.DELETE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.CONSULT, Model1.class,MyForm.class));
		
		Component.__setClass__(null,Constant.Action.DELETE, Model1.class, null, MyFormDelete.class);
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.READ, Model1.class,MyFormRead.class));
		assertEquals(MyFormUpdate.class, Component.getClass(window,Constant.Action.UPDATE, Model1.class,MyForm.class));
		assertEquals(MyFormDelete.class, Component.getClass(window,Constant.Action.DELETE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.CONSULT, Model1.class,MyForm.class));
		
		Component.__setClass__(null,Constant.Action.CONSULT, Model1.class, null, MyFormConsult.class);
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		assertEquals(MyFormRead.class, Component.getClass(window,Constant.Action.READ, Model1.class,MyFormRead.class));
		assertEquals(MyFormUpdate.class, Component.getClass(window,Constant.Action.UPDATE, Model1.class,MyForm.class));
		assertEquals(MyFormDelete.class, Component.getClass(window,Constant.Action.DELETE, Model1.class,MyForm.class));
		assertEquals(MyFormConsult.class, Component.getClass(window,Constant.Action.CONSULT, Model1.class,MyForm.class));
	}
	
	@Test
	public void getClazz02(){
		Component.clearClasses();
		Window window = new Window();
		
		Component.__setClass__(null,Constant.Action.CREATE, Model1.class, null, MyFormCreate.class);
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.READ, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.UPDATE, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.DELETE, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CONSULT, Model1.class,MyForm.class));
		
		Component.__setClass__(null,Constant.Action.CONSULT, Model1.class, null, MyFormConsult.class);
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.CREATE, Model1.class,MyForm.class));
		assertEquals(MyFormConsult.class, Component.getClass(window,Constant.Action.READ, Model1.class,MyFormRead.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.UPDATE, Model1.class,MyForm.class));
		assertEquals(MyFormCreate.class, Component.getClass(window,Constant.Action.DELETE, Model1.class,MyForm.class));
		assertEquals(MyFormConsult.class, Component.getClass(window,Constant.Action.CONSULT, Model1.class,MyForm.class));
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
