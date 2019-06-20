package org.cyk.utility.field;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.StringHelper;

@Dependent
public class FieldInstancesImpl extends AbstractCollectionInstanceImpl<FieldInstance> implements FieldInstances,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FieldInstance get(Class<?> aClass, Collection<String> paths) {
		String path = __inject__(FieldHelper.class).join(paths);
		if(collection!=null && !collection.isEmpty() && aClass!=null && __inject__(StringHelper.class).isNotBlank(path))
			for(FieldInstance index : collection)
				if(aClass.equals(index.getClazz()) && path.equals(index.getPath()))
					return index;
		return null;
	}

	@Override
	public FieldInstance get(Class<?> aClass, String... paths) {
		return get(aClass, __inject__(CollectionHelper.class).instanciate(paths));
	}
	
}
