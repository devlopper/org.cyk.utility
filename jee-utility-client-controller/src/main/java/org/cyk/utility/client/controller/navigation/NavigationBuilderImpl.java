package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.object.ObjectByObjectMap;
import org.cyk.utility.object.Objects;
import org.cyk.utility.resource.locator.UrlBuilder;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public class NavigationBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Navigation> implements NavigationBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private UrlBuilder url;
	private ObjectByObjectMap parameterMap;
	private Objects dynamicParameterNames;
	private SystemAction systemAction;
	
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
		
		Objects dynamicParameterNames = getDynamicParameterNames();
		if(__injectCollectionHelper__().isNotEmpty(dynamicParameterNames)) {
			navigation.setDynamicParameterNames(__inject__(Strings.class));
			for(Object index : dynamicParameterNames.get()) {
				if(index!=null)
					if(index instanceof String) {
						navigation.getDynamicParameterNames().add((String)index);
					}else {
						navigation.getDynamicParameterNames().add(index.toString());
					}
			}
		}
		
		SystemAction systemAction = getSystemAction();
		navigation.setSystemAction(systemAction);
		
		return navigation;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}
	
	@Override
	public NavigationBuilder setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
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
	
	@Override
	public Objects getDynamicParameterNames() {
		return dynamicParameterNames;
	}
	
	@Override
	public Objects getDynamicParameterNames(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_DYNAMIC_PARAMETER_NAMES, injectIfNull);
	}
	
	@Override
	public NavigationBuilder setDynamicParameterNames(Objects dynamicParameterNames) {
		this.dynamicParameterNames = dynamicParameterNames;
		return this;
	}
	
	public static final String FIELD_URL = "url";
	public static final String FIELD_PARAMETER_MAP = "parameterMap";
	public static final String FIELD_DYNAMIC_PARAMETER_NAMES = "dynamicParameterNames";
}
