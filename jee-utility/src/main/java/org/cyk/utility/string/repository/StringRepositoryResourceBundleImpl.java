package org.cyk.utility.string.repository;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.locale.LocaleHelper;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueHelper;

@Singleton
public class StringRepositoryResourceBundleImpl extends AbstractStringRepositoryImpl implements StringRepositoryResourceBundle,Serializable {
	private static final long serialVersionUID = 1L;

	//private final Map<String,ClassLoader> map = new LinkedHashMap<>();
	
	/*
	public ResourceBundleRepositoryImpl() {
		for(String index : new String[]{"core","word","phrase","ordinal","class","field","condition","userinterface"})
			addResourceBundle("org.cyk.utility.string.i18n."+index);
	}*/
	
	@Override
	public StringRepositoryResourceBundle addBundle(String baseName, ClassLoader classLoader) {
		Collection<ResourceBundle> bundles = (Collection<ResourceBundle>) getProperties().getResourceBundles();
		if(bundles == null)
			getProperties().setResourceBundles(bundles = new ArrayList<ResourceBundle>());
		Boolean found = Boolean.FALSE;
		for(ResourceBundle index : bundles)
			if(index.getName().equals(baseName) && index.getClassLoader().equals(classLoader)) {
				found = Boolean.TRUE;
				break;
			}
		if(Boolean.FALSE.equals(found))
			bundles.add(new ResourceBundle(baseName, classLoader));
		return this;
	}
	
	@Override
	public StringRepositoryResourceBundle addBundle(String baseName) {
		return addBundle(baseName, StringRepositoryResourceBundleImpl.class.getClassLoader());
	}
	
	@Override
	public StringRepositoryResourceBundle addBundle(String... baseNames) {
		for(String index : baseNames)
			addBundle(index);
		return this;
	}
	
	@Override
	public String getOne(Properties properties) {
		Collection<ResourceBundle> bundles = (Collection<ResourceBundle>) __inject__(CollectionHelper.class).concatenate((Collection<ResourceBundle>)properties.getResourceBundles()
				,(Collection<ResourceBundle>)getProperties().getResourceBundles());
		if(__inject__(CollectionHelper.class).isEmpty(bundles)){
			//TODO log a warning
		}else{
			String identifier = (String) properties.getKey();
			Object[] parameters = (Object[]) properties.getParameters();
			Locale locale = __inject__(ValueHelper.class).defaultToIfNull((Locale)properties.getLocale(), __inject__(LocaleHelper.class).getLocaleDefaultIfNull());
			Case aCase = __inject__(ValueHelper.class).defaultToIfNull((Case)properties.getCase(), Case.DEFAULT);
			Boolean cachable = __inject__(ValueHelper.class).defaultToIfNull((Boolean)properties.getCachable(), Boolean.TRUE);
			
			for(ResourceBundle index : bundles){
				try {
					java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle(index.getName(), locale
							, __inject__(ValueHelper.class).defaultToIfNull(index.getClassLoader(), StringRepositoryResourceBundle.class.getClassLoader()) );
					String value = resourceBundle.getString(identifier);
					if(__inject__(ArrayHelper.class).isNotEmpty(parameters))
						value = MessageFormat.format(value,parameters);
					
					//addLoggingMessageBuilderNamedParameters("location","resource bundle "+entry.getKey());
					
					String substituteCode = null;
					while((substituteCode = StringUtils.substringBetween(value, SUBSTITUTE_TAG_START, SUBSTITUTE_TAG_END)) != null){
						String substituteValue = getOne(properties.setKey(substituteCode).setCase(Case.NONE).setLocale(locale).setCachable(cachable));
						if(substituteValue == null){
							value = formatUnknownIdentifier(substituteCode);
							break;
						}else
							value = StringUtils.replace(value, SUBSTITUTE_TAG_START+substituteCode+SUBSTITUTE_TAG_END, substituteValue);										
					}
					
					if(StringUtils.startsWith(value,DO_NOT_PROCESS_TAG_START) && StringUtils.endsWith(value,DO_NOT_PROCESS_TAG_END)){
						value = StringUtils.substringBetween(value, DO_NOT_PROCESS_TAG_START, DO_NOT_PROCESS_TAG_END);
					}else{
						value = __inject__(StringHelper.class).applyCase(value, aCase);
					}
					
					if(properties.getResourceBundleKeyValueFoundListener() instanceof ResourceBundleKeyValueFoundListener)
						((ResourceBundleKeyValueFoundListener)properties.getResourceBundleKeyValueFoundListener()).execute(properties);
					
					/*
					if(Boolean.TRUE.equals(cachable)){
						Cache.Adapter.Default.add(identifier, locale, caseType, parameters, value);
						String cacheIdentifier = new Builder.CacheIdentifier.Adapter.Default().setInput(identifier).setLocale(locale)
								.addParameters(parameters).addParameters(new Object[]{caseType}).execute();
						Datasource.Cache.REPOSITORY.put(cacheIdentifier, value);
						//addLoggingMessageBuilderNamedParameters("cache identifier",cacheIdentifier,"cache value",value);
					}
					*/
					return value;
				} catch (Exception e) {
					//It is not in that bundle. Let try the next one
					//e.printStackTrace();
				}
			}	
		}
		
		return null;		
	}
	
	private String formatUnknownIdentifier(String identifier){
		return String.format(UNKNOWN_FORMAT, UNKNOWN_MARKER_START,identifier,UNKNOWN_MARKER_END);
	}
	
	/**/
	
	private static final String UNKNOWN_MARKER_START = "##";
	private static final String UNKNOWN_MARKER_END = "##";
	private static final String UNKNOWN_FORMAT = "%s%s%s";
	
	private static final String DO_NOT_PROCESS_TAG = "cyk_donotprocess";
	private static final String DO_NOT_PROCESS_TAG_START = "<"+DO_NOT_PROCESS_TAG+">";
	private static final String DO_NOT_PROCESS_TAG_END = "</"+DO_NOT_PROCESS_TAG+">";
	
	private static final String SUBSTITUTE_TAG = "cyk_code";
	private static final String SUBSTITUTE_TAG_START = "<"+SUBSTITUTE_TAG+">";
	private static final String SUBSTITUTE_TAG_END = "</"+SUBSTITUTE_TAG+">";

}
