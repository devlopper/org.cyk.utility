package org.cyk.utility.array;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class ArrayHelperImpl extends AbstractHelper implements ArrayHelper,Serializable  {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isEmpty(Object[] objects) {
		return objects == null ? Boolean.TRUE : objects.length == 0;
	}
	
	@Override
	public Boolean isNotEmpty(Object[] objects) {
		return Boolean.FALSE.equals(isEmpty(objects));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <ELEMENT> ELEMENT[] instanciate(Class<ELEMENT> aClass,Collection<ELEMENT> collection) {
		ELEMENT[] array = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(collection)){
			array = (ELEMENT[]) Array.newInstance(aClass, __inject__(CollectionHelper.class).getSize(collection));
			Integer index = 0;
			for(ELEMENT element : collection)
				array[index++] = element;
		}
		return array;
	}
	
	@Override
	public Object[] instanciate(Collection<Object> collection) {
		return instanciate(Object.class, collection);
	}
	
	@Override
	public Integer getSize(Object[] objects) {
		return objects == null ? null : objects.length;
	}
}
