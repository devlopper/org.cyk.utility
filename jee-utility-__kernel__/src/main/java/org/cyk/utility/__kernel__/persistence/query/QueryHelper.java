package org.cyk.utility.__kernel__.persistence.query;

import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface QueryHelper {

	/* get */
	
	static String getIdentifier(Class<?> klass,String name) {
		if(klass == null || StringHelper.isBlank(name))
			return null;
		Map<String,String> map = IDENTIFIERS.get(klass);
		if(map == null)
			IDENTIFIERS.put(klass, map = new HashMap<>());
		String identifier = map.get(name);
		if(StringHelper.isBlank(identifier))
			map.put(name, identifier = QueryIdentifierBuilder.getInstance().build(klass,name));
		return identifier;
	}
	
	static String getIdentifierReadByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readByFilters");
	}
	
	static String getIdentifierCountByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countByFilters");
	}
	
	static String getIdentifierReadByFiltersLike(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readByFiltersLike");
	}
	
	static String getIdentifierCountByFiltersLike(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countByFiltersLike");
	}
	
	static String getIdentifierReadWhereCodeNotInByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readWhereCodeNotInByFilters");
	}
	
	static String getIdentifierCountWhereCodeNotInByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countWhereCodeNotInByFilters");
	}
	
	static String getIdentifierReadWhereBusinessIdentifierOrNameContains(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readWhereBusinessIdentifierOrNameContains");
	}
	
	static String getIdentifierCountWhereBusinessIdentifierOrNameContains(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countWhereBusinessIdentifierOrNameContains");
	}
	
	/**/
	
	Map<Class<?>,Map<String,String>> IDENTIFIERS = new HashMap<>();
	
	static void clear() {
		IDENTIFIERS.clear();
	}
}
