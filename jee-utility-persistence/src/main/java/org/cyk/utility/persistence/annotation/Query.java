package org.cyk.utility.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Query {

	Class<?> tupleClass();
	
	String identifier() default ConstantEmpty.STRING;
	
	String name() default ConstantEmpty.STRING;
	
	String value();
	
	Class<?> resultClass() default Void.class;
	
	boolean isDerivable() default true;
}
