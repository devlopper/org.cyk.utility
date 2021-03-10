package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface JoinStringBuilder {

	String build(Arguments arguments);	
	
	public static abstract class AbstractImpl extends AbstractObject implements JoinStringBuilder,Serializable {
	
		@Override
		public String build(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("join string builder arguments", arguments);
			JoinType type = ValueHelper.defaultToIfNull(arguments.type, JoinType.LEFT);			
			String masterVariableName = arguments.masterVariableName;
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("join string builder master variable name", masterVariableName);
			String masterFieldName = arguments.masterFieldName;
			//ThrowableHelper.throwIllegalArgumentExceptionIfBlank("join string builder master field name", masterFieldName);
			
			String tupleName = arguments.tupleName;
			if(StringHelper.isBlank(masterFieldName))
				masterFieldName = StringHelper.getVariableNameFrom(tupleName);
			
			String variableName = arguments.variableName;
			if(StringHelper.isBlank(variableName))
				variableName = masterFieldName;//StringHelper.getVariableNameFrom(tupleName);
			
			return String.format(FORMAT, type,tupleName,variableName,variableName,masterVariableName,masterFieldName);
		}
		
		private static final String FORMAT = "%s JOIN %s %s ON %s = %s.%s";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {		
		private JoinType type;
		private String masterVariableName,masterFieldName;
		private String tupleName,variableName;				
	}
	
	/**/
	
	static JoinStringBuilder getInstance() {
		return Helper.getInstance(JoinStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}