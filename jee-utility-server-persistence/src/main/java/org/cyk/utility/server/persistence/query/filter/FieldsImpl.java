package org.cyk.utility.server.persistence.query.filter;

import java.io.Serializable;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.string.StringHelper;

public class FieldsImpl extends AbstractCollectionInstanceImpl<Field> implements Fields,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Field getByPath(String...paths) {
		if(__inject__(ArrayHelper.class).isNotEmpty(paths)  && __inject__(CollectionHelper.class).isNotEmpty(collection)) {
			String path = __inject__(FieldHelper.class).join(paths);
			if(__inject__(StringHelper.class).isNotBlank(path))
				for(Field index : collection) {
					FieldInstance fieldInstance = index.getInstance();
					if(fieldInstance != null && path.equals(fieldInstance.getPath()))
						return index;
				}
		}
		return null;
	}
	
	@Override
	public Boolean hasPath(String...paths) {
		return getByPath(paths) != null;
	}

}
