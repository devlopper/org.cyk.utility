package org.cyk.utility.__kernel__.enumeration;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface EnumerationHelper {

	@SuppressWarnings("unchecked")
	static <T extends Enum<T>> T getByName(Class<T> enumerationClass,String name,Boolean isCaseSensitive) {
		if(enumerationClass == null || StringHelper.isBlank(name))
			return null;
		for(Enum<T> index : enumerationClass.getEnumConstants()) {
			if(Boolean.TRUE.equals(isCaseSensitive)) {
				if(index.name().equals(name))
					return (T) index;
			}else {
				if(index.name().equalsIgnoreCase(name))
					return (T) index;
			}
		}
		return null;
	}
	
}
