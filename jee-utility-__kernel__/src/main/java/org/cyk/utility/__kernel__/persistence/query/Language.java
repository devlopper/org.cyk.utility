package org.cyk.utility.__kernel__.persistence.query;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public interface Language {
	
	static String of(Collection<String> clauses) {
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("clauses", clauses);
		return StringHelper.concatenate(clauses, " ");
	}
	
	static String of(String...clauses) {
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("clauses", clauses);
		return of(CollectionHelper.listOf(clauses));
	}

	/**/
	
	public static interface Select {
		static String of(String select) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("select", select);
			return String.format(SELECT, select);
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
		
		String SELECT = "SELECT %s";
		String CONCAT = "CONCAT (%s)";
		String CONCATENATE_CODE_NAME = "CONCAT(%1$s.code,' ',%1$s.name)";
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
		
		static String join(Collection<String> predicates,LogicalOperator operator) {
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("predicates", predicates);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("operator", operator);
			return StringHelper.concatenate(predicates, " "+operator.name()+" ");
		}
		
		static String join(LogicalOperator operator,String...predicates) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("operator", operator);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("predicates", predicates);			
			return join(CollectionHelper.listOf(predicates),operator);
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
		
		String WHERE = "WHERE %s";
		String OPERATE = "%s.%s %s :%s";
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
}