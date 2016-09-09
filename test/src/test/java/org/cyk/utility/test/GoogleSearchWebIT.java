package org.cyk.utility.test;

import java.util.concurrent.TimeUnit;

import org.cyk.utility.test.integration.ui.web.AbstractIntegrationWebTest;
import org.openqa.selenium.By;

public class GoogleSearchWebIT extends AbstractIntegrationWebTest {

	private static final long serialVersionUID = 1L;


	/*@Override
	protected WebDriver getWebDriver() {
    	WebDriver webDriver;
        String n = "C:\\Users\\SIB01\\AppData\\Local\\Mozilla Firefox\\firefox.exe";
        //n = "D:\\selenium\\firefox\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", "D:\\selenium\\firefox\\geckodriver.exe");
        n = "C:\\Users\\SIB01\\Downloads\\FirefoxPortable\\App\\Firefox\\firefox.exe";
        FirefoxBinary binary = new FirefoxBinary(new File(n));
        FirefoxProfile profile = new FirefoxProfile();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        webDriver = new FirefoxDriver(binary,profile,capabilities);
        
		return webDriver;
	}*/

	
    @Override
	protected void __execute__() {
    	getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    	getDriver().get("https://www.google.com/");
    	getDriver().findElement(By.id("lst-ib")).click();
    	getDriver().findElement(By.id("lst-ib")).clear();
    	getDriver().findElement(By.id("lst-ib")).sendKeys("hello world");
    	getDriver().findElement(By.id("lst-ib")).click();
    	getDriver().findElement(By.id("lst-ib")).sendKeys("\n");
    	getDriver().findElement(By.id("slim_appbar")).click();
    	getDriver().findElement(By.linkText("\"Hello, World!\" program - Wikipedia, the free encyclopedia")).click();
    	getDriver().findElement(By.id("mw-content-text")).click();
    	getDriver().findElement(By.linkText("5 References")).click();
	}
    

}
