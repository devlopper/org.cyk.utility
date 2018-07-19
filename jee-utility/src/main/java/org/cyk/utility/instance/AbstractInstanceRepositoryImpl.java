package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.repository.AbstractRepositoryImpl;
import org.cyk.utility.repository.Repository;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractInstanceRepositoryImpl<INSTANCE> extends AbstractRepositoryImpl implements InstanceRepository<INSTANCE>, Serializable {
	private static final long serialVersionUID = 1L;

	protected Set<INSTANCE> instances;
	
	@Override
	public InstanceRepository<INSTANCE> add(INSTANCE instance) {
		if(instance == null){
			//TODO log warning
		}else {
			if(instances == null)
				instances = new LinkedHashSet<INSTANCE>();
			instances.add(instance);
		}
		return null;
	}
	
	@Override
	public INSTANCE getAt(Integer index) {
		return __inject__(CollectionHelper.class).getElementAt(readAll(), index);
	}
	
	@Override
	public INSTANCE getFirst() {
		return __inject__(CollectionHelper.class).getFirst(readAll());
	}
	
	@Override
	public INSTANCE getLast() {
		return __inject__(CollectionHelper.class).getLast(readAll());
	}
	
	@Override
	public INSTANCE getBySystemIdentifier(Object identifier) {
		INSTANCE instance = null;
		if(identifier == null){
			
		}else{
			if(__inject__(CollectionHelper.class).isNotEmpty(instances)){
				for(INSTANCE index : instances){
					Object indexSystemIdentifier = getSystemIdentifier(index);
					if(identifier.equals(indexSystemIdentifier)){
						instance = index;
						break;
					}
				}
			}
		}
		return instance;
	}
	
	protected Object getSystemIdentifier(INSTANCE instance){
		return __inject__(FieldValueGetter.class).execute(instance, FieldName.IDENTIFIER, ValueUsageType.SYSTEM);
	}
	
	@Override
	public Collection<INSTANCE> readAll() {
		return instances;
	}
	
	@Override
	public Long countAll() {
		return __inject__(NumberHelper.class).getLong(__inject__(CollectionHelper.class).getSize(instances));
	}
	
	@Override
	public Repository clear() {
		if(instances!=null)
			instances.clear();
		return this;
	}
}
