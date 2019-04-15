package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.string.Strings;

public interface Fields extends CollectionInstance<Field> {

	Strings getNames();
	Fields removeByNames(Collection<String> names);
	Fields removeByNames(String...names);
	Fields removeModifier(Integer modifier);
	Fields removeModifierStatic();
	Fields removeModifierFinal();
}
