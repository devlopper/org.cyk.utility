package org.cyk.utility.resource.locator;

import java.io.Serializable;
import java.net.URL;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class UrlBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<URL> implements UrlBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private UniformResourceLocatorStringBuilder string;
	
	@Override
	protected URL __execute__() throws Exception {
		URL url = null;
		UniformResourceLocatorStringBuilder string = getString();
		if(string!=null)
			url = new URL(string.execute().getOutput());
		return url;
	}
	
	@Override
	public UniformResourceLocatorStringBuilder getString() {
		return string;
	}
	
	@Override
	public UniformResourceLocatorStringBuilder getString(Boolean injectIfNull) {
		return (UniformResourceLocatorStringBuilder) __getInjectIfNull__(FIELD_STRING, injectIfNull);
	}

	@Override
	public UrlBuilder setString(UniformResourceLocatorStringBuilder string) {
		this.string = string;
		return this;
	}

	public static final String FIELD_STRING = "string";
	
}
