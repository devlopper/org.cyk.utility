package org.cyk.utility.object;

import java.util.Collection;

@Deprecated
public interface ObjectToStringBuilder extends ObjectToOrFromStringBuilder<String> {

	Object getObject();
	ObjectToStringBuilder setObject(Object object);
	
	ObjectToStringBuilder addFieldNamesStrings(Collection<String> fieldNamesStrings);
	ObjectToStringBuilder addFieldNamesStrings(String...fieldNamesStrings);
}
