package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;
import org.cyk.utility.system.exception.ServiceNotFoundException;

public class InternalizationStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements InternalizationStringBuilder,Serializable{
	private static final long serialVersionUID = 1L;

	private InternalizationKeyStringBuilder keyBuilder;
	private String key;
	
	@Override
	protected String __execute__() throws Exception {
		String result = null;
		String key = getKey();
		Collection<Object> parameters = getParameters();
		if(__injectStringHelper__().isBlank(key)) {
			InternalizationKeyStringBuilder keyBuilder = getKeyBuilder();
			if(keyBuilder!=null)
				key = keyBuilder.execute().getOutput();
			if(__injectStringHelper__().isNotBlank(key)) {
				if(keyBuilder.getValue() instanceof UnknownHostException) {
					if(parameters == null)
						parameters = new ArrayList<Object>();
					parameters.add(((UnknownHostException)keyBuilder.getValue()).getMessage().trim());	
				}else if(keyBuilder.getValue() instanceof ServiceNotFoundException) {
					if(parameters == null)
						parameters = new ArrayList<Object>();
					ServiceNotFoundException serviceNotFoundException = (ServiceNotFoundException) keyBuilder.getValue();
					parameters.add(__inject__(InternalizationStringBuilder.class).setKeyValue(serviceNotFoundException.getSystemAction())
							.setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput());
					parameters.add(__inject__(InternalizationStringBuilder.class).setKeyValue(serviceNotFoundException.getSystemAction().getEntityClass()).execute().getOutput());
				}				
			}
		}
		if(__injectStringHelper__().isBlank(key)) {
			result = "##??KEY_NOT_DEF??##";
		}else {
			result = null;
			Properties properties = new Properties();
			properties.setKey(key);
			
			if(parameters!=null)
				properties.setParameters(parameters.toArray());
			properties.setLocale(getLocale());
			properties.setCase(getCase());
			//1 - cache
			//Build cache identifier from properties
			
			//2 - user
			if(__injectStringHelper__().isBlank(result))
				result = null;
			
			//3 - database
			if(__injectStringHelper__().isBlank(result))
				result = null;
			
			//4 - bundles
			if(__injectStringHelper__().isBlank(result))
				result = __inject__(StringRepositoryResourceBundle.class).getOne(properties);
			
			//
			if(__injectStringHelper__().isBlank(result)) {
				result = "##??"+key+"??##";
			}
			
		}
		return result;
	}
	
	@Override
	public InternalizationKeyStringBuilder getKeyBuilder() {
		return keyBuilder;
	}
	
	@Override
	public InternalizationKeyStringBuilder getKeyBuilder(Boolean injectIfNull) {
		return (InternalizationKeyStringBuilder) __getInjectIfNull__(FIELD_KEY_BUILDER, injectIfNull);
	}
	
	@Override
	public InternalizationStringBuilder setKeyBuilder(InternalizationKeyStringBuilder key) {
		this.keyBuilder = key;
		return this;
	}
	
	@Override
	public InternalizationStringBuilder setKeyValue(Object key) {
		getKeyBuilder(Boolean.TRUE).setValue(key);
		return this;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public InternalizationStringBuilder setKey(String key) {
		this.key = key;
		return this;
	}
	
	@Override
	public InternalizationStringBuilder setKeyType(InternalizationKeyStringType type) {
		getKeyBuilder(Boolean.TRUE).setType(type);
		return this;
	}

	@Override
	public Collection<Object> getParameters() {
		return (Collection<Object>) getProperties().getParameters();
	}

	@Override
	public InternalizationStringBuilder setParameters(Collection<Object> parameters) {
		getProperties().setParameters(parameters);
		return this;
	}

	@Override
	public Locale getLocale() {
		return (Locale) getProperties().getLocale();
	}

	@Override
	public InternalizationStringBuilder setLocale(Locale locale) {
		getProperties().setLocale(locale);
		return this;
	}

	@Override
	public Case getCase() {
		return (Case) getProperties().getCase();
	}

	@Override
	public InternalizationStringBuilder setCase(Case aCase) {
		getProperties().setCase(aCase);
		return this;
	}
	
	/**/
	
	public static final String FIELD_KEY_BUILDER = "keyBuilder";

}
