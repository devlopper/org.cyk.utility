package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.jboss.weld.exceptions.IllegalArgumentException;

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
					String countQueryValue = StringUtils.replaceOnce(selectQueryValue, variable, String.format("COUNT(%s)", variable));
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
}