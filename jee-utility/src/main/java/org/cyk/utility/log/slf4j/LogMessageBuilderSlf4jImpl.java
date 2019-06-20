package org.cyk.utility.log.slf4j;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.log.message.AbstractLogMessageBuilderImpl;

@Dependent @Slf4j
public class LogMessageBuilderSlf4jImpl extends AbstractLogMessageBuilderImpl implements LogMessageBuilderSlf4j, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getParameterFormatRightSide() {
		return "{}";
	}

}
