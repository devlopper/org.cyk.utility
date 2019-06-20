package org.cyk.utility.log.jul;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;

import org.cyk.utility.log.message.AbstractLogMessageBuilderImpl;

@Dependent @Default
public class LogMessageBuilderJulImpl extends AbstractLogMessageBuilderImpl implements LogMessageBuilderJul, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getParameterFormatRightSide() {
		return "{%s}";
	}
	
}
