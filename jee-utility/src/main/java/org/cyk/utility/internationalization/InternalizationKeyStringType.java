package org.cyk.utility.internationalization;

import lombok.Getter;

@Getter
public enum InternalizationKeyStringType {

	NOUN
	,VERB
	,GENDER
	,MASCULINE
	,FEMININE
	,SINGULAR
	,PLURAL
	;
	
	private String format;
	
	private InternalizationKeyStringType() {
		format = "%s.__"+name().toLowerCase()+"__";
	}
	
}
