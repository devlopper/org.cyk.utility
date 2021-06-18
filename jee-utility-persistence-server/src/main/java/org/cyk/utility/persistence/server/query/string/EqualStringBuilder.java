package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface EqualStringBuilder {

	String build(Arguments arguments);	
	//String build(String tupleName,String fieldName,String parameterName);
	
	public static abstract class AbstractImpl extends AbstractObject implements EqualStringBuilder,Serializable {
	
		@Override
		public String build(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("equal string builder arguments", arguments);
			if(StringHelper.isBlank(arguments.tupleName) || StringHelper.isBlank(arguments.fieldName) || StringHelper.isBlank(arguments.parameterName))
				throw new RuntimeException(String.format("Illegal parameters. tuple name %s , field name : %s , parameter name : %s."
						,arguments.tupleName,arguments.fieldName,arguments.parameterName));
			return format(arguments.tupleName, arguments.fieldName, arguments.parameterName,arguments.negate);		
		}
		
		private static String format(String tupleName,String fieldName,String parameterName,Boolean negate) {
			return String.format(FORMAT,tupleName, fieldName,Boolean.TRUE.equals(negate) ? "<>" : "=", parameterName);
		}
		
		private static final String FORMAT = "%s.%s %s :%s";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {		
		private String tupleName;
		private String fieldName;
		private String parameterName;
		private Boolean negate;
	}
	
	/**/
	
	static EqualStringBuilder getInstance() {
		return Helper.getInstance(EqualStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}