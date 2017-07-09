package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;

@Singleton
public class CollectionHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static CollectionHelper INSTANCE;
	
	public static CollectionHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CollectionHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public <ELEMENT> Collection<ELEMENT> get(Boolean distinct,@SuppressWarnings("unchecked") ELEMENT...elements){
		Collection<ELEMENT> result = Boolean.TRUE.equals(distinct) ? new LinkedHashSet<ELEMENT>() : new ArrayList<ELEMENT>();
		if(elements!=null)
			for(ELEMENT element : elements)
				if(element!=null)
					result.add(element);
		return result;
	}
	
	public <ELEMENT> Collection<ELEMENT> get(@SuppressWarnings("unchecked") ELEMENT...elements){
		return get(Boolean.FALSE,elements);
	}
	
	public <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,Boolean append,Collection<ELEMENT> elements){
		Collection<ELEMENT> result = Boolean.TRUE.equals(append) ? collection : null;
		if(result==null)
			result = new ArrayList<ELEMENT>();
		if(Boolean.TRUE.equals(append))
			;
		else
			if(collection!=null)
				result.addAll(collection);
		result.addAll(elements);
		return result;
	}
	
	public <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,Boolean append,@SuppressWarnings("unchecked") ELEMENT...elements){
		return add(collection,append,get(elements));
	}
		
	public <ELEMENT> Boolean contains(Collection<ELEMENT> collection1,Collection<ELEMENT> collection2){
		if(collection1==null)
			if(collection2==null)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		else
			if(collection2==null)
				return Boolean.TRUE;
			else
				return collection1.containsAll(collection2);
	}
	
	public Boolean isEmpty(Collection<?> collection){
		return collection==null || collection.isEmpty();
	}

	public String concatenate(Collection<Object> collection, String separator) {
		if(isEmpty(collection))
			return Constant.EMPTY_STRING;
		return StringUtils.join(collection,separator);
	}
	
	@SuppressWarnings("unchecked")
	public <ELEMENT> ELEMENT[] getArray(Collection<ELEMENT> collection){
		return (ELEMENT[]) (collection == null ? null : collection.toArray());
	}
}
