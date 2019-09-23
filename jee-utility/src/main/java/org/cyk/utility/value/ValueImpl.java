package org.cyk.utility.value;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.field.FieldInstance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Dependent
public class ValueImpl extends AbstractObject implements Value, Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true) private String name;
	@Getter @Setter @Accessors(chain=true) private Object object;
	@Getter @Setter @Accessors(chain=true) private FieldInstance fieldInstance;
	
	private Object __value__;
	private Boolean __isValueHashBeenSet__;
	
	@Override
	public Value set(Object value) {
		__isValueHashBeenSet__ = Boolean.TRUE;
		this.__value__ = value;
		return this;
	}
	
	@Override
	public Object get() {
		if(Boolean.TRUE.equals(__isValueHashBeenSet__) || fieldInstance == null)
			return __value__;
		__value__ =org.cyk.utility.__kernel__.field.FieldHelper.read(object, fieldInstance.getPath());
		return __value__;
	}
	
}
