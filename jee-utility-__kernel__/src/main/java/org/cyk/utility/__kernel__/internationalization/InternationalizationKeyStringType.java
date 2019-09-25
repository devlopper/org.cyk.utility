package org.cyk.utility.__kernel__.internationalization;

import lombok.Getter;

@Getter
public enum InternationalizationKeyStringType {

	NOUN
	,VERB
	,GENDER
	,MASCULINE
	,FEMININE
	,SINGULAR
	,PLURAL
	;
	
	private String format;
	
	private InternationalizationKeyStringType() {
		format = "%s.__"+name().toLowerCase()+"__";
	}
	
}
