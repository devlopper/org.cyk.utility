package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface Fields extends CollectionInstance<Field> {

	Strings getNames();
	Field getByName(String name);
	Field getByName(Class<?> klass,FieldName fieldName,ValueUsageType valueUsageType);
	
	Fields removeByNames(Collection<String> names);
	Fields removeByNames(String...names);
	Fields removeModifier(Integer modifier);
	Fields removeModifierStatic();
	Fields removeModifierFinal();
}
