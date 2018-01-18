package org.cyk.utility.common.userinterface;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceLoggingUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void logEditWindow(){
		MyEditWindow window = new MyEditWindow();
		window.postConstruct();
	}
	
	@Test
	public void logListWindow(){
		MyListWindow window = new MyListWindow();
		window.postConstruct();
	}
	
	public static class MyEditWindow extends Window implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	public static class MyListWindow extends Window implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
}
