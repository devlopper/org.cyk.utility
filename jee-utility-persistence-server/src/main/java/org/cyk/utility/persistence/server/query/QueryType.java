package org.cyk.utility.persistence.server.query;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum QueryType {

	/*READ
	,*/READ_ONE(List.of("readByIdentifier","readByCode","readBySystemIdentifier","readByBusinessIdentifier"))
	,READ_MANY(List.of("readBy"))
	,COUNT(List.of("count"))
	
	;
	
	private Collection<String> prefixes;
	
	/**/
	
	public Boolean isOneOfPrefixesStarts(String string) {
		if(StringHelper.isBlank(string) || CollectionHelper.isEmpty(prefixes))
			return Boolean.FALSE;
		for(String prefix : prefixes)
			if(StringUtils.startsWith(string, prefix))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	/**/
	
	public static QueryType getOneOfPrefixesStarts(String string) {
		for(QueryType queryType : QueryType.values())
			if(queryType.isOneOfPrefixesStarts(string))
				return queryType;
		return null;
	}
}
