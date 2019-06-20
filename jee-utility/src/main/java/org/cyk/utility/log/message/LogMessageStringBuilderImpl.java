package org.cyk.utility.log.message;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

@Dependent
public class LogMessageStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements LogMessageStringBuilder, Serializable {

	private static final long serialVersionUID = 1L;

}
