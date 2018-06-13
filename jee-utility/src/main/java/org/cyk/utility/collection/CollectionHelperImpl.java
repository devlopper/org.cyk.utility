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
			collectionClass = (Class<COLLECTION>) LinkedHashSet.class;
		else if(List.class.equals(collectionClass))
			collectionClass = (Class<COLLECTION>) ArrayList.class;
		else if(Collection.class.equals(collectionClass))
			collectionClass = (Class<COLLECTION>) ArrayList.class;			
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
	
}
