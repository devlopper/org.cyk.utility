package org.cyk.utility.log.jul;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class JulUnitTest {
	
	private static final Logger LOGGER = Logger.getLogger(JulUnitTest.class.getName());
	
	@Test
	public void logInfoWithSimpleMessage(){ 
		LOGGER.info("Info using JUL API");
	}
	
	@Test
	public void logInfoWithParameters(){ 
		LOGGER.log(Level.INFO,"Info using JUL API. p1 = {0} , p2 = {1} , another one = {2}",new Object[]{"first","two","great world!!!"});
	}
	
	/*@Test
	public void logInfoWithSimpleMessageAndMarker(){ 
		LOGGER.info(MarkerFactory.getMarker("M01"),"Info using SLF4J API");
	}
	
	@Test
	public void logInfoWithParametersAndMarker(){ 
		LOGGER.info(MarkerFactory.getMarker("M01"),"Info using SLF4J API. p1 = {} , p2 = {} , another one = {}","first","two","great world!!!");
	}*/
}
