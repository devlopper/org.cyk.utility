package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;

public class InternalizationStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements InternalizationStringBuilder,Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		String result = null;
		String key = getKey();
		if(__injectStringHelper__().isBlank(key)) {
			result = "##??KEY_NOT_DEF??##";
		}else {
			result = null;
			Properties properties = new Properties();
			properties.setKey(key);
			properties.setParameters(getParameters());
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
			if(__injectStringHelper__().isBlank(result))
				result = "##??"+key+"??##";
			
		}
		return result;
	}
	
	@Override
	public String getKey() {
		return (String) getProperties().getKey();
	}

	@Override
	public InternalizationStringBuilder setKey(String key) {
		getProperties().setKey(key);
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

}
