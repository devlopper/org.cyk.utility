package org.cyk.utility.__kernel__.system.action;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.field.FieldHelper;

public abstract class AbstractSystemActionFieldsGetterImpl implements SystemActionFieldsGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<Field> get(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<?> entityClass) {
		if(systemActionClass == null || entityClass == null)
			return null;
		String key = systemActionClass.getName()+"."+systemActionIdentifier+"."+entityClass.getName();
		if(FIELDS.containsKey(key))
			return FIELDS.get(key);
		Collection<Field> fields = __get__(systemActionClass,systemActionIdentifier,entityClass);
		FIELDS.put(key, fields);
		return fields;
	}
	
	protected Collection<Field> __get__(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<?> entityClass) {
		return FieldHelper.get(entityClass);
	}
	
	/**/
	
	private static final Map<String,Collection<Field>> FIELDS = new HashMap<>();
}
