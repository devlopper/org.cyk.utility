package org.cyk.utility.__kernel__.annotation;

import javax.enterprise.util.AnnotationLiteral;

@javax.inject.Qualifier
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.PARAMETER })
public @interface Representation {

	public static class Class extends AnnotationLiteral<Representation> {
		private static final long serialVersionUID = 1L;
		
	}
	
}
