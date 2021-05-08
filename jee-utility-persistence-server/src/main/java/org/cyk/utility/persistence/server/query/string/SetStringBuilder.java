package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.PersistenceHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface SetStringBuilder {

	String build(Arguments arguments);
	String build(String fieldName,String value,String variableName);
	String build(String fieldName,String value);
	
	public static abstract class AbstractImpl extends AbstractObject implements SetStringBuilder,Serializable {
	
		@Override
		public String build(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("set string builder arguments", arguments);
			String fieldName = arguments.fieldName;
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("set string builder field name", fieldName);
			String variableName = arguments.variableName;
			//ThrowableHelper.throwIllegalArgumentExceptionIfBlank("set string builder variable name", variableName);
			String valueAsString = null;
			if(arguments.kase == null)
				valueAsString = arguments.valueAsString;
			else
				valueAsString = CaseStringBuilder.getInstance().build(arguments.kase);
			
			if(StringHelper.isBlank(variableName))
				variableName = "t";
			return String.format(FORMAT, variableName,fieldName,valueAsString);
		}
		
		@Override
		public String build(String fieldName, String value, String variableName) {
			return build(new Arguments().setVariableName(variableName).setFieldName(fieldName).setValueAsString(value));
		}
		
		@Override
		public String build(String fieldName, String value) {
			return build(new Arguments().setFieldName(fieldName).setValueAsString(value));
		}
		
		private static final String FORMAT = "%s.%s = %s";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {		
		private String variableName,fieldName,valueAsString;
		private CaseStringBuilder.Case kase;
		
		public Arguments setValue(Object object) {
			valueAsString = PersistenceHelper.stringifyColumnValue(object);
			return this;
		}
		
		public Arguments setKaseFromIdentifiables(Collection<?> identifiables,String fieldName) {
			if(CollectionHelper.isEmpty(identifiables) || StringHelper.isBlank(fieldName))
				return this;
			this.fieldName = fieldName;
			kase = new CaseStringBuilder.Case();
			for(Object identifiable : identifiables)
				kase.when(String.format("t.%s = %s", "identifier",PersistenceHelper.stringifyColumnValue(FieldHelper.readSystemIdentifier(identifiable)))
						, StringHelper.get(FieldHelper.read(identifiable, fieldName)));
			return this;
		}
	}
	
	/**/
	
	static SetStringBuilder getInstance() {
		return Helper.getInstance(SetStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}