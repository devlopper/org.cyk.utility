package org.cyk.utility.sql.jpql.builder;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.sql.builder.AbstractQueryAttributeNameBuilderImpl;
import org.cyk.utility.sql.jpql.Jpql;
import org.cyk.utility.sql.jpql.JpqlQualifier;

@Dependent @Jpql
public class QueryAttributeNameBuilderJpqlImpl extends AbstractQueryAttributeNameBuilderImpl implements QueryAttributeNameBuilderJpql, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected <OBJECT> OBJECT ____inject____(Class<OBJECT> aClass, AnnotationLiteral<?>... annotationLiterals) {
		return JpqlQualifier.map(aClass, annotationLiterals);
	}
	
}
