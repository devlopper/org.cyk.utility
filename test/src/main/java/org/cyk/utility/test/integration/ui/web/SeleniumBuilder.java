package org.cyk.utility.test.integration.ui.web;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SeleniumBuilder extends AbstractIntegrationWebTest {

	private static final long serialVersionUID = 1L;


	@Override
	protected WebDriver getWebDriver() {
    	WebDriver webDriver;
        String n = "C:\\Users\\SIB01\\AppData\\Local\\Mozilla Firefox\\firefox.exe";
        //n = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
        FirefoxBinary binary = new FirefoxBinary(new File(n));
        FirefoxProfile profile = new FirefoxProfile();
        webDriver = new FirefoxDriver(binary,profile);
		return webDriver;
	}

	
    @Override
	protected void __execute__(WebDriver webDriver) {
    	webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    	webDriver.get("https://www.google.com/");
    	webDriver.findElement(By.id("lst-ib")).click();
    	webDriver.findElement(By.id("lst-ib")).clear();
    	webDriver.findElement(By.id("lst-ib")).sendKeys("hello world");
    	webDriver.findElement(By.id("lst-ib")).click();
    	webDriver.findElement(By.id("lst-ib")).sendKeys("\n");
    	webDriver.findElement(By.id("slim_appbar")).click();
    	webDriver.findElement(By.linkText("\"Hello, World!\" program - Wikipedia, the free encyclopedia")).click();
    	webDriver.findElement(By.id("mw-content-text")).click();
    	webDriver.findElement(By.linkText("5 References")).click();
	}
    

}
