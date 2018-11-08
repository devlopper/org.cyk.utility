package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.object.ObjectByObjectMap;
import org.cyk.utility.resource.locator.UrlBuilder;

public class NavigationBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Navigation> implements NavigationBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private UrlBuilder url;
	private ObjectByObjectMap parameterMap;
	
	@Override
	protected Navigation __execute__() throws Exception {
		Navigation navigation = __inject__(Navigation.class);
		Object identifier = getIdentifier();
		navigation.setIdentifier(identifier);
		UrlBuilder url = getUrl();
		if(url==null) {
			url = __inject__(UrlBuilder.class);
			
			String urlString = __inject__(NavigationIdentifierToUrlStringMapper.class).setIdentifier(identifier).execute().getOutput();
			url.getString(Boolean.TRUE).getUniformResourceIdentifierString(Boolean.TRUE).setString(urlString);
			
			ObjectByObjectMap parameterMap = getParameterMap();
			url.getString(Boolean.TRUE).getUniformResourceIdentifierString(Boolean.TRUE).setParameterMap(parameterMap);
		}
		
		if(url!=null)
			navigation.setUniformResourceLocator(url.execute().getOutput());
		
		return navigation;
	}
	
	@Override
	public UrlBuilder getUrl() {
		return url;
	}
	
	@Override
	public UrlBuilder getUrl(Boolean injectIfNull) {
		return (UrlBuilder) __getInjectIfNull__(FIELD_URL, injectIfNull);
	}

	@Override
	public NavigationBuilder setUrl(UrlBuilder url) {
		this.url = url;
		return this;
	}
	
	@Override
	public ObjectByObjectMap getParameterMap() {
		return parameterMap;
	}
	
	@Override
	public NavigationBuilder setParameterMap(ObjectByObjectMap parameterMap) {
		this.parameterMap = parameterMap;
		return this;
	}
	
	@Override
	public ObjectByObjectMap getParameterMap(Boolean injectIfNull) {
		return (ObjectByObjectMap) __getInjectIfNull__(FIELD_PARAMETER_MAP, injectIfNull);
	}
	
	@Override
	public NavigationBuilder setParameters(Object... keyValues) {
		getParameterMap(Boolean.TRUE).set(keyValues);
		return this;
	}
	
	public static final String FIELD_URL = "url";
	public static final String FIELD_PARAMETER_MAP = "parameterMap";
}
