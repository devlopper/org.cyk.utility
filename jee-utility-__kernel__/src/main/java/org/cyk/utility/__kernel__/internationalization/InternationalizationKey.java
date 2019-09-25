package org.cyk.utility.__kernel__.internationalization;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.string.Case;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InternationalizationKey implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;
	private Object[] arguments;
	
	public String buildCacheEntryIdentifier(Locale locale,Case kase) {
		return __buildCacheEntryIdentifier__(Arrays.asList(this),locale,kase);
	}
	
	public static String __buildCacheEntryIdentifier__(Collection<InternationalizationKey> keys,Locale locale,Case kase) {
		if(keys == null || keys.isEmpty())
			return null;
		StringBuilder stringBuilder = new StringBuilder();
		for(InternationalizationKey index : keys) {
			stringBuilder.append(index.value);
			if(index.arguments != null)
				stringBuilder.append(IDENTIFIER_TOKEN_SEPARATOR+ StringUtils.join(index.arguments,IDENTIFIER_TOKEN_SEPARATOR));				
		}
		if(locale != null)
			stringBuilder.append(IDENTIFIER_TOKEN_SEPARATOR+ locale);
		if(kase != null)
			stringBuilder.append(IDENTIFIER_TOKEN_SEPARATOR+ kase);	
		return stringBuilder.toString();
	}		
	private static final String IDENTIFIER_TOKEN_SEPARATOR = "-";
}