package org.cyk.utility.__kernel__;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface FieldHelper {
	
	static Class<?> getFieldType(Class<?> aClass, String fieldName) {
		Class<?> clazz = null;
		if(fieldName!=null && fieldName.length()>0) {
			Field field = FieldUtils.getField(aClass, fieldName, Boolean.TRUE);
			if(field!=null)
				clazz = field.getType();
		}
		return clazz;
	}
}
