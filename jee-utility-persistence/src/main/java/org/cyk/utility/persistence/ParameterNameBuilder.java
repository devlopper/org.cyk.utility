package org.cyk.utility.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ParameterNameBuilder {

	String build(Arguments arguments);
	String build(String prefix,Boolean isNull,Boolean nullable);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ParameterNameBuilder,Serializable {
		@Override
		public String build(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("prefix", arguments.prefix);
			StringBuilder name = new StringBuilder(arguments.prefix);
			if(arguments.isNull != null) {
				if(arguments.isNull)
					name.append("IsNull");
				else
					name.append("IsNotNull");
			}
			
			if(Boolean.TRUE.equals(arguments.nullable))
				name.append("Nullable");
			return name.toString();
		}
		
		@Override
		public String build(String prefix, Boolean isNull, Boolean nullable) {
			return build(new Arguments().setPrefix(prefix).setIsNull(isNull).setNullable(nullable));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private String prefix;
		private Boolean isNull;
		private Boolean nullable;
	}
	
	/**/
	
	static ParameterNameBuilder getInstance() {
		return Helper.getInstance(ParameterNameBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}