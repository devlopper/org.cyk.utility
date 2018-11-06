package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;
import java.net.URL;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.resource.locator.UrlBuilder;
import org.cyk.utility.string.StringHelper;

public class NavigationBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Navigation> implements NavigationBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private UrlBuilder url;
	
	@Override
	protected Navigation __execute__() throws Exception {
		Navigation navigation = __inject__(Navigation.class);
		Object identifier = getIdentifier();
		navigation.setIdentifier(identifier);
		UrlBuilder url = getUrl();
		if(url==null) {
			String urlString = __inject__(NavigationIdentifierToUrlStringMapper.class).setIdentifier(identifier).execute().getOutput();
			if(__inject__(StringHelper.class).isNotBlank(urlString))
				navigation.setUniformResourceLocator(new URL(urlString));
		}else {
			navigation.setUniformResourceLocator(url.execute().getOutput());
		}
		return navigation;
	}
	
	@Override
	public UrlBuilder getUrl() {
		return url;
	}

	@Override
	public NavigationBuilder setUrl(UrlBuilder url) {
		this.url = url;
		return this;
	}
	
}
