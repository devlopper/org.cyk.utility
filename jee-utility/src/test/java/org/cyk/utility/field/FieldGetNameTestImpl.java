package org.cyk.utility.field;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.value.ValueUsageType;

@Test
public class FieldGetNameTestImpl extends FieldNameGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__(Class<?> aClass, FieldName fieldName, ValueUsageType valueUsageType) {
		if(MyOtherEntity.class.equals(aClass))
			if(FieldName.IDENTIFIER.equals(fieldName))
				if(ValueUsageType.SYSTEM.equals(valueUsageType))
					return "sysId";
				else if(ValueUsageType.BUSINESS.equals(valueUsageType))
					return "matricule";
		return super.__execute__(aClass, fieldName, valueUsageType);
	}
	
}
