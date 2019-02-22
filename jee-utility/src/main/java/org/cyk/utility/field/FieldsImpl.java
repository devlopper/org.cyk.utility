package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.Strings;

public class FieldsImpl extends AbstractCollectionInstanceImpl<Field> implements Fields,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Strings getNames() {
		Strings names = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(collection)) {
			names = __inject__(Strings.class);
			for(Field index : collection)
				names.add(index.getName());
		}
		return names;
	}

}
