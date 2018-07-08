package org.cyk.utility.log.log4j2;

import java.io.Serializable;

import javax.enterprise.inject.Alternative;

import org.cyk.utility.log.message.AbstractLogMessageBuilderImpl;

@Alternative
public class LogMessageBuilderLog4j2Impl extends AbstractLogMessageBuilderImpl implements LogMessageBuilderLog4j2,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String getParameterFormatRightSide() {
		return "{}";
	}
	
}
