package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;
import org.cyk.utility.system.exception.EntityNotFoundException;
import org.cyk.utility.system.exception.ServiceNotFoundException;

@Dependent @Deprecated
public class InternalizationStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements InternalizationStringBuilder,Serializable{
	private static final long serialVersionUID = 1L;

	private InternalizationKeyStringBuilder keyBuilder;
	private String key;
	private Collection<Object> parameters;
	private Locale locale;
	private Case stringCase;
	
	@Override
	protected String __execute__() throws Exception {
		String result = null;
		String key = getKey();
		Collection<Object> parameters = getParameters();
		if(StringHelper.isBlank(key)) {
			InternalizationKeyStringBuilder keyBuilder = getKeyBuilder();
			if(keyBuilder!=null)
				key = keyBuilder.execute().getOutput();
			if(StringHelper.isNotBlank(key)) {
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
							.setKeyType(InternationalizationKeyStringType.NOUN).execute().getOutput());
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
		if(StringHelper.isBlank(key)) {
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
			if(StringHelper.isBlank(result))
				result = null;
			
			//3 - database
			if(StringHelper.isBlank(result))
				result = null;
			
			//4 - bundles
			if(StringHelper.isBlank(result))
				result = __inject__(StringRepositoryResourceBundle.class).getOne(properties);
			
			//5 - derive from related
			if(StringHelper.isBlank(result)) {
				Collection<Strings> related = __inject__(InternalizationKeyRelatedStringsBuilder.class).setKey(key).execute().getOutput();
				if(CollectionHelper.isNotEmpty(related)) {
					for(Strings index : related) {
						InternalizationPhraseBuilder phraseBuilder = __inject__(InternalizationPhraseBuilder.class);
						phraseBuilder.addStringsByKeys(CollectionHelper.cast(Object.class, index.get()));
						result = phraseBuilder.execute().getOutput();
						if(StringHelper.isNotBlank(result))
							break;
					}
				}
			}
			
			//
			if(StringHelper.isBlank(result)) {
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
		if(keyBuilder == null && Boolean.TRUE.equals(injectIfNull))
			keyBuilder = __inject__(InternalizationKeyStringBuilder.class);
		return keyBuilder;
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
	public InternalizationStringBuilder setKeyType(InternationalizationKeyStringType type) {
		getKeyBuilder(Boolean.TRUE).setType(type);
		return this;
	}

	@Override
	public Collection<Object> getParameters() {
		return parameters;
	}

	@Override
	public InternalizationStringBuilder setParameters(Collection<Object> parameters) {
		this.parameters = parameters;
		return this;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public InternalizationStringBuilder setLocale(Locale locale) {
		this.locale = locale;;
		return this;
	}

	@Override
	public Case getCase() {
		return stringCase;
	}

	@Override
	public InternalizationStringBuilder setCase(Case aCase) {
		this.stringCase = aCase;
		return this;
	}
	
	/**/
	
	public static final String FIELD_KEY_BUILDER = "keyBuilder";
	
}
