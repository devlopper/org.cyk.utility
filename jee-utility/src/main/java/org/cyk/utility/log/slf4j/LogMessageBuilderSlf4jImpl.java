package org.cyk.utility.log.slf4j;

import java.io.Serializable;

import javax.enterprise.inject.Alternative;

import org.cyk.utility.log.message.AbstractLogMessageBuilderImpl;

@Alternative
public class LogMessageBuilderSlf4jImpl extends AbstractLogMessageBuilderImpl implements LogMessageBuilderSlf4j, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getParameterFormatRightSide() {
		return "{}";
	}

}
