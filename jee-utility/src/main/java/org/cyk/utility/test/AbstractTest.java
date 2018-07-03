package org.cyk.utility.test;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.assertion.AssertionHelper;
import org.cyk.utility.log.log4j2.Log4j2Appender;
import org.cyk.utility.log.log4j2.LogEventRepositoryLog4j;
import org.junit.Before;

public abstract class AbstractTest extends AbstractObject implements Serializable {
	private static final long serialVersionUID = -4375668358714913342L;
	
	/* Log4j Configuration */
	static {
		/*
		 * Let us use Log4j as logging framework for our tests.
		 * This can be disabled by setting null 
		 */
		//ConfigurationFactory.setConfigurationFactory(new Log4j2ConfigurationFactory());
		//System.setProperty("log4j.configurationFile", "org/cyk/utility/log/log4j2.xml");
	}
	
	protected LogEventRepositoryLog4j logEventRepository;
	
	@Inject protected AssertionHelper assertionHelper;
	
	@Before
	public void listenBefore() {
		__listenBefore__();
	}
	
	protected void __listenBefore__(){
		org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
		logEventRepository = new Log4j2Appender();
		((Appender)logEventRepository).start();
		logger.getContext().getConfiguration().addLoggerAppender(logger, (Appender) logEventRepository);
	}
	
}
