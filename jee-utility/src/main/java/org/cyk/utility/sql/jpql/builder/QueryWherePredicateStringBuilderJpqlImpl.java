package org.cyk.utility.sql.jpql.builder;

import java.io.Serializable;

import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.sql.builder.AbstractQueryWherePredicateStringBuilderImpl;
import org.cyk.utility.sql.jpql.JpqlQualifier;

public class QueryWherePredicateStringBuilderJpqlImpl extends AbstractQueryWherePredicateStringBuilderImpl
		implements QueryWherePredicateStringBuilderJpql, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected <OBJECT> OBJECT ____inject____(Class<OBJECT> aClass, AnnotationLiteral<?>... annotationLiterals) {
		return JpqlQualifier.map(aClass, annotationLiterals);
	}
	
}
