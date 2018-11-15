package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import org.cyk.utility.field.FieldName;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.system.action.SystemAction;

public class UniformResourceIdentifierParameterNameStringBuilderImpl extends AbstractStringFunctionImpl implements UniformResourceIdentifierParameterNameStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Object name;
	
	@Override
	protected String __execute__() throws Exception {
		String value = null;
		Object name = getName();
		if(name!=null) {
			if(SystemAction.class.equals(name)) {
				value = "action";
			}else if(Class.class.equals(name)) {
				value = "class";
			}else if(FieldName.IDENTIFIER.equals(name)) {
				value = "identifier";
			}
			
			if(__injectStringHelper__().isBlank(value))
				__injectThrowableHelper__().throwRuntimeException("Parameter name cannot be found for <<"+name+">>");
		}
		return value;
	}
	
	@Override
	public Object getName() {
		return name;
	}

	@Override
	public UniformResourceIdentifierParameterNameStringBuilder setName(Object name) {
		this.name = name;
		return this;
	}

}
