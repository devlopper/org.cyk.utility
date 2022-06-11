package org.cyk.utility.persistence.server.query.string;

import static org.cyk.utility.persistence.query.Language.parenthesis;
import static org.cyk.utility.persistence.query.Language.Where.or;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.SpecificPersistence;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;

public abstract class AbstractSpecificQueryStringBuilder<T> implements Serializable {
	
	protected String searchPredicate;
	
	@PostConstruct
	public void postConstruct() {
		searchPredicate = buildSearchPredicate();
	}
	
	String buildSearchPredicate() {
		if(CollectionHelper.isEmpty(getSearchPredicateFieldsNames()) || StringHelper.isBlank(getPersistence().getParameterNameSearch()))
			return null;
		return parenthesis(or(
				getSearchPredicateFieldsNames().stream().map(fieldName -> LikeStringBuilder.getInstance().build("t",fieldName, getPersistence().getParameterNameSearch())).collect(Collectors.toList())
		));
	}
	
	protected Collection<String> getSearchPredicateFieldsNames() {
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
	
	public void setOrder(QueryExecutorArguments queryExecutorArguments, Arguments arguments) {
		
	}
}