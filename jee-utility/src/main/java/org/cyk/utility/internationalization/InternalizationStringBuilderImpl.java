package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.Strings;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;
import org.cyk.utility.system.exception.EntityNotFoundException;
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
				}else if(keyBuilder.getValue() instanceof ConnectException) {
					if(parameters == null)
						parameters = new ArrayList<Object>();
					ConnectException connectException = (ConnectException) keyBuilder.getValue();
					parameters.add(connectException.getMessage().trim());	
				}else if(keyBuilder.getValue() instanceof ServiceNotFoundException) {
					if(parameters == null)
						parameters = new ArrayList<Object>();
					ServiceNotFoundException serviceNotFoundException = (ServiceNotFoundException) keyBuilder.getValue();
					parameters.add(__inject__(InternalizationStringBuilder.class).setKeyValue(serviceNotFoundException.getSystemAction())
							.setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput());
					parameters.add(__inject__(InternalizationStringBuilder.class).setKeyValue(serviceNotFoundException.getSystemAction().getEntityClass()).execute().getOutput());
				}else if(keyBuilder.getValue() instanceof EntityNotFoundException) {
					if(parameters == null)
						parameters = new ArrayList<Object>();
					EntityNotFoundException entityNotFoundException = (EntityNotFoundException) keyBuilder.getValue();
					parameters.add(__inject__(InternalizationStringBuilder.class).setKeyValue(entityNotFoundException.getSystemAction().getEntityClass())
							.execute().getOutput());
					parameters.add(entityNotFoundException.getSystemAction().getEntitiesIdentifiers().getFirst());
				}else if(keyBuilder.getValue() instanceof RuntimeException) {
					if(parameters == null)
						parameters = new ArrayList<Object>();
					RuntimeException runtimeException = (RuntimeException) keyBuilder.getValue();
					parameters.add(runtimeException.getMessage());
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
			
			//5 - derive from related
			if(__injectStringHelper__().isBlank(result)) {
				Collection<Strings> related = __inject__(InternalizationKeyRelatedStringsBuilder.class).setKey(key).execute().getOutput();
				if(__injectCollectionHelper__().isNotEmpty(related)) {
					for(Strings index : related) {
						InternalizationPhraseBuilder phraseBuilder = __inject__(InternalizationPhraseBuilder.class);
						phraseBuilder.addStringsByKeys(__injectCollectionHelper__().cast(Object.class, index.get()));
						result = phraseBuilder.execute().getOutput();
						if(__injectStringHelper__().isNotBlank(result))
							break;
					}
				}
			}
			
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
