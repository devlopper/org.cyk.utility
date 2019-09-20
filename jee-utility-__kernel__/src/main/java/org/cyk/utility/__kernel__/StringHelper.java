package org.cyk.utility.__kernel__;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public interface StringHelper {

	static Collection<String> splitByCharacterTypeCamelCase(String string) {
		if(string == null || string.isBlank())
			return null;
		String[] strings =	StringUtils.splitByCharacterTypeCamelCase(string);
		return strings == null || strings.length == 0 ? null : List.of(strings);
	}
	
}
