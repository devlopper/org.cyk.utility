package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public interface QueryParameterNameBuilder {

	String build(Arguments arguments);
	
	default String build(String fieldName,Boolean pluralable) {
		return build(new Arguments().setFieldName(fieldName).setPluralable(pluralable));
	}
	
	default String build(String fieldName) {
		return build(new Arguments().setFieldName(fieldName));
	}
	
	default String buildPlural(String fieldName) {
		return build(new Arguments().setFieldName(fieldName).setPluralable(Boolean.TRUE));
	}
	
	public static abstract class AbstractImpl extends AbstractObject implements QueryParameterNameBuilder,Serializable {
		@Override
		public String build(Arguments arguments) {
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			if(StringHelper.isBlank(arguments.fieldName))
				throw new RuntimeException("field name is required");
			String parameterName = arguments.fieldName;
			if(Boolean.TRUE.equals(arguments.pluralable))
				parameterName = parameterName + "s";
			return parameterName;
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @ToString(callSuper = false)
	public static class Arguments extends AbstractObject implements Serializable {
		private String fieldName;
		private Boolean pluralable;
	}
	
	static QueryParameterNameBuilder getInstance() {
		return Helper.getInstance(QueryParameterNameBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
