package org.cyk.utility.log.log4j2;

import java.io.Serializable;

import org.cyk.utility.log.message.AbstractLogMessageBuilderImpl;

public class LogLog4jMessageBuilderImpl extends AbstractLogMessageBuilderImpl implements LogLog4jMessageBuilder,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String getParameterFormatRightSide() {
		return "{}";
	}
	
}
