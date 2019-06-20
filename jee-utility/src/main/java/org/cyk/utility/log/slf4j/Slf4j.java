package org.cyk.utility.log.slf4j;

import javax.enterprise.util.AnnotationLiteral;

@javax.inject.Qualifier
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.PARAMETER })
public @interface Slf4j {

	public static class Class extends AnnotationLiteral<Slf4j> {
		private static final long serialVersionUID = 1L;
		
	}
	
}
