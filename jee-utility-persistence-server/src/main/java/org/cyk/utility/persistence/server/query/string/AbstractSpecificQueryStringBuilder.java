package org.cyk.utility.persistence.server.query.string;

import static org.cyk.utility.persistence.query.Language.parenthesis;
import static org.cyk.utility.persistence.query.Language.Where.or;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.SpecificPersistence;
import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringImpl;
import org.cyk.utility.persistence.entity.AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.persistence.entity.IdentifiableSystemScalarStringIdentifiableBusinessString;
import org.cyk.utility.persistence.entity.IdentifiableSystemScalarStringIdentifiableBusinessStringNamable;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;

public abstract class AbstractSpecificQueryStringBuilder<T> implements Serializable {
	
	protected String searchPredicate;
	
	@PostConstruct
	public void postConstruct() {
		searchPredicate = buildSearchPredicate();
	}
	
	protected Class<T> getPeristenceClass() {
		return (Class<T>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	String buildSearchPredicate() {
		if(CollectionHelper.isEmpty(getSearchPredicateFieldsNames()) || StringHelper.isBlank(getPersistence().getParameterNameSearch()))
			return null;
		return parenthesis(or(
				getSearchPredicateFieldsNames().stream().map(fieldName -> LikeStringBuilder.getInstance().build("t",fieldName, getPersistence().getParameterNameSearch())).collect(Collectors.toList())
		));
	}
	
	protected Collection<String> getSearchPredicateFieldsNames() {
		if(ClassHelper.isInstanceOf(getPeristenceClass(), IdentifiableSystemScalarStringIdentifiableBusinessStringNamable.class))
			return CollectionHelper.listOf(AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringNamableImpl.FIELD_CODE,AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringNamableImpl.FIELD_NAME);
		if(ClassHelper.isInstanceOf(getPeristenceClass(), IdentifiableSystemScalarStringIdentifiableBusinessString.class))
			return CollectionHelper.listOf(AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringImpl.FIELD_CODE);
		return null;
	}
	
	public abstract SpecificPersistence<T> getPersistence();
	
	public Boolean isProcessable(QueryExecutorArguments arguments) {
		return getPersistence().isProcessable(arguments);
	}
	
	public void populatePredicates(QueryExecutorArguments queryExecutorArguments, Arguments arguments, WhereStringBuilder.Predicate predicate,Filter filter) {
		populatePredicatesSearch(queryExecutorArguments, arguments, predicate, filter);
	}
	
	public void populatePredicatesSearch(QueryExecutorArguments queryExecutorArguments, Arguments arguments, WhereStringBuilder.Predicate predicate,Filter filter) {
		if(queryExecutorArguments.getFilterField(getPersistence().getParameterNameSearch()) != null) {
			predicate.add(searchPredicate);
			String search = ValueHelper.defaultToIfBlank((String) queryExecutorArguments.getFilterFieldValue(getPersistence().getParameterNameSearch()),"");
			filter.addField(getPersistence().getParameterNameSearch(), LikeStringValueBuilder.getInstance().build(search, null, null));
		}
	}
	
	protected static void populatePredicatesExists(QueryExecutorArguments queryExecutorArguments, Arguments arguments, WhereStringBuilder.Predicate predicate,Filter filter,String parameterName,String query) {
		if(queryExecutorArguments.getFilterFieldValue(parameterName) == null)
			return;
		predicate.add(String.format("EXISTS(%s)",query));
		filter.addField(parameterName, queryExecutorArguments.getFilterFieldValue(parameterName));
	}
	
	protected static void populatePredicatesIsNull(QueryExecutorArguments queryExecutorArguments, Arguments arguments, WhereStringBuilder.Predicate predicate,Filter filter,String fieldName,String parameterName,Boolean negate) {
		Boolean value = queryExecutorArguments.getFilterFieldValueAsBoolean(null,parameterName);
		if(value == null)
			return;
		if(Boolean.TRUE.equals(negate))
			value = !value;
		predicate.add(String.format("t.%s IS%s NULL",fieldName,value ? "" : " NOT"));
	}
	
	protected static void populatePredicatesIsNull(QueryExecutorArguments queryExecutorArguments, Arguments arguments, WhereStringBuilder.Predicate predicate,Filter filter,String fieldName,String parameterName) {
		populatePredicatesIsNull(queryExecutorArguments, arguments, predicate, filter, fieldName, parameterName, null);
	}
	
	/**/
	
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		
	}
}