package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringHelper;

@Dependent @Deprecated
public class InternalizationPhraseBuilderImpl extends AbstractStringFunctionImpl implements InternalizationPhraseBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private InternalizationStringBuilders strings;
	private Locale locale;
	private Case aCase;
	
	@Override
	protected String __execute__() throws Exception {
		Collection<String> stringsCollection = null;
		InternalizationStringBuilders strings = getStrings();
		if(__injectCollectionHelper__().isNotEmpty(strings)) {
			stringsCollection = new ArrayList<>();
			Locale locale = getLocale();
			for(InternalizationStringBuilder index : strings.get()) {
				if(index.getLocale() == null)
					index.setLocale(locale);
				//if(index.getCase() == null)
				//	index.setCase(Case.LOWER);
				stringsCollection.add(index.execute().getOutput());
			}
		}
		Case aCase = getCase();
		//if(aCase == null)
		//	aCase = Case.FIRST_CHARACTER_UPPER;
		String string = __injectStringHelper__().concatenate(stringsCollection,SEPARATOR);
		string = __inject__(StringHelper.class).applyCase(string, aCase);	
		return string;
	}
	
	@Override
	public InternalizationStringBuilders getStrings() {
		return strings;
	}
	@Override
	public InternalizationStringBuilders getStrings(Boolean injectIfNull) {
		return (InternalizationStringBuilders) __getInjectIfNull__(FIELD_STRINGS, injectIfNull);
	}
	@Override
	public InternalizationPhraseBuilder setStrings(InternalizationStringBuilders strings) {
		this.strings = strings;
		return this;
	}
	@Override
	public InternalizationPhraseBuilder addStrings(Collection<InternalizationStringBuilder> strings) {
		getStrings(Boolean.TRUE).add(strings);
		return this;
	}
	@Override
	public InternalizationPhraseBuilder addStrings(InternalizationStringBuilder... strings) {
		getStrings(Boolean.TRUE).add(strings);
		return this;
	}
	@Override
	public InternalizationPhraseBuilder addStringsByKeys(Collection<Object> keys) {
		if(__injectCollectionHelper__().isNotEmpty(keys)) {
			for(Object index : keys)
				getStrings(Boolean.TRUE).add(__inject__(InternalizationStringBuilder.class).setKeyValue(index));
		}
		return this;
	}
	@Override
	public InternalizationPhraseBuilder addStringsByKeys(Object... keys) {
		addStringsByKeys(__injectCollectionHelper__().instanciate(keys));
		return this;
	}
	@Override
	public Locale getLocale() {
		return locale;
	}
	@Override
	public InternalizationPhraseBuilder setLocale(Locale locale) {
		this.locale = locale;
		return this;
	}
	@Override
	public Case getCase() {
		return aCase;
	}
	@Override
	public InternalizationPhraseBuilder setCase(Case aCase) {
		this.aCase = aCase;
		return this;
	}
	
	public static final String FIELD_STRINGS = "strings";
	
	private static final String SEPARATOR = ConstantCharacter.SPACE.toString();
	
}
