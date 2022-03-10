package org.cyk.utility.__kernel__.field;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.string.StringHelper;


@Dependent
public class FieldInstancesImpl extends AbstractCollectionInstanceImpl<FieldInstance> implements FieldInstances,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FieldInstance get(Class<?> aClass, Collection<String> paths) {
		String path = FieldHelper.join(paths);
		if(collection!=null && !collection.isEmpty() && aClass!=null && StringHelper.isNotBlank(path))
			for(FieldInstance index : collection)
				if(aClass.equals(index.getClazz()) && path.equals(index.getPath()))
					return index;
		return null;
	}

	@Override
	public FieldInstance get(Class<?> aClass, String... paths) {
		return get(aClass, List.of(paths));
	}
	
}
