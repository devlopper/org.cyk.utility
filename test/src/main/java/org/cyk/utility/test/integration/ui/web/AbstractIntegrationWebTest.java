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
	private WebDriver driver;
	protected Boolean authenticationEnabled = Boolean.FALSE,autoLogin=Boolean.TRUE,autoLogout=Boolean.TRUE;
	protected String[][] users;
	protected WebDriver getDriver(){
		return driver;
	}
	
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
		
		for(String[] user : users){
			System.out.println("Running with user "+user[0]+" and password "+user[1]);
			for(Class<? extends WebDriver> clazz : getWebDriverClasses()){
				System.out.println("Running using driver of type "+clazz.getName());
				driver = getWebDriver(clazz);
				if(driver==null)
					System.out.println("No driver instance can be created from type "+clazz);
				else{
					setDriver(driver);
					if(Boolean.TRUE.equals(authenticationEnabled) && Boolean.TRUE.equals(autoLogin)){
						goToLoginPage();
						login(user[0],user[1]);
					}
					__execute__();
					if(Boolean.TRUE.equals(authenticationEnabled) && Boolean.TRUE.equals(autoLogout))
						logout(user[0]);
					driver.quit();
				}
			}
		}
	}
	protected void goToLoginPage(){}
	protected void login(String username,String password){}
	protected void logout(String username){}
	
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
	
	protected void setDriver(WebDriver driver){
		this.driver = driver;
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
	
	protected abstract void __execute__();
	
}
