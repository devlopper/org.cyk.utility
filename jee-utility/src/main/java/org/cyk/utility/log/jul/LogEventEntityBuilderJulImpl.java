package org.cyk.utility.log.jul;

import java.io.Serializable;
import java.util.logging.LogRecord;

import javax.enterprise.context.Dependent;

import org.cyk.utility.log.AbstractLogEventEntityBuilderImpl;

@Dependent
public class LogEventEntityBuilderJulImpl extends AbstractLogEventEntityBuilderImpl<LogRecord> implements LogEventEntityBuilderJul, Serializable {
	private static final long serialVersionUID = 1L;
	

}
