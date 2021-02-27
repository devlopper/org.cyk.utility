package org.cyk.utility.persistence.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface QueryStringHelper {
	
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
