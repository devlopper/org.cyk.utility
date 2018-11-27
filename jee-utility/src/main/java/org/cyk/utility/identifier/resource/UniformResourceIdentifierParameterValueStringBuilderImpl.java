package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.system.action.SystemActionUpdate;

public class UniformResourceIdentifierParameterValueStringBuilderImpl extends AbstractStringFunctionImpl implements UniformResourceIdentifierParameterValueStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Object value;
	
	@Override
	protected String __execute__() throws Exception {
		String _value = null;
		Object value = getValue();
		if(value!=null) {
			if(value instanceof Class) {
				Class<?> clazz = (Class<?>)value;
				if(clazz.equals(SystemActionCreate.class))
					_value = "create";
				else if(clazz.equals(SystemActionRead.class))
					_value = "read";
				else if(clazz.equals(SystemActionUpdate.class))
					_value = "update";
				else if(clazz.equals(SystemActionDelete.class))
					_value = "delete";
				else if(clazz.equals(SystemActionList.class))
					_value = "list";
				else if(clazz.equals(SystemActionSelect.class))
					_value = "select";
				else if(clazz.equals(SystemActionProcess.class))
					_value = "process";
				else
					_value = clazz.getSimpleName().toLowerCase();
			}else if(value instanceof Objectable) {
				_value = ((Objectable)value).getIdentifier() == null ? null : ((Objectable)value).getIdentifier().toString().toLowerCase();
			}
			
			if(__injectStringHelper__().isBlank(_value))
				__injectThrowableHelper__().throwRuntimeException("Parameter value cannot be found for <<"+value+">>");
		}
		return _value;
	}
	
	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public UniformResourceIdentifierParameterValueStringBuilder setValue(Object value) {
		this.value = value;
		return this;
	}

}
