package org.cyk.utility.__kernel__.persistence.query.filter;

import java.io.Serializable;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.string.StringHelper;

public class FieldsImpl extends AbstractCollectionInstanceImpl<Field> implements Fields,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Field getByPath(String...paths) {
		if(ArrayHelper.isNotEmpty(paths)  && CollectionHelper.isNotEmpty(collection)) {
			String path = org.cyk.utility.__kernel__.field.FieldHelper.join(paths);
			if(StringHelper.isNotBlank(path))
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
