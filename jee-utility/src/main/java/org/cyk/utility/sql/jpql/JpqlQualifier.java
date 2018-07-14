package org.cyk.utility.sql.jpql;

import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.__kernel__.DependencyInjection;

public class JpqlQualifier extends AnnotationLiteral<Jpql> implements Jpql {
	private static final long serialVersionUID = 1L;
	
	public static <T> T inject(Class<T> aClass){
		return DependencyInjection.injectByQualifiersClasses(aClass, JpqlQualifier.class);
	}
	
}