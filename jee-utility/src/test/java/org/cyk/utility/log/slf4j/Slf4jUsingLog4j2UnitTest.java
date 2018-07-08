package org.cyk.utility.log.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

public class Slf4jUsingLog4j2UnitTest {
	
	static {
		System.setProperty("log4j.configurationFile", "org/cyk/utility/log/slf4j/log4j2.xml");
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jUsingLog4j2UnitTest.class);
	
	@Test
	public void logInfoWithSimpleMessage(){ 
		LOGGER.info("Info using SLF4J API");
	}
	
	@Test
	public void logInfoWithParameters(){ 
		LOGGER.info("Info using SLF4J API. p1 = {} , p2 = {} , another one = {}","first","two","great world!!!");
	}
	
	@Test
	public void logInfoWithSimpleMessageAndMarker(){ 
		LOGGER.info(MarkerFactory.getMarker("M01"),"Info using SLF4J API");
	}
	
	@Test
	public void logInfoWithParametersAndMarker(){ 
		LOGGER.info(MarkerFactory.getMarker("M01"),"Info using SLF4J API. p1 = {} , p2 = {} , another one = {}","first","two","great world!!!");
	}
}
