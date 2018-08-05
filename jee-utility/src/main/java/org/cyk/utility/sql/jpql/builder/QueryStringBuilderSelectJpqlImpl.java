package org.cyk.utility.sql.jpql.builder;

import java.io.Serializable;

import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.sql.builder.AbstractQueryStringBuilderSelectImpl;
import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilder;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.Jpql;
import org.cyk.utility.sql.jpql.JpqlQualifier;

@Jpql
public class QueryStringBuilderSelectJpqlImpl extends AbstractQueryStringBuilderSelectImpl implements QueryStringBuilderSelectJpql, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public QueryStringBuilderSelectJpql from(Tuple tuple) {
		return (QueryStringBuilderSelectJpql) super.from(tuple);
	}
	
	@Override
	public QueryStringBuilderSelectJpql where(QueryWherePredicateStringBuilder predicateBuilder) {
		return (QueryStringBuilderSelectJpql) super.where(predicateBuilder);
	}
	
	@Override
	public QueryStringBuilderSelectJpql select(Tuple tuple) {
		return (QueryStringBuilderSelectJpql) super.select(tuple);
	}
	
	@Override
	public QueryStringBuilderSelectJpql orderBy(String... attributeNames) {
		return (QueryStringBuilderSelectJpql) super.orderBy(attributeNames);
	}
	
	/**/
	
	@Override
	protected <OBJECT> OBJECT ____inject____(Class<OBJECT> aClass, AnnotationLiteral<?>... annotationLiterals) {
		return JpqlQualifier.map(aClass, annotationLiterals);
	}
}
