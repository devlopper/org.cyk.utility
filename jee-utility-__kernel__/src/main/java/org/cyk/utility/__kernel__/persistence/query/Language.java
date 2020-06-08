package org.cyk.utility.__kernel__.persistence.query;

import java.util.Collection;

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
		String SELECT = "SELECT %s";
	}
	
	public static interface From {
		static String of(String from) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("from", from);
			return String.format(FROM, from);
		}
		String FROM = "FROM %s";
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