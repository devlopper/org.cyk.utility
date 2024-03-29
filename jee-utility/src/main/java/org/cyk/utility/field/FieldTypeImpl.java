package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.map.MapInstanceIntegerToClass;

@Dependent @Deprecated
public class FieldTypeImpl extends AbstractObject implements FieldType,Serializable {
	private static final long serialVersionUID = 1L;

	private Field field;
	private Class<?> type;
	private MapInstanceIntegerToClass parameterizedClasses;
	
	@Override
	public Field getField() {
		return field;
	}
	@Override
	public FieldType setField(Field field) {
		this.field = field;
		return this;
	}
	@Override
	public Class<?> getType() {
		return type;
	}
	@Override
	public FieldType setType(Class<?> type) {
		this.type = type;
		return this;
	}
	@Override
	public MapInstanceIntegerToClass getParameterizedClasses() {
		return parameterizedClasses;
	}
	@Override
	public MapInstanceIntegerToClass getParameterizedClasses(Boolean injectIfNull) {
		if(parameterizedClasses == null && Boolean.TRUE.equals(injectIfNull))
			parameterizedClasses = DependencyInjection.inject(MapInstanceIntegerToClass.class);
		return parameterizedClasses;
	}
	@Override
	public FieldType setParameterizedClasses(MapInstanceIntegerToClass parameterizedClasses) {
		this.parameterizedClasses = parameterizedClasses;
		return this;
	}
	
	public static final String FIELD_PARAMETERIZED_CLASSES = "parameterizedClasses";
}
