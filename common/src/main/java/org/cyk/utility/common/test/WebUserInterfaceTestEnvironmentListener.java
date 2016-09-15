package org.cyk.utility.common.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.openqa.selenium.WebDriver;

public interface WebUserInterfaceTestEnvironmentListener extends TestEnvironmentListener {

	Collection<WebUserInterfaceTestEnvironmentListener> COLLECTION = new ArrayList<>();
	
	/**/
	
	void setDriver(WebDriver driver);
	
	void login(String username,String password);
	
	void logout(String username);
	
	void goToLoginPage();
	
	/**/
	
	public static class Adapter extends TestEnvironmentListener.Adapter implements WebUserInterfaceTestEnvironmentListener,Serializable{
		private static final long serialVersionUID = 926931368237515224L;

		/**/
		
		@Override
		public void setDriver(WebDriver driver) {}

		@Override
		public void login(String username, String password) {}

		@Override
		public void logout(String username) {}

		@Override
		public void goToLoginPage() {}
		
		public static class Default extends WebUserInterfaceTestEnvironmentListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
				
		}

		
	}
	/**/
	
}
