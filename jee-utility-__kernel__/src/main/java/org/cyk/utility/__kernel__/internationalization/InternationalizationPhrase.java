package org.cyk.utility.__kernel__.internationalization;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.string.Case;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InternationalizationPhrase extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private InternationalizationStrings strings;
	private Locale locale;
	private Case kase;
	private String value;
	
	public InternationalizationStrings getStrings(Boolean injectIfNull) {
		if(strings == null && Boolean.TRUE.equals(injectIfNull))
			strings = __inject__(InternationalizationStrings.class);
		return strings;
	}
	
	public InternationalizationPhrase addString(Object key,InternationalizationKeyStringType type,Object[] arguments) {
		InternationalizationString internationalizationString = new InternationalizationString();
		internationalizationString.setKey(InternationalizationHelper.buildKey(key,type));
		internationalizationString.setArguments(arguments);
		getStrings(Boolean.TRUE).add(internationalizationString);
		return this;
	}
	
	public InternationalizationPhrase addString(Object key,InternationalizationKeyStringType type) {
		return addString(key, type,null);
	}
	
	public InternationalizationPhrase addString(Object key) {
		return addString(key, null,null);
	}
	
	public InternationalizationPhrase addNoun(Object key) {
		return addString(key, InternationalizationKeyStringType.NOUN);
	}
	
	public InternationalizationPhrase addVerb(Object key) {
		return addString(key, InternationalizationKeyStringType.VERB);
	}
}