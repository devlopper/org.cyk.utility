package org.cyk.utility.type;

import org.cyk.utility.helper.Helper;

@Deprecated
public interface BooleanHelper extends Helper {

	static Boolean get(Object object) {
		if(object instanceof Boolean)
			return (Boolean) object;
		if(object instanceof String) {
			String string = (String) object;
			if("yes".equalsIgnoreCase(string))
				string = "true";
			return Boolean.parseBoolean(string);
		}
		if(object instanceof Number)
			return ((Number)object).doubleValue() > 0;
		return null;
	}
	
}
