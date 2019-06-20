package org.cyk.utility.log.log4j2;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;

import org.apache.logging.log4j.core.LogEvent;
import org.cyk.utility.log.AbstractLogEventEntityBuilderImpl;

@Dependent @Alternative
public class LogEventEntityBuilderLog4j2Impl extends AbstractLogEventEntityBuilderImpl<LogEvent> implements LogEventEntityBuilderLog4j2, Serializable {
	private static final long serialVersionUID = 1L;
	

}
