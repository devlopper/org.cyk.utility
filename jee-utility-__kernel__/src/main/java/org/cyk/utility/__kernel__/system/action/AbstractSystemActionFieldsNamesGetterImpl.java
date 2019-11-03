package org.cyk.utility.__kernel__.system.action;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

public abstract class AbstractSystemActionFieldsNamesGetterImpl extends AbstractSystemActionXXXGetterImpl<String> implements SystemActionFieldsNamesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	protected Collection<String> __get__(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<?> entityClass) {
		Collection<Field> fields = FieldHelper.get(entityClass);
		if(CollectionHelper.isEmpty(fields))
			return null;
		return FieldHelper.getNames(fields);
	}
	
	@Override
	protected Map<String, Collection<String>> __getCache__() {
		return FIELDS_NAMES;
	}
	
	/**/
	
	private static final Map<String,Collection<String>> FIELDS_NAMES = new HashMap<>();
}
