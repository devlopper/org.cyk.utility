package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Language {
	
	static String of(Collection<String> clauses) {
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("clauses", clauses);
		return StringHelper.concatenate(clauses, " ");
	}
	
	static String of(String...clauses) {
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("clauses", clauses);
		return of(CollectionHelper.listOf(clauses));
	}
	
	static String jpql(Collection<String> clauses) {
		return of(clauses);
	}
	
	static String jpql(String...clauses) {
		return of(clauses);
	}
	
	static String parenthesis(String string) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("string", string);
		return String.format(PARENTHESIS, string);
	}
	
	String PARENTHESIS = "(%s)";

	/**/
	
	public static interface Select {
		static String of(String select) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("select", select);
			return String.format(SELECT, select);
		}
		
		static String of(Collection<String> strings) {
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("strings", strings);
			return of(StringHelper.concatenate(strings, ","));
		}
		
		static String of(String...strings) {
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("strings", strings);
			return of(ArrayHelper.isEmpty(strings) ? null : CollectionHelper.listOf(strings));
		}
		
		static String select(Collection<String> strings) {
			return of(strings);
		}
		
		static String select(String...strings) {
			return of(strings);
		}
		
		static String concat(String tupleName,Collection<String> attributesNames) {
			if(StringHelper.isBlank(tupleName) || CollectionHelper.isEmpty(attributesNames))
				return null;
			if(CollectionHelper.getSize(attributesNames) < 2)
				throw new IllegalArgumentException("At least two(2) attributes names are required");
			Collection<String> fields = attributesNames.stream().map(attributeName -> FieldHelper.join(tupleName,attributeName)).collect(Collectors.toList());
			return String.format(CONCAT, StringHelper.concatenate(fields,",' ',"));
		}
		
		static String concat(String tupleName,String...attributesNames) {
			if(StringHelper.isBlank(tupleName) || ArrayHelper.isEmpty(attributesNames))
				return null;
			return concat(tupleName, CollectionHelper.listOf(attributesNames));
		}
		
		static String concatCodeName(Collection<String> tuplesNames) {
			if(CollectionHelper.isEmpty(tuplesNames))
				return null;
			return StringHelper.concatenate(tuplesNames.stream().map(tupleName ->  String.format(CONCATENATE_CODE_NAME, tupleName)).collect(Collectors.toList()),",");
		}
		
		static String concatCodeName(String...tuplesNames) {
			if(ArrayHelper.isEmpty(tuplesNames))
				return null;
			return concatCodeName(CollectionHelper.listOf(tuplesNames));
		}
		
		static String fields(String tuple,Collection<String> names) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("tuple name", tuple);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("fields names", names);
			return names.stream().map(name -> tuple+"."+name).collect(Collectors.joining(","));
		}
		
		static String fields(String tuple,String...names) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("tuple name", tuple);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("fields names", names);
			return fields(tuple, CollectionHelper.listOf(names));
		}
		
		String SELECT = "SELECT %s";
		String CONCAT = "CONCAT (%s)";
		String CONCATENATE_CODE_NAME = "CONCAT(%1$s.code,' ',%1$s.name)";
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class Tuple extends AbstractObject implements Serializable {
			String name;
			
			public static Tuple instantiate(String name) {
				return new Tuple().setName(name);
			}
		}
	}
	
	public static interface From {
		static String of(Collection<String> strings) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("from", strings);
			String from = StringHelper.concatenate(strings, " ");
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("from", from);
			return String.format(FROM, from);
		}
		
		static String of(String...strings) {
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("from", strings);			
			return of(CollectionHelper.listOf(strings));
		}
		
		static String from(Collection<String> strings) {
			return of(strings);
		}
		
		static String from(String...strings) {
			return of(strings);
		}
		
		static String ofTuple(Class<?> tupleClass) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("Tuple class", tupleClass);
			return of(tuple(tupleClass));
		}
		
		static String tuple(String tuple,String name) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("Tuple", tuple);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("Tuple name", name);
			return tuple+" "+name;
		}
		
		static String tuple(String tuple) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("Tuple", tuple);
			return tuple(tuple, "t");
		}
		
		static String tuple(Class<?> tupleClass) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("Tuple class", tupleClass);
			return tuple(tupleClass.getSimpleName());
		}
		
		static String innerJoin(String tupleName,String variableName,String fieldName,String joinedVariableName,String joinedFieldName) {
			return String.format(INNER_JOIN, tupleName,variableName,fieldName,joinedVariableName,joinedFieldName);
		}
		
		static String join(String tupleName,String variableName,String fieldName,String joinedVariableName,String joinedFieldName) {
			return innerJoin(tupleName, variableName, fieldName, joinedVariableName, joinedFieldName);
		}
		
		static String leftJoin(String tupleName,String variableName,String fieldName,String joinedVariableName,String joinedFieldName) {
			return String.format(LEFT_JOIN, tupleName,variableName,fieldName,joinedVariableName,joinedFieldName);
		}
		
		String FROM = "FROM %s";
		String JOIN = "JOIN %1$s %2$s ON %2$s.%3$s = %4$s.%5$s";
		String INNER_JOIN = "INNER "+JOIN;
		String LEFT_JOIN = "LEFT "+JOIN;
	}
	
	public static interface Where {
		static String of(String where) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("where", where);
			return String.format(WHERE, where);
		}
		
		static String of(Collection<String> strings) {
			if(CollectionHelper.isEmpty(strings))
				return ConstantEmpty.STRING;
			return of(StringHelper.concatenate(strings, " "));
		}
		
		static String of(String...strings) {
			if(ArrayHelper.isEmpty(strings))
				return ConstantEmpty.STRING;
			return of(CollectionHelper.listOf(strings));
		}
		
		static String where(Collection<String> strings) {
			return of(strings);
		}
		
		static String where(String...strings) {
			return of(strings);
		}
		
		static String not(String predicate) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("predicate", predicate);
			return String.format(NOT, predicate);
		}
		
		static String join(Collection<String> predicates,LogicalOperator operator) {
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("predicates", predicates);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("operator", operator);
			return predicates.stream().filter(predicate -> StringHelper.isNotBlank(predicate)).collect(Collectors.joining(" "+operator.name()+" "));
		}
		
		static String join(LogicalOperator operator,String...predicates) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("operator", operator);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("predicates", predicates);			
			return join(CollectionHelper.listOf(predicates),operator);
		}
		
		static String and(Collection<String> predicates) {
			return join(predicates,LogicalOperator.AND);
		}
		
		static String and(String...predicates) {
			return join(LogicalOperator.AND, predicates);
		}
		
		static String or(String...predicates) {
			return join(LogicalOperator.OR, predicates);
		}
		
		static String in(String tupleName,String fieldName,String parameterName) {
			return operate(tupleName, fieldName, parameterName, ArithmeticOperator.IN.getSymbol());
		}
		
		static String inJoinFieldsNames(String tupleName,String parameterName,String...fieldsNames) {
			return in(tupleName, FieldHelper.join(fieldsNames), parameterName);
		}
		
		static String equals(String tupleName,String fieldName,String parameterName) {
			return operate(tupleName, fieldName, parameterName, ArithmeticOperator.EQ.getSymbol());
		}
		
		static String equalsJoinFieldsNames(String tupleName,String parameterName,String...fieldsNames) {
			return equals(tupleName, FieldHelper.join(fieldsNames), parameterName);
		}
		
		static String operate(String tupleName,String fieldName,String parameterName,String operator) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("tuple name", tupleName);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("field name", fieldName);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("parameter name", parameterName);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("operator", operator);
			return String.format(OPERATE, tupleName,fieldName,operator,parameterName);
		}
		
		static String operateJoinFieldsNames(String tupleName,String parameterName,String operator,String...fieldsNames) {
			return operate(tupleName, FieldHelper.join(fieldsNames), parameterName, operator);
		}
		
		static String like(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters,LogicalOperator additionalParametersOperator,Boolean isCaseSensitive) {
			return new Like().setTupleName(tupleName).setFieldName(fieldName).setParameterName(parameterName).setNumberOfAdditionalParameters(numberOfAdditionalParameters)
					.setOperator(operator).setIsCaseSensitive(isCaseSensitive).setAdditionalParametersOperator(additionalParametersOperator).generate();
		}
		
		static String like(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters,LogicalOperator additionalParametersOperator) {
			return like(tupleName, fieldName, parameterName, operator, numberOfAdditionalParameters, additionalParametersOperator, null);
		}
		
		static String like(String tupleName,String fieldName,String parameterName,LogicalOperator operator,Integer numberOfAdditionalParameters) {
			return like(tupleName, fieldName, parameterName, operator, numberOfAdditionalParameters, null);
		}
		
		static String like(String tupleName,String fieldName,String parameterName,Integer numberOfAdditionalParameters) {
			return like(tupleName, fieldName, parameterName, LogicalOperator.OR, numberOfAdditionalParameters, LogicalOperator.AND, null);
		}
		
		static String like(String tupleName,String fieldName,String parameterName) {
			return like(tupleName, fieldName, parameterName, null, null, null);
		}
		
		static String exists(String exists) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("exists", exists);
			return String.format(EXISTS, exists);
		}
		
		static String exists(Collection<String> strings) {
			if(CollectionHelper.isEmpty(strings))
				return ConstantEmpty.STRING;
			return exists(StringHelper.concatenate(strings, " "));
		}
		
		static String exists(String...strings) {
			if(ArrayHelper.isEmpty(strings))
				return ConstantEmpty.STRING;
			return exists(CollectionHelper.listOf(strings));
		}
		
		/*
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
		*/
		String WHERE = "WHERE %s";
		String OPERATE = "%s.%s %s :%s";
		String EXISTS = "EXISTS(%s)";
		String NOT = "NOT (%s)";
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class Like extends AbstractObject implements Serializable {
			private String tupleName;
			private String fieldName;
			private String parameterName;
			private LogicalOperator operator;
			private Boolean isCaseSensitive;
			private Integer numberOfAdditionalParameters;
			private LogicalOperator additionalParametersOperator;
			
			public String generate() {
				if(StringHelper.isBlank(tupleName) || StringHelper.isBlank(fieldName) || StringHelper.isBlank(parameterName))
					throw new RuntimeException(String.format("Illegal parameters. tuple name %s , field name : %s , parameter name : %s."
							,tupleName,fieldName,parameterName));
				String like = format(tupleName, fieldName, parameterName, isCaseSensitive);
				if(numberOfAdditionalParameters == null || numberOfAdditionalParameters <= 0)
					return like;
				if(operator == null)
					throw new RuntimeException("Operator is required.");
				if(numberOfAdditionalParameters > 1 && additionalParametersOperator == null)
					throw new RuntimeException("Operator of additional parameters is required.");	
				Collection<String> additionalParametersNames = new ArrayList<String>();			
				for(Integer index = 0; index < numberOfAdditionalParameters; index++)
					additionalParametersNames.add(parameterName+index);
				Collection<String> likes = new ArrayList<String>();
				additionalParametersNames.forEach(parameterName -> {
					likes.add(format(tupleName, fieldName, parameterName, isCaseSensitive));
				});
				String additionalParametersLike;
				if(likes.size() == 1)
					additionalParametersLike = likes.iterator().next();
				else
					additionalParametersLike = "("+StringHelper.concatenate(likes, " "+additionalParametersOperator.name()+" ")+")";				
				return "("+like+" "+operator.name()+" "+additionalParametersLike+")";
			}
			
			private static String format(String tupleName,String fieldName,String parameterName,Boolean isCaseSensitive) {
				return String.format(Boolean.TRUE.equals(isCaseSensitive) ? FORMAT_CASE_SENSITIVE : FORMAT,tupleName, fieldName, parameterName);
			}
			
			private static final String FORMAT = "LOWER(%s.%s) LIKE LOWER(:%s)";
			private static final String FORMAT_CASE_SENSITIVE = "%s.%s LIKE :%s";
		}
	}
	
	public static interface Group {
		static String of(String group) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("group", group);
			return String.format(GROUP, group);
		}
		String GROUP = "GROUP BY %s";
	}
	
	public static interface Having {
		
	}
	
	public static interface Order {
		static String of(String order) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("order", order);
			return String.format(ORDER, order);
		}
		
		static String ascending(String tupleName,String fieldName) {
			return operate(tupleName, fieldName, "ASC");
		}
		
		static String asc(String tupleName,String fieldName) {
			return ascending(tupleName, fieldName);
		}
		
		static String descending(String tupleName,String fieldName) {
			return operate(tupleName, fieldName, "DESC");
		}
		
		static String desc(String tupleName,String fieldName) {
			return descending(tupleName, fieldName);
		}
		
		static String operate(String tupleName,String fieldName,String operator) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("tuple name", tupleName);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("field name", fieldName);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("operator", operator);
			return String.format(OPERATE, tupleName,fieldName,operator);
		}
		
		static String join(Collection<String> orders) {
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("orders", orders);
			return StringHelper.concatenate(orders, ",");
		}
		
		static String join(String...orders) {
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("orders", orders);			
			return join(CollectionHelper.listOf(orders));
		}
		
		String ORDER = "ORDER BY %s";
		String OPERATE = "%s.%s %s";
	}

	/* Argument Generator*/
	
	public static interface Argument {
		
		public static interface Like {
			
			static String startsWith(Object value) {
				String string = value == null ? ConstantEmpty.STRING : value.toString();
				return string+"%";
			}
			
			static String endsWith(Object value) {
				String string = value == null ? ConstantEmpty.STRING : value.toString();
				return "%"+string;
			}
			
			static String contains(Object value) {
				String string = value == null ? ConstantEmpty.STRING : value.toString();
				return "%"+string+"%";
			}
			
			static List<String> containsWords(Object value,Integer count,String defaultWord) {
				if(count == null || count < 0)
					return null;
				String string = value == null ? ConstantEmpty.STRING : value.toString();
				Collection<String> words = StringHelper.getWords(string, count, defaultWord);
				if(CollectionHelper.isEmpty(words))
					return null;
				return words.stream().map(word -> contains(word)).collect(Collectors.toList());
			}
			
			static List<String> containsWords(Object value,Integer count) {
				return containsWords(value, count, ConstantEmpty.STRING);
			}
			
			static List<String> containsStringOrWords(Object value,Integer count,String defaultWord) {
				if(count == null || count < 0)
					return null;
				String string = value == null ? ConstantEmpty.STRING : value.toString();
				List<String> strings = new ArrayList<>();
				strings.add(contains(string));
				Collection<String> words = containsWords(value, count, defaultWord);
				if(CollectionHelper.isNotEmpty(words))
					strings.addAll(words);
				return strings;
			}
			
			static List<String> containsStringOrWords(Object value,Integer count) {
				return containsStringOrWords(value, count, ConstantEmpty.STRING);
			}
		}
	}
}