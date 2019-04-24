package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionRemove;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.system.action.SystemActionView;

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
				/*
				if(SystemAction.class.isAssignableFrom(clazz)) {
					if(SystemAction.class.equals(clazz))
						_value = "action";
					else
						_value = StringUtils.substringBetween(clazz.getSimpleName(), SystemAction.class.getSimpleName(),"Impl").toLowerCase();
				}else
					_value = clazz.getSimpleName().toLowerCase();
				*/
				//TODO make it more better by using reflection to get value
				if(SystemActionCreate.class.isAssignableFrom(clazz))
					_value = "create";
				else if(SystemActionRead.class.isAssignableFrom(clazz))
					_value = "read";
				else if(SystemActionUpdate.class.isAssignableFrom(clazz))
					_value = "update";
				else if(SystemActionDelete.class.isAssignableFrom(clazz))
					_value = "delete";
				else if(SystemActionList.class.isAssignableFrom(clazz))
					_value = "list";
				else if(SystemActionSelect.class.isAssignableFrom(clazz))
					_value = "select";
				else if(SystemActionProcess.class.isAssignableFrom(clazz))
					_value = "process";
				else if(SystemActionAdd.class.isAssignableFrom(clazz))
					_value = "add";
				else if(SystemActionRemove.class.isAssignableFrom(clazz))
					_value = "remove";
				else if(SystemActionView.class.isAssignableFrom(clazz))
					_value = "view";
				else
					_value = clazz.getSimpleName().toLowerCase();
				
			}else if(value instanceof Objectable) {
				_value = ((Objectable)value).getIdentifier() == null ? null : ((Objectable)value).getIdentifier().toString()
						//.toLowerCase()
						;
				if(value instanceof SystemAction)
					_value = _value.toLowerCase();
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
