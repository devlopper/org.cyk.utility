package org.cyk.utility.identifier.resource;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.system.action.SystemAction;

@Dependent @Deprecated
public class UniformResourceIdentifierParameterValueStringBuilderImpl extends AbstractStringFunctionImpl implements UniformResourceIdentifierParameterValueStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<String,String> VALUES_MAP = new HashMap<>();
	
	private Object value;
	
	@Override
	public Function<Properties, String> execute() {
		String _value = null;
		Object value = getValue();
		if(value!=null) {
			if(value instanceof Class) {
				Class<?> klass = (Class<?>)value;
				if(VALUES_MAP.containsKey(klass.getName())) {
					setProperty(Properties.OUTPUT, VALUES_MAP.get(klass.getName()));
					return this;
				}
				_value = StringUtils.substringBetween(klass.getSimpleName(), SystemAction.class.getSimpleName(), "Impl");
				if(_value == null)
					_value = StringUtils.substringAfter(klass.getSimpleName(), SystemAction.class.getSimpleName());
				if(_value != null)
					_value = _value.toLowerCase();
				if(StringUtils.isBlank(_value))
					_value = klass.getSimpleName().toLowerCase();				
				VALUES_MAP.put(klass.getName(), _value);
			}else if(value instanceof Objectable) {
				_value = ((Objectable)value).getIdentifier() == null ? null : ((Objectable)value).getIdentifier().toString();
				String key = _value;
				if(VALUES_MAP.containsKey(key)) {
					setProperty(Properties.OUTPUT, VALUES_MAP.get(_value));
					return this;
				}
				if(value instanceof SystemAction)
					_value = _value.toLowerCase();
				
				if(StringUtils.isNotBlank(key))
					VALUES_MAP.put(key, _value);
			}
			if(StringHelper.isBlank(_value))
				__injectThrowableHelper__().throwRuntimeException("Parameter value cannot be found for <<"+value+">>");
		}
		setProperty(Properties.OUTPUT, _value);
		return this;
	}
	
	@Override
	protected String __execute__() throws Exception {
		String _value = null;
		Object value = getValue();
		if(value!=null) {
			if(value instanceof Class) {
				Class<?> klass = (Class<?>)value;
				if(VALUES_MAP.containsKey(klass.getName()))
					return VALUES_MAP.get(klass.getName());
				_value = StringUtils.substringBetween(klass.getSimpleName(), SystemAction.class.getSimpleName(), "Impl");
				if(_value == null)
					_value = StringUtils.substringAfter(klass.getSimpleName(), SystemAction.class.getSimpleName());
				if(_value != null)
					_value = _value.toLowerCase();
				if(StringUtils.isBlank(_value))
					_value = klass.getSimpleName().toLowerCase();				
				VALUES_MAP.put(klass.getName(), _value);
			}else if(value instanceof Objectable) {
				_value = ((Objectable)value).getIdentifier() == null ? null : ((Objectable)value).getIdentifier().toString();
				String key = _value;
				if(VALUES_MAP.containsKey(key))
					return VALUES_MAP.get(_value);
				if(value instanceof SystemAction)
					_value = _value.toLowerCase();
				
				if(StringUtils.isNotBlank(key))
					VALUES_MAP.put(key, _value);
			}
			if(StringHelper.isBlank(_value))
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
