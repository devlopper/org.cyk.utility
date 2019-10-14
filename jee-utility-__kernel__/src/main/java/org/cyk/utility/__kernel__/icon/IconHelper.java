package org.cyk.utility.__kernel__.icon;

import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface IconHelper {

	static String getIdentifier(Icon icon,IconIdentifierGetter getter) {
		String identifier = IDENTIFIERS.get(icon);
		if(identifier != null)
			return identifier;
		if(getter == null)
			getter = IconIdentifierGetter.FONT_AWSOME;
		identifier = getter.get(icon);
		if(StringHelper.isBlank(identifier))
			return null;
		IDENTIFIERS.put(icon, identifier);
		return identifier;
	}
	
	static String getIdentifier(Icon icon) {
		return getIdentifier(icon,IconIdentifierGetter.FONT_AWSOME);
	}
	
	Map<Icon,String> IDENTIFIERS = new HashMap<>();
}
