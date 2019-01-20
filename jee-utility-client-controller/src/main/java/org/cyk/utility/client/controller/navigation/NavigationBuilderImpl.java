package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterNameStringBuilder;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueStringBuilder;
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
	private NavigationIdentifierStringBuilder identifierBuilder;
	private Boolean isDeriveParametersFromSystemAction;
	
	@Override
	protected Navigation __execute__() throws Exception {
		Navigation navigation = __inject__(Navigation.class);
		
		SystemAction systemAction = getSystemAction();
		if(systemAction == null) {
			NavigationIdentifierStringBuilder identifierBuilder = getIdentifierBuilder();
			if(identifierBuilder!=null)
				systemAction = identifierBuilder.getSystemAction();
		}
		navigation.setSystemAction(systemAction);
		
		Object identifier = getIdentifier();
		if(identifier == null) {
			NavigationIdentifierStringBuilder identifierBuilder = getIdentifierBuilder();
			if(identifierBuilder!=null)
				identifier = identifierBuilder.execute().getOutput();
		}
		
		if(identifier!=null) {
			
			UrlBuilder url = getUrl();
			if(url==null) {
				url = __inject__(UrlBuilder.class);
				
				String urlString = __inject__(NavigationIdentifierToUrlStringMapper.class).setIdentifier(identifier).execute().getOutput();
				if(__injectStringHelper__().isBlank(urlString) && systemAction!=null) {
					Class<?> aClass = systemAction.getEntities() == null ? null : systemAction.getEntities().getElementClass();
					//Class<?> aClass = systemAction.getEntities(Boolean.TRUE).getElementClass();
					if(systemAction.getEntities()!=null)
						systemAction.getEntities().setElementClass(null);
					String identifier02 = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
					if(systemAction.getEntities()!=null)
						systemAction.getEntities().setElementClass(aClass);
					
					if(__injectStringHelper__().isNotBlank(identifier02)) {
						urlString = __inject__(NavigationIdentifierToUrlStringMapper.class).setIdentifier(identifier02).execute().getOutput();
						if(__injectStringHelper__().isNotBlank(urlString)) {
							identifier = identifier02;
						}
					}
				}
				
				if(__injectStringHelper__().isBlank(urlString))
					__injectThrowableHelper__().throwRuntimeException("url not found for identifier "+identifier);
				url.getString(Boolean.TRUE).getUniformResourceIdentifierString(Boolean.TRUE).setString(urlString);
				
				ObjectByObjectMap parameterMap = getParameterMap();
				Boolean isDeriveParametersFromSystemAction = getIsDeriveParametersFromSystemAction();
				if((isDeriveParametersFromSystemAction == null || Boolean.TRUE.equals(isDeriveParametersFromSystemAction)) && systemAction!=null) {
					NavigationIdentifierStringBuilder identifierBuilder = getIdentifierBuilder();
					if(identifierBuilder!=null) {
						if(parameterMap == null) {
							parameterMap = __inject__(ObjectByObjectMap.class);
							parameterMap.setIsSequential(Boolean.TRUE);	
						}
						if(systemAction.getEntities()!=null) {
							if(systemAction.getEntities().getElementClass()!=null)
								parameterMap.set(
										__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsEntityClass()
										,__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(systemAction.getEntities().getElementClass())
										);
							if(__injectCollectionHelper__().isNotEmpty(systemAction.getEntities()))
								parameterMap.set(
										__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsEntityIdentifier()
										,__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(__injectCollectionHelper__().getFirst(systemAction.getEntities().get()))
										);
							
							if(__injectCollectionHelper__().isNotEmpty(systemAction.getEntitiesIdentifiers()))
								parameterMap.set(
										__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsEntityIdentifier()
										,__injectCollectionHelper__().getFirst(systemAction.getEntitiesIdentifiers().get())
										);
						}
						parameterMap.set(
								__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsActionClass()
								,__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(systemAction.getClass())
								);
						
						parameterMap.set(
								__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsActionIdentifier()
								,__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(systemAction)
								);
						
						if(systemAction.getNextAction()!=null) {
							parameterMap.set(
									__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsNextActionClass()
									,__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(systemAction.getNextAction().getClass())
									);
							
							parameterMap.set(
									__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsNextActionIdentifier()
									,__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(systemAction.getNextAction())
									);
						}
					}
				}
				url.getString(Boolean.TRUE).getUniformResourceIdentifierString(Boolean.TRUE).setParameterMap(parameterMap);
			}
			
			navigation.setIdentifier(identifier);
			if(url!=null)
				navigation.setUniformResourceLocator(url.execute().getOutput());
		}
		
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
		
		
		
		return navigation;
	}
	
	@Override
	public NavigationIdentifierStringBuilder getIdentifierBuilder() {
		return identifierBuilder;
	}
	@Override
	public NavigationIdentifierStringBuilder getIdentifierBuilder(Boolean injectIfNull) {
		return (NavigationIdentifierStringBuilder) __getInjectIfNull__(FIELD_IDENTIFIER_BUILDER, injectIfNull);
	}
	
	@Override
	public NavigationBuilder setIdentifierBuilder(NavigationIdentifierStringBuilder identifierBuilder) {
		this.identifierBuilder = identifierBuilder;
		return this;
	}
	
	@Override
	public NavigationBuilder setIdentifierBuilderSystemAction(SystemAction systemAction) {
		getIdentifierBuilder(Boolean.TRUE).setSystemAction(systemAction);
		return this;
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
		ObjectByObjectMap map = (ObjectByObjectMap) __getInjectIfNull__(FIELD_PARAMETER_MAP, injectIfNull);
		map.setIsSequential(Boolean.TRUE);
		return map;
	}
	
	@Override
	public NavigationBuilder setParameters(Object... keyValues) {
		if(__inject__(ArrayHelper.class).isNotEmpty(keyValues))
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
	
	@Override
	public Boolean getIsDeriveParametersFromSystemAction() {
		return isDeriveParametersFromSystemAction;
	}
	
	@Override
	public NavigationBuilder setIsDeriveParametersFromSystemAction(Boolean isDeriveParametersFromSystemAction) {
		this.isDeriveParametersFromSystemAction = isDeriveParametersFromSystemAction;
		return this;
	}
	
	@Override
	public NavigationBuilder setIdentifier(Object identifier) {
		return (NavigationBuilder) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_URL = "url";
	public static final String FIELD_PARAMETER_MAP = "parameterMap";
	public static final String FIELD_IDENTIFIER_BUILDER = "identifierBuilder";
	public static final String FIELD_DYNAMIC_PARAMETER_NAMES = "dynamicParameterNames";
}
