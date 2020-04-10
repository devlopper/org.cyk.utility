package org.cyk.utility.__kernel__.object.__static__.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Choices {

	Count count() default Count.RUNTIME;
	
	/**/
	
	public static enum Count{RUNTIME,ALL,AUTO_COMPLETE}
}
