package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@Target(value={ElementType.TYPE})
public @interface ControlSetRow {

	int index() default -1;
	int span() default 1;
	
}
