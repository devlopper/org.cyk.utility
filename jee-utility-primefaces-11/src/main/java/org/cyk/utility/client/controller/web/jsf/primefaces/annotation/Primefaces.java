package org.cyk.utility.client.controller.web.jsf.primefaces.annotation;

import javax.enterprise.util.AnnotationLiteral;

@javax.inject.Qualifier
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.PARAMETER })
public @interface Primefaces {

	public static class Class extends AnnotationLiteral<Primefaces> {
		private static final long serialVersionUID = 1L;
		
	}
	
}
