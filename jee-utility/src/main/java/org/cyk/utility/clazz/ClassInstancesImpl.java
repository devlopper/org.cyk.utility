package org.cyk.utility.clazz;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

@Dependent @Deprecated
public class ClassInstancesImpl extends AbstractCollectionInstanceImpl<ClassInstance> implements ClassInstances,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ClassInstance get(Class<?> aClass) {
		if(aClass!=null && CollectionHelper.isNotEmpty(collection))
			for(ClassInstance index : collection)
				if(aClass.equals(index.getClazz()))
					return index;
		return null;
	}

}
