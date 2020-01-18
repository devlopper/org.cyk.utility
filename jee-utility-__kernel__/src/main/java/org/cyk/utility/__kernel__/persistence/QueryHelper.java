package org.cyk.utility.__kernel__.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface QueryHelper {

	/* get */
	
	static String getIdentifier(Class<?> klass,String name) {
		if(klass == null || StringHelper.isBlank(name))
			return null;
		Map<String,String> map = IDENTIFIERS.get(klass);
		if(map == null)
			IDENTIFIERS.put(klass, map = new HashMap<>());
		String identifier = map.get(name);
		if(StringHelper.isBlank(identifier))
			map.put(name, identifier = QueryIdentifierBuilder.getInstance().build(klass,name));
		return identifier;
	}
	
	static String getIdentifierReadByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readByFilters");
	}
	
	static String getIdentifierCountByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countByFilters");
	}
	
	static String getIdentifierReadByFiltersLike(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readByFiltersLike");
	}
	
	static String getIdentifierCountByFiltersLike(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countByFiltersLike");
	}
	
	static String getIdentifierReadWhereCodeNotInByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readWhereCodeNotInByFilters");
	}
	
	static String getIdentifierCountWhereCodeNotInByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countWhereCodeNotInByFilters");
	}
	
	static String getIdentifierReadWhereBusinessIdentifierOrNameContains(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readWhereBusinessIdentifierOrNameContains");
	}
	
	static String getIdentifierCountWhereBusinessIdentifierOrNameContains(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countWhereBusinessIdentifierOrNameContains");
	}
	
	
	/* format */
	
	static String formatTupleFieldLike(String tuple,String fieldName,String parameterName,Integer numberOfTokens,LogicalOperator operator){
		if(StringHelper.isBlank(tuple) || StringHelper.isBlank(fieldName) || StringHelper.isBlank(parameterName))
			throw new RuntimeException(String.format("Illegal parameters. tuple %s , field name : %s , parameter name : %s.",tuple,fieldName,parameterName));
		if(numberOfTokens == null || numberOfTokens <=1)
			return String.format(FORMAT_TUPLE_FIELD_LIKE_PARAMETER, tuple,fieldName,parameterName);
		Collection<String> tokens = new ArrayList<>();
		for(Integer index = 1; index <=numberOfTokens; index = index + 1)
			tokens.add(String.format(FORMAT_TUPLE_FIELD_LIKE_PARAMETER, tuple,fieldName,parameterName+index));
		return StringHelper.concatenate(tokens, " "+operator.name()+" ");
	}
	
	static String formatTupleFieldLike(String tuple,String fieldName,Integer numberOfTokens,LogicalOperator operator){
		return formatTupleFieldLike(tuple, fieldName, fieldName, numberOfTokens, operator);
	}
	
	static String formatTupleFieldLike(String tuple,String fieldName,String parameterName){
		return formatTupleFieldLike(tuple, fieldName, parameterName, null, null);
	}
	
	static String formatTupleFieldLike(String tuple,String fieldName){
		return formatTupleFieldLike(tuple, fieldName, fieldName, null, null);
	}
	
	static String formatTupleFieldLikeOrTokens(String tuple,String fieldName,String parameterName,String tokens){
		return String.format(FORMAT_TUPLE_FIELD_LIKE_PARAMETER_OR_TOKENS, tuple,fieldName,parameterName,tokens);
	}
	
	static String formatTupleFieldLikeOrTokens(String tuple,String fieldName,String parameterName,Integer numberOfTokens,LogicalOperator operator){
		return formatTupleFieldLikeOrTokens(tuple, fieldName, parameterName, formatTupleFieldLike(tuple, fieldName, parameterName, numberOfTokens, operator));
	}
	
	static String formatTupleFieldLikeOrTokens(String tuple,String fieldName,Integer numberOfTokens,LogicalOperator operator){
		return formatTupleFieldLikeOrTokens(tuple, fieldName, fieldName, numberOfTokens, operator);
	}
	
	String FORMAT_TUPLE_FIELD_LIKE_PARAMETER = "LOWER(%s.%s) LIKE LOWER(:%s)";
	String FORMAT_TUPLE_FIELD_LIKE_PARAMETER_OR_TOKENS = "LOWER(%s.%s) LIKE LOWER(:%s) OR (%s)";
	
	Map<Class<?>,Map<String,String>> IDENTIFIERS = new HashMap<>();
	
	static void clear() {
		IDENTIFIERS.clear();
	}
}
