package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.field.FieldName;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.value.ValueUsageType;

public class InstanceGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Object>> implements InstanceGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public InstanceGetter setFieldName(FieldName fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FieldName getFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstanceGetter setValueUsageType(ValueUsageType valueUsageType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueUsageType getValueUsageType() {
		// TODO Auto-generated method stub
		return null;
	}

}
