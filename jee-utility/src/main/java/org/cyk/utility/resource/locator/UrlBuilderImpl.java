package org.cyk.utility.resource.locator;

import java.io.Serializable;
import java.net.URL;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent
public class UrlBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<URL> implements UrlBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private UniformResourceLocatorStringBuilder string;
	
	@Override
	protected URL __execute__() throws Exception {
		URL url = null;
		UniformResourceLocatorStringBuilder string = getString();
		if(string!=null) {
			String urlString = string.execute().getOutput();
			url = StringHelper.isBlank(urlString) ? null : new URL(urlString);
		}
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
