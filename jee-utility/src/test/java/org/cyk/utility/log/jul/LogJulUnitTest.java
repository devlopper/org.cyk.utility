package org.cyk.utility.log.jul;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.log.Log;
import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class LogJulUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void logWithoutParameter() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).setSourceClassName(LogJulUnitTest.class.getName()).setSourceMethodName("logWithoutParameter").getMessageBuilder(Boolean.TRUE).addParameter("this is info without parameters").getParent().execute();
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.INFO).setMessage("this is info without parameters"));
	}
	
	@Test
	public void logWithParameter() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).getMessageBuilder(Boolean.TRUE).addParameter("this is info with parameter").addParameter("p1", "myvalue").getParent().execute();
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.INFO).setMessage("this is info with parameter , p1=myvalue"));
	}
	
	@Test
	public void logInfo() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).setLevel(LogLevel.INFO).getMessageBuilder(Boolean.TRUE).addParameter("this a info").getParent().execute();
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.INFO).setMessage("this a info"));
	}
	
	//@Test TODO make it support marker. An idea is to include marker in message itself which can affect readability
	public void logInfoWithMarker() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).setLevel(LogLevel.INFO).addMarkers("M01").getMessageBuilder(Boolean.TRUE).addParameter("this is info with marker").getParent().execute();
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.INFO).setMarker("M01").setMessage("this is info with marker"));
	}
	
	//@Test TODO make it support marker. An idea is to include marker in message itself which can affect readability
	public void logInfoWithMarkers() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).setLevel(LogLevel.INFO).addMarkers("MASTER","DETAIL","SPECIFIC").getMessageBuilder(Boolean.TRUE).addParameter("this is info with markers").getParent().execute();
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.INFO).setMarker("SPECIFIC").setMessage("this is info with markers"));
	}
	
	@Test
	public void logInfoUsingMessageOnly() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).executeInfo("this a info message");
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.INFO).setMessage("this a info message"));
	}

	@Test
	public void logTrace() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).setLevel(LogLevel.TRACE).getMessageBuilder(Boolean.TRUE).addParameter("this a trace").getParent().execute();
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.TRACE).setMessage("this a trace"));
	}
	
	@Test
	public void logDebug() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).setLevel(LogLevel.DEBUG).getMessageBuilder(Boolean.TRUE).addParameter("this a debug").getParent().execute();
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.DEBUG).setMessage("this a debug"));
	}
	
	@Test
	public void logInfoFromClass001FromMethodM001() {
		assertionHelper.assertEqualsLogEventCount(0);
		__inject__(Log.class).setLevel(LogLevel.INFO).getMessageBuilder(Boolean.TRUE).addParameter("INFOS From Class01.M001").getParent().setSourceMethodName("Class001").setSourceMethodName("M001").execute();
		assertionHelper.assertEqualsLastLogEventProperties(new Properties().setLogLevel(LogLevel.INFO).setMessage("INFOS From Class01.M001"));
	}

}
