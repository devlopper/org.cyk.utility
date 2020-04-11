package org.cyk.utility.__kernel__.persistence.query.filter;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.string.StringHelper;

@Deprecated
public class Fields extends AbstractCollectionInstanceImpl<Field> implements Serializable {
	private static final long serialVersionUID = 1L;

	public Field get(Collection<String> paths) {
		if(CollectionHelper.isEmpty(collection) || CollectionHelper.isEmpty(paths))
			return null;		
		String path = FieldHelper.join(paths);
		if(StringHelper.isBlank(path))
			return null;		
		for(Field index : collection) {
			FieldInstance fieldInstance = index.getInstance();
			if(fieldInstance != null && path.equals(fieldInstance.getPath()))
				return index;
		}		
		return null;
	}
	
	public Field get(String...paths) {
		if(ArrayHelper.isEmpty(paths))
			return null;
		return get(CollectionHelper.listOf(paths));
	}
	
	@Deprecated
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
	
	public Boolean hasPath(String...paths) {
		return get(paths) != null;
	}

}
