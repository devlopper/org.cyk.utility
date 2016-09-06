package org.cyk.utility.test.integration.ui.web;

import org.cyk.utility.test.AbstractTest;
import org.openqa.selenium.WebDriver;

public abstract class AbstractIntegrationWebTest extends AbstractTest  {
	
	private static final long serialVersionUID = -8873735551443449606L;
	
	@Override
	protected void _execute_() {
		WebDriver webDriver = getWebDriver();
		__execute__(webDriver);
		webDriver.quit();
	}
	
	protected abstract WebDriver getWebDriver();
	protected abstract void __execute__(WebDriver webDriver);
	
}
