package org.cyk.utility.sql.jpql;

import javax.enterprise.util.AnnotationLiteral;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.sql.builder.QueryAttributeNameBuilder;
import org.cyk.utility.sql.builder.QueryClauseStringBuilderFrom;
import org.cyk.utility.sql.builder.QueryClauseStringBuilderSelect;
import org.cyk.utility.sql.builder.QueryClauseStringBuilderWhere;
import org.cyk.utility.sql.builder.QueryOperandStringBuilder;
import org.cyk.utility.sql.builder.QueryParameterNameBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilderEqual;
import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilderGroup;
import org.cyk.utility.sql.jpql.builder.QueryAttributeNameBuilderJpql;
import org.cyk.utility.sql.jpql.builder.QueryClauseStringBuilderFromJpql;
import org.cyk.utility.sql.jpql.builder.QueryClauseStringBuilderSelectJpql;
import org.cyk.utility.sql.jpql.builder.QueryClauseStringBuilderWhereJpql;
import org.cyk.utility.sql.jpql.builder.QueryOperandStringBuilderJpql;
import org.cyk.utility.sql.jpql.builder.QueryParameterNameBuilderJpql;
import org.cyk.utility.sql.jpql.builder.QueryStringBuilderSelectJpql;
import org.cyk.utility.sql.jpql.builder.QueryWherePredicateStringBuilderEqualJpql;
import org.cyk.utility.sql.jpql.builder.QueryWherePredicateStringBuilderGroupJpql;

public class JpqlQualifier extends AnnotationLiteral<Jpql> implements Jpql {
	private static final long serialVersionUID = 1L;
	
	public static <T> T inject(Class<T> aClass){
		return DependencyInjection.injectByQualifiersClasses(aClass, JpqlQualifier.class);
	}

	/**/
	
	public static <OBJECT> OBJECT map(Class<OBJECT> aClass, AnnotationLiteral<?>... annotationLiterals) {
		/* clause */
		if(QueryClauseStringBuilderSelect.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryClauseStringBuilderSelectJpql.class);
		if(QueryClauseStringBuilderWhere.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryClauseStringBuilderWhereJpql.class);
		if(QueryClauseStringBuilderFrom.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryClauseStringBuilderFromJpql.class);
		
		if(QueryAttributeNameBuilder.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryAttributeNameBuilderJpql.class);
		
		if(QueryParameterNameBuilder.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryParameterNameBuilderJpql.class);
		
		if(QueryOperandStringBuilder.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryOperandStringBuilderJpql.class);
		
		if(QueryWherePredicateStringBuilderEqual.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryWherePredicateStringBuilderEqualJpql.class);
		
		if(QueryStringBuilderSelect.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryStringBuilderSelectJpql.class);
		
		if(QueryWherePredicateStringBuilderGroup.class.equals(aClass))
			return (OBJECT) JpqlQualifier.inject(QueryWherePredicateStringBuilderGroupJpql.class);
		
		return DependencyInjection.inject(aClass, annotationLiterals);
	}
}