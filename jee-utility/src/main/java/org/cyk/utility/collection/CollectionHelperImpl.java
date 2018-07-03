package org.cyk.utility.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.value.ValueHelper;

public class CollectionHelperImpl extends AbstractHelper implements CollectionHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isEmpty(Collection<?> collection){
		return collection==null || collection.isEmpty();
	}
	
	@Override
	public Boolean isNotEmpty(Collection<?> collection){
		return Boolean.FALSE.equals(isEmpty(collection));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> instanciate(Class<COLLECTION> collectionClass,ELEMENT...elements){
		if(collectionClass==null)
			return null;
		if(Set.class.equals(collectionClass))
			collectionClass = __inject__(ValueHelper.class).cast(LinkedHashSet.class,collectionClass);
		else if(List.class.equals(collectionClass))
			collectionClass = __inject__(ValueHelper.class).cast(ArrayList.class, collectionClass);
		else if(Collection.class.equals(collectionClass))
			collectionClass = __inject__(ValueHelper.class).cast(ArrayList.class, collectionClass);			
		Collection<ELEMENT> collection = (Collection<ELEMENT>) __inject__(ClassHelper.class).instanciateOne(collectionClass);
		if(__inject__(ArrayHelper.class).isNotEmpty(elements))
			collection.addAll(Arrays.asList(elements));
		return collection;
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> instanciate(@SuppressWarnings("unchecked") ELEMENT...elements){
		return instanciate(ArrayList.class, elements);
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> concatenate(Collection<Collection<ELEMENT>> collections) {
		Collection<ELEMENT> collection = null;
		if(isNotEmpty(collections)){
			collection = new ArrayList<ELEMENT>();
			for(Collection<ELEMENT> index : collections)
				if(isNotEmpty(index))
					collection.addAll(index);
		}
		return collection;
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> concatenate(@SuppressWarnings("unchecked") Collection<ELEMENT>... collections) {
		return concatenate(instanciate(collections));
	}
	
	@Override
	public Integer getSize(Collection<?> collection) {
		if(isEmpty(collection))
			return 0;
		return collection.size();
	}
	
	@Override
	public <ELEMENT> ELEMENT getElementAt(Collection<ELEMENT> collection,Integer index){
		ELEMENT element = null;
		if(isNotEmpty(collection) && index < getSize(collection)){
			if(collection instanceof List)
				element = ((List<ELEMENT>)collection).get(index.intValue());
			else {
				java.util.Iterator<ELEMENT> iterator = collection.iterator();
				Integer count = 0;
				while (count++ <= index)
					element = iterator.next();
			}
		}
		return element;
	}

	@Override
	public <ELEMENT> ELEMENT getFirst(Collection<ELEMENT> collection){
		if(isEmpty(collection))
			return null;
		return collection.iterator().next();
	}
	
	@Override
	public <ELEMENT> ELEMENT getLast(Collection<ELEMENT> collection){
		if(isEmpty(collection))
			return null;
		if(collection instanceof List)
			return ((List<ELEMENT>)collection).get(((List<ELEMENT>)collection).size()-1);
		new RuntimeException("cannot find last on collection of type "+collection.getClass());
		return null;
	}
	
	@Override
	public CollectionHelper clear(Collection<?> collection){
		if(collection!=null)
			collection.clear();
		return this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <ELEMENT> Collection<ELEMENT> cast(Class<ELEMENT> aClass,Collection<?> collection){
		Collection<ELEMENT> result;
		if(collection==null){
			result = null;
		}else{
			result = new ArrayList<ELEMENT>();
			for(Object item : collection)
				result.add((ELEMENT) item);	
		}
		return result;
	}
}
