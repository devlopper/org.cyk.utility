package org.cyk.utility.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@Target(value={ElementType.TYPE})
public @interface FieldOverride {

	String name();
	Class<?> type();
	
}
