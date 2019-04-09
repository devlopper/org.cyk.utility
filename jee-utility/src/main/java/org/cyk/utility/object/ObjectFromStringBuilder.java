package org.cyk.utility.object;

import java.util.Collection;

public interface ObjectFromStringBuilder extends ObjectToOrFromStringBuilder<Object> {

	String getString();
	ObjectFromStringBuilder setString(String string);
	
	Class<?> getKlass();
	ObjectFromStringBuilder setKlass(Class<?> klass);
	
	ObjectFromStringBuilder addFieldNamesStrings(Collection<String> fieldNamesStrings);
	ObjectFromStringBuilder addFieldNamesStrings(String...fieldNamesStrings);
}
