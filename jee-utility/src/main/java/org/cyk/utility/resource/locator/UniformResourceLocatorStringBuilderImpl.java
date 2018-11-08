package org.cyk.utility.resource.locator;

import java.io.Serializable;

import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.string.AbstractStringFunctionImpl;

public class UniformResourceLocatorStringBuilderImpl extends AbstractStringFunctionImpl implements UniformResourceLocatorStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private UniformResourceIdentifierStringBuilder uniformResourceIdentifierString;
	
	@Override
	protected String __execute__() throws Exception {
		String string = null;
		UniformResourceIdentifierStringBuilder uniformResourceIdentifierString = getUniformResourceIdentifierString();
		if(uniformResourceIdentifierString!=null)
			string = uniformResourceIdentifierString.execute().getOutput();
		return string;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString() {
		return uniformResourceIdentifierString;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString(Boolean injectIfNull) {
		return (UniformResourceIdentifierStringBuilder) __getInjectIfNull__(FIELD_UNIFORM_RESOURCE_IDENTIFIER_STRING, injectIfNull);
	}

	@Override
	public UniformResourceLocatorStringBuilder setUniformResourceIdentifierString(UniformResourceIdentifierStringBuilder uniformResourceIdentifierString) {
		this.uniformResourceIdentifierString = uniformResourceIdentifierString;
		return this;
	}

	/**/
	
	public static final String FIELD_UNIFORM_RESOURCE_IDENTIFIER_STRING = "uniformResourceIdentifierString";
}
