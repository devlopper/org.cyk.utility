package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public interface QueryValueBuilder {

	String build(Arguments arguments);
	
	default String buildSelect(Class<?> tupleClass) {
		return build(new Arguments().setName(QueryName.READ).setTupleClass(tupleClass).setResultClass(tupleClass));
	}
	
	default String buildSelectWhereFieldIn(Class<?> tupleClass,String fieldName,String parameterName) {
		Field field = FieldHelper.getByName(tupleClass, fieldName);
		if(field == null)
			throw new RuntimeException("tuple class "+tupleClass+" does not have field named "+fieldName);
		if(StringHelper.isBlank(parameterName))
			parameterName = QueryParameterNameBuilder.getInstance().buildPlural(fieldName);
		return build(new Arguments().setTupleClass(tupleClass).setResultClass(tupleClass).setFieldInName(fieldName).setFieldInParameterName(parameterName));
	}
	
	default String buildSelectWhereFieldIn(Class<?> tupleClass,String fieldName) {
		return buildSelectWhereFieldIn(tupleClass, fieldName, null);
	}
	
	default String buildSelectBySystemIdentifiers(Class<?> tupleClass) {
		Field field = FieldHelper.getSystemIdentifier(tupleClass);
		if(field == null)
			throw new RuntimeException("tuple class "+tupleClass+" does not have system identifier field");
		return buildSelectWhereFieldIn(tupleClass, field.getName(), "identifiers");
	}
	
	default String buildSelectByBusinessIdentifiers(Class<?> tupleClass) {
		Field field = FieldHelper.getBusinessIdentifier(tupleClass);
		if(field == null)
			throw new RuntimeException("tuple class "+tupleClass+" does not have business identifier field");
		return buildSelectWhereFieldIn(tupleClass, field.getName(), "identifiers");
	}
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements QueryValueBuilder,Serializable {
		@Override
		public String build(Arguments arguments) {
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			//if(arguments.name == null)
			//	throw new RuntimeException("name is required");
			String value = null;
			String tupleName = arguments.tupleClass.getSimpleName();
			String variableName = StringHelper.getVariableNameFrom(tupleName);
			if(StringHelper.isBlank(value) && StringHelper.isNotBlank(arguments.fieldInName)) {
				value = formatSelectWhereFieldIn(tupleName, variableName, arguments.fieldInName, arguments.fieldInParameterName);
			}
			if(StringHelper.isBlank(value) && (QueryName.READ.equals(arguments.name) || QueryName.READ_BY_SYSTEM_IDENTIFIERS.equals(arguments.name)  || QueryName.READ_BY_SYSTEM_IDENTIFIERS.equals(arguments.name)))
				value = buildSelect(arguments);
			if(StringHelper.isBlank(value))
				new RuntimeException("we cannot build query value using arguments "+arguments);
			return value;
		}
		
		private String buildSelect(Arguments arguments) {
			if(arguments.resultClass == null)
				throw new RuntimeException("result class is required");
			if(arguments.tupleClass == null)
				throw new RuntimeException("tuple class is required");
			String tupleName = arguments.tupleClass.getSimpleName();
			String variableName = StringHelper.getVariableNameFrom(tupleName);
			if(QueryName.READ.equals(arguments.name))
				return String.format(FORMAT_SELECT,tupleName,variableName);
			if(QueryName.READ_BY_SYSTEM_IDENTIFIERS.equals(arguments.name))
				return String.format(FORMAT_SELECT_WHERE_FIELD_IN,tupleName,variableName,FieldHelper.getSystemIdentifier(arguments.tupleClass).getName(),"identifiers");
			if(QueryName.READ_BY_SYSTEM_IDENTIFIERS.equals(arguments.name))
				return String.format(FORMAT_SELECT_WHERE_FIELD_IN,tupleName,variableName,FieldHelper.getBusinessIdentifier(arguments.tupleClass).getName(),"identifiers");
			return null;
		}
		
		private String formatSelectWhereFieldIn(String tupleName,String variableName,String fieldName,String parameterName) {
			return String.format(FORMAT_SELECT_WHERE_FIELD_IN,tupleName,variableName,fieldName,parameterName);
		}
	}
	
	/**/
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @ToString(callSuper = false)
	public static class Arguments extends AbstractObject implements Serializable {
		private QueryName name;
		private Class<?> tupleClass;
		private Class<?> resultClass;
		private String fieldInName;
		private String fieldInParameterName;
	}
	
	static QueryValueBuilder getInstance() {
		return Helper.getInstance(QueryValueBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String FORMAT_SELECT = "SELECT %2$s FROM %1$s %2$s";
	String FORMAT_SELECT_WHERE_FIELD_IN = FORMAT_SELECT+" WHERE %2$s.%3$s IN :%4$s";
	
	/**/
	
	public static String buildCountFromSelect(String selectQueryValue) {
		if(StringHelper.isBlank(selectQueryValue))
			throw new IllegalArgumentException("select query value is required");
		Integer selectIndex = StringUtils.indexOfIgnoreCase(selectQueryValue, "select");
		if(selectIndex != null && selectIndex > -1) {
			Integer fromIndex = StringUtils.indexOfIgnoreCase(selectQueryValue, "from");
			if(fromIndex != null && fromIndex > -1) {
				String variable = StringUtils.trimToNull(StringUtils.substring(selectQueryValue, selectIndex+6, fromIndex));
				if(StringHelper.isNotBlank(variable)) {
					String replacement = StringUtils.split(variable,",")[0];
					String countQueryValue = StringUtils.replaceOnce(selectQueryValue, variable, String.format("COUNT(%s)", replacement));
					//we do not need order by
					Integer orderByIndex = StringUtils.lastIndexOfIgnoreCase(countQueryValue, "order by");
					if(orderByIndex != null && orderByIndex > -1)
						countQueryValue = StringUtils.substring(countQueryValue, 0, orderByIndex).strip();
					return countQueryValue;
				}					
			}
		}			
		throw new IllegalArgumentException(String.format("we cannot build count query from following select query : %s",selectQueryValue));
	}

	/* derive */
	
	static String deriveSum(String tupleName,Collection<String> fieldsNames) {
		if(StringHelper.isBlank(tupleName))
			return null;
		if(CollectionHelper.isEmpty(fieldsNames))
			return null;
		Collection<String> numbers = fieldsNames.stream().map(fieldName -> deriveCaseZeroIfNull(tupleName, fieldName)).collect(Collectors.toList());		
		return StringHelper.concatenate(numbers.stream().map(number -> String.format(FORMAT_SUM, number)).collect(Collectors.toList()),",");
	}
	
	static String deriveSum(String tupleName,String...fieldsNames) {
		if(StringHelper.isBlank(tupleName))
			return null;
		if(ArrayHelper.isEmpty(fieldsNames))
			return null;
		return deriveSum(tupleName, CollectionHelper.listOf(fieldsNames));
	}
	
	static String deriveSumAsOne(String tupleName,Collection<String> fieldsNames) {
		if(StringHelper.isBlank(tupleName))
			return null;
		if(CollectionHelper.isEmpty(fieldsNames))
			return null;
		String number = StringHelper.concatenate(fieldsNames.stream().map(fieldName -> deriveCaseZeroIfNull(tupleName, fieldName)).collect(Collectors.toList()),"+");		
		return String.format(FORMAT_SUM, number);
	}
	
	static String deriveSumAsOne(String tupleName,String...fieldsNames) {
		if(StringHelper.isBlank(tupleName))
			return null;
		if(ArrayHelper.isEmpty(fieldsNames))
			return null;
		return deriveSumAsOne(tupleName, CollectionHelper.listOf(fieldsNames));
	}
	
	static String deriveCaseZeroIfNull(String tupleName,Collection<String> fieldsNames,String separator) {
		if(StringHelper.isBlank(tupleName))
			return null;
		if(CollectionHelper.isEmpty(fieldsNames))
			return null;
		if(StringHelper.isBlank(separator))
			separator = ",";
		return StringHelper.concatenate(fieldsNames.stream().map(fieldName -> String.format(FORMAT_CASE, tupleName+"."+fieldName,"IS NULL","0l",tupleName+"."+fieldName))
				.collect(Collectors.toList()),separator);
	}
	
	static String deriveCaseZeroIfNull(String tupleName,String...fieldsNames) {
		if(StringHelper.isBlank(tupleName))
			return null;
		if(ArrayHelper.isEmpty(fieldsNames))
			return null;
		return deriveCaseZeroIfNull(tupleName, CollectionHelper.listOf(fieldsNames),null);
	}
	
	static String deriveCaseZeroIfNullWithSeparator(String tupleName,String separator,String...fieldsNames) {
		if(StringHelper.isBlank(tupleName))
			return null;
		if(ArrayHelper.isEmpty(fieldsNames))
			return null;
		return deriveCaseZeroIfNull(tupleName, CollectionHelper.listOf(fieldsNames),separator);
	}
	
	static String deriveCaseZeroIfNull(String tupleName,Collection<String> fieldsNames) {
		return deriveCaseZeroIfNull(tupleName, fieldsNames,",");
	}
	
	static String deriveLeftJoinsFromFieldsNames(String tupleName,Collection<String> fieldsNames) {
		if(CollectionHelper.isEmpty(fieldsNames))
			return null;
		return StringHelper.concatenate(fieldsNames.stream().map(fieldName ->  String.format(FORMAT_LEFT_JOIN, tupleName,fieldName
				,fieldName.contains(".") ? StringUtils.substringAfterLast(fieldName, ".") : fieldName)).collect(Collectors.toList())," ");
	}
	
	static String deriveLeftJoinsFromFieldsNames(String tupleName,String...fieldsNames) {
		if(ArrayHelper.isEmpty(fieldsNames))
			return null;
		return deriveLeftJoinsFromFieldsNames(tupleName, CollectionHelper.listOf(fieldsNames));
	}
	
	static String deriveConcatsCodeAndNameFromTuplesNames(Collection<String> tuplesNames) {
		if(CollectionHelper.isEmpty(tuplesNames))
			return null;
		return StringHelper.concatenate(tuplesNames.stream().map(tupleName ->  String.format(FORMAT_CONCAT_CODE_NAME, tupleName)).collect(Collectors.toList()),",");
	}
	
	static String deriveConcatsCodeAndNameFromTuplesNames(String...tuplesNames) {
		if(ArrayHelper.isEmpty(tuplesNames))
			return null;
		return deriveConcatsCodeAndNameFromTuplesNames(CollectionHelper.listOf(tuplesNames));
	}
	
	static String deriveConcatsIdentifierAndNameFromTuplesNames(Collection<String> tuplesNames) {
		if(CollectionHelper.isEmpty(tuplesNames))
			return null;
		return StringHelper.concatenate(tuplesNames.stream().map(tupleName ->  String.format(FORMAT_CONCAT_IDENTIFIER_NAME, tupleName)).collect(Collectors.toList()),",");
	}
	
	static String deriveConcatsIdentifierAndNameFromTuplesNames(String...tuplesNames) {
		if(ArrayHelper.isEmpty(tuplesNames))
			return null;
		return deriveConcatsIdentifierAndNameFromTuplesNames(CollectionHelper.listOf(tuplesNames));
	}
	
	static String deriveLike(String tuple,String fieldName,String parameterName,Integer numberOfTokens,LogicalOperator operator,Boolean isCaseSensitive){
		if(StringHelper.isBlank(tuple) || StringHelper.isBlank(fieldName) || StringHelper.isBlank(parameterName))
			throw new RuntimeException(String.format("Illegal parameters. tuple %s , field name : %s , parameter name : %s.",tuple,fieldName,parameterName));
		if(numberOfTokens == null || numberOfTokens <=1)
			return String.format(Boolean.TRUE.equals(isCaseSensitive) ? FORMAT_TUPLE_FIELD_LIKE_PARAMETER_CASE_SENSITIVE : FORMAT_TUPLE_FIELD_LIKE_PARAMETER, tuple,fieldName,parameterName);
		Collection<String> tokens = new ArrayList<>();
		for(Integer index = 1; index <=numberOfTokens; index = index + 1)
			tokens.add(String.format(FORMAT_TUPLE_FIELD_LIKE_PARAMETER, tuple,fieldName,parameterName+index));
		return StringHelper.concatenate(tokens, " "+operator.name()+" ");
	}
	
	static String deriveLike(String tuple,String fieldName,String parameterName,Integer numberOfTokens,LogicalOperator operator){
		return deriveLike(tuple, fieldName, parameterName, numberOfTokens, operator, Boolean.FALSE);
	}
	
	static String deriveLike(String tuple,String fieldName,Integer numberOfTokens,LogicalOperator operator){
		return deriveLike(tuple, fieldName, fieldName, numberOfTokens, operator);
	}
	
	static String deriveLike(String tuple,String fieldName,String parameterName){
		return deriveLike(tuple, fieldName, parameterName, null, null);
	}
	
	static String deriveLike(String tuple,String fieldName){
		return deriveLike(tuple, fieldName, fieldName, null, null);
	}
	
	static String deriveLikeOrTokens(String tuple,String fieldName,String parameterName,String tokens){
		return String.format(FORMAT_TUPLE_FIELD_LIKE_PARAMETER_OR_TOKENS, tuple,fieldName,parameterName,tokens);
	}
	
	static String deriveLikeOrTokens(String tuple,String fieldName,String parameterName,Integer numberOfTokens,LogicalOperator operator){
		return deriveLikeOrTokens(tuple, fieldName, parameterName, deriveLike(tuple, fieldName, parameterName, numberOfTokens, operator));
	}
	
	static String deriveLikeOrTokens(String tuple,String fieldName,Integer numberOfTokens,LogicalOperator operator){
		return deriveLikeOrTokens(tuple, fieldName, fieldName, numberOfTokens, operator);
	}
	
	String FORMAT_SUM = "SUM(%s)";
	String FORMAT_CASE = "CASE WHEN %s %s THEN %s ELSE %s END";
	
	String FORMAT_LEFT_JOIN = "LEFT JOIN %s.%s %s";
	String FORMAT_CONCAT_CODE_NAME = "CONCAT(%1$s.code,' ',%1$s.name)";
	String FORMAT_CONCAT_IDENTIFIER_NAME = "CONCAT(%1$s.identifier,' ',%1$s.name)";
	
	String FORMAT_TUPLE_FIELD_LIKE_PARAMETER = "LOWER(%s.%s) LIKE LOWER(:%s)";
	String FORMAT_TUPLE_FIELD_LIKE_PARAMETER_CASE_SENSITIVE = "%s.%s LIKE :%s";
	String FORMAT_TUPLE_FIELD_LIKE_PARAMETER_OR_TOKENS = FORMAT_TUPLE_FIELD_LIKE_PARAMETER+" OR (%s)";
	
	/**/
	
	
}