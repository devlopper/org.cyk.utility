package org.cyk.utility.sql.jpql.builder;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.sql.builder.AbstractQueryClauseStringBuilderSelectImpl;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.Jpql;
import org.cyk.utility.sql.jpql.JpqlQualifier;

@Dependent @Jpql
public class QueryClauseStringBuilderSelectJpqlImpl extends AbstractQueryClauseStringBuilderSelectImpl implements QueryClauseStringBuilderSelectJpql, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getAllColumnsArgument(Collection<Tuple> tuples) {
		return CollectionHelper.getFirst(tuples).getAlias();
	}
	
	@Override
	protected <OBJECT> OBJECT ____inject____(Class<OBJECT> aClass, AnnotationLiteral<?>... annotationLiterals) {
		return JpqlQualifier.map(aClass, annotationLiterals);
	}

}
