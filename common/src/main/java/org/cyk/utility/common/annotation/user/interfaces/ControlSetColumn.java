package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@Target(value={ElementType.TYPE})
public @interface ControlSetColumn {

	int index() ;
	int span() default 1;
	
}
