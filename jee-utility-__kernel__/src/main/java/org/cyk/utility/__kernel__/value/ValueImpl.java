package org.cyk.utility.__kernel__.value;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Dependent
public class ValueImpl extends AbstractObject implements Value, Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true) private String name;
	@Getter @Setter @Accessors(chain=true) private Object object;
	@Getter @Setter @Accessors(chain=true) private FieldInstance fieldInstance;
	@Getter @Setter @Accessors(chain=true) private String configurationValueName;
	
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
		if(isHasBeenSet() || (fieldInstance == null && StringHelper.isBlank(configurationValueName)))
			return __value__;
		if(fieldInstance != null)
			__value__ = FieldHelper.read(object, fieldInstance.getPath());
		else if(StringHelper.isNotBlank(configurationValueName))
			__value__ = ConfigurationHelper.getValue(configurationValueName, null, null,null,null);
		return __value__;
	}
	
	@Override
	public Value initialize() {
		Object value = get();
		set(value);
		return this;
	}
	
	@Override
	public Boolean isHasBeenSet() {
		return Boolean.TRUE.equals(__isValueHashBeenSet__);
	}
}
