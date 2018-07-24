package org.cyk.utility.server.persistence.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.cyk.utility.string.StringConstant;

@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface Query {

	String identifier() default StringConstant.EMPTY;
	String value() default StringConstant.EMPTY;
	Class<?> resultClass() default Void.class;
	
	/**/
	
}
