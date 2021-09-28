package org.cyk.utility.__kernel__.configuration;

import java.io.Serializable;

public interface ClassIdentifierGetter {

	String get(Class<?> klass);
	
	public static abstract class AbstractImpl implements ClassIdentifierGetter,Serializable {
		
	}
}