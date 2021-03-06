package org.cyk.utility.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Deployment {
	
	public static enum InitialisationType{EAGER,LAZY}
	
	InitialisationType initialisationType() default InitialisationType.LAZY;
	
	int order() default 0;

}