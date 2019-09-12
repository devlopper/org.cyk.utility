package org.cyk.utility.string;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractStringFunctionImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements StringFunction,Serializable {
	private static final long serialVersionUID = 1L;
	
	private StringFormat format;
	
	protected StringFormat __getFormat__(StringFormat format) {
		return format;
	}
	
	@Override
	protected String __execute__() throws Exception {
		String string = null;
		StringFormat format = __getFormat__(getFormat());
		if(format == null)
			string = super.__execute__();
		else
			string = __execute__(format);
		return string;
	}
	
	protected String __execute__(StringFormat format) {
		return format.evaluate();
	}
	
	@Override
	public StringFormat getFormat() {
		return format;
	}

	@Override
	public StringFormat getFormat(Boolean injectIfNull) {
		if(format == null && Boolean.TRUE.equals(injectIfNull))
			format = __inject__(StringFormat.class);
		return format;
	}

	@Override
	public StringFunction setFormat(StringFormat format) {
		this.format = format;
		return this;
	}
	
	/**/

}
