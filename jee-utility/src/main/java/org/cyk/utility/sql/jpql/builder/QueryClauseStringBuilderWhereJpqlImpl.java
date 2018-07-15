package org.cyk.utility.sql.jpql.builder;

import java.io.Serializable;

import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.sql.builder.AbstractQueryClauseStringBuilderWhereImpl;
import org.cyk.utility.sql.jpql.Jpql;
import org.cyk.utility.sql.jpql.JpqlQualifier;

@Jpql
public class QueryClauseStringBuilderWhereJpqlImpl extends AbstractQueryClauseStringBuilderWhereImpl implements QueryClauseStringBuilderWhereJpql, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected <OBJECT> OBJECT ____inject____(Class<OBJECT> aClass, AnnotationLiteral<?>... annotationLiterals) {
		return JpqlQualifier.map(aClass, annotationLiterals);
	}
	
}
