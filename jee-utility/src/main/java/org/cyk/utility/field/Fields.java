package org.cyk.utility.field;

import java.lang.reflect.Field;

import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.string.Strings;

public interface Fields extends CollectionInstance<Field> {

	Strings getNames();
	Fields removeModifier(Integer modifier);
	Fields removeModifierStatic();
	Fields removeModifierFinal();
}
