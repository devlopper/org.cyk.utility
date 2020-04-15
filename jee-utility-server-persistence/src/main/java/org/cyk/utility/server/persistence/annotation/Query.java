package org.cyk.utility.server.persistence.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;

@Target({ TYPE, METHOD })
@Retention(RUNTIME) @Deprecated
public @interface Query {

	String identifier() default ConstantEmpty.STRING;
	String value() default ConstantEmpty.STRING;
	Class<?> resultClass() default Void.class;
	
	/**/
	
}
