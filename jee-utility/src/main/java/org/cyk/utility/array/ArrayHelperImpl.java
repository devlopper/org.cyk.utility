package org.cyk.utility.array;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class ArrayHelperImpl extends AbstractHelper implements ArrayHelper,Serializable  {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isEmpty(Object[] objects) {
		return objects == null || objects.length == 0;
	}
	
	@Override
	public Boolean isNotEmpty(Object[] objects) {
		return Boolean.FALSE.equals(isEmpty(objects));
	}
	
	private <ELEMENT> ELEMENT[] __instanciate__(Class<ELEMENT> aClass,Collection<ELEMENT> collection) {
		ELEMENT[] array = null;
		if(CollectionHelper.isNotEmpty(collection)){
			array = (ELEMENT[]) Array.newInstance(aClass, CollectionHelper.getSize(collection));
			Integer index = 0;
			for(ELEMENT element : collection)
				array[index++] = element;
		}
		return array;
	}
	
	@Override
	public <ELEMENT> ELEMENT[] instanciate(Class<ELEMENT> aClass,Collection<ELEMENT> collection) {
		return __instanciate__(aClass, collection);
	}
	
	@Override
	public <ELEMENT> ELEMENT[] instanciate(Class<ELEMENT> aClass, CollectionInstance<ELEMENT> collectionInstance) {
		return __instanciate__(aClass,CollectionHelper.isEmpty(collectionInstance) ? null : collectionInstance.get());
	}
	
	@Override
	public Object[] instanciate(Collection<Object> collection) {
		return instanciate(Object.class, collection);
	}
	
	@Override
	public Integer getSize(Object[] objects) {
		return objects == null ? null : objects.length;
	}
	
	/**/
	
	public static Boolean __isEmpty__(Object[] objects) {
		return objects == null || objects.length == 0;
	}
	
	public static Boolean __isNotEmpty__(Object[] objects) {
		return Boolean.FALSE.equals(__isEmpty__(objects));
	}
}
