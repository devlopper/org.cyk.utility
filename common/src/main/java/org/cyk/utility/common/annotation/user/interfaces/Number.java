package org.cyk.utility.common.annotation.user.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Number {

	long integer() default 0;
	
	long decimal() default 0;
}
