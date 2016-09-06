package org.cyk.utility.test.integration.ui.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.test.AbstractTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class AbstractIntegrationWebTest extends AbstractTest  {
	
	private static final long serialVersionUID = -8873735551443449606L;
	
	protected String propertiesFileName = "selenium",googleDriverPathPropertyName="google_driver";
	protected String googleDriverPath;
	
	@Override
	protected void _execute_() {
		File propertiesFile = new File(System.getProperty("user.dir"),propertiesFileName+".properties");
		if(propertiesFile.exists()){
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(propertiesFile));
				googleDriverPath = properties.getProperty(googleDriverPathPropertyName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else
			System.out.println("No cyk selenium properties file found. Test can be envionment dependent");
		
		for(Class<? extends WebDriver> clazz : getWebDriverClasses()){
			System.out.println("Running using driver of type "+clazz.getName());
			WebDriver webDriver = getWebDriver(clazz);
			if(webDriver==null)
				System.out.println("No driver instance can be created from type "+clazz);
			else{
				__execute__(webDriver);
				webDriver.quit();
			}
		}
	}
	
	protected WebDriver getWebDriver(Class<? extends WebDriver> webDriverClass){
		if(ChromeDriver.class.equals(webDriverClass))
			return getChromeDriver();
		return null;
	}
	
	protected Collection<Class<? extends WebDriver>> getWebDriverClasses(){
		Collection<Class<? extends WebDriver>> collection = new LinkedHashSet<>();
		collection.add(ChromeDriver.class);
		return collection;
	}
	
	protected ChromeDriver getChromeDriver(){
		ChromeDriver chromeDriver = null;
		if(StringUtils.isBlank(googleDriverPath))
			System.out.println("No google driver path set.");
		else{
			System.setProperty("webdriver.chrome.driver", googleDriverPath);
			chromeDriver = new ChromeDriver();
		}
		return chromeDriver;
	}
	
	protected abstract void __execute__(WebDriver webDriver);
	
}
