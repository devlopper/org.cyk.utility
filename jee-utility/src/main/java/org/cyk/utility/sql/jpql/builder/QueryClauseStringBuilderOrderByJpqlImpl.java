package org.cyk.utility.sql.jpql.builder;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.sql.builder.AbstractQueryClauseStringBuilderOrderByImpl;
import org.cyk.utility.sql.jpql.Jpql;
import org.cyk.utility.sql.jpql.JpqlQualifier;

@Dependent @Jpql
public class QueryClauseStringBuilderOrderByJpqlImpl extends AbstractQueryClauseStringBuilderOrderByImpl implements QueryClauseStringBuilderOrderByJpql, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected <OBJECT> OBJECT ____inject____(Class<OBJECT> aClass, AnnotationLiteral<?>... annotationLiterals) {
		return JpqlQualifier.map(aClass, annotationLiterals);
	}

}
