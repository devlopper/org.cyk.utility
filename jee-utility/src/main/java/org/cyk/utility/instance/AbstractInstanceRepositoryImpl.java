package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.repository.AbstractRepositoryImpl;
import org.cyk.utility.repository.Repository;

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
			//System.out.println("AbstractInstanceRepositoryImpl.add() : "+this+" : "+instance);
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
		//System.out.println("AbstractInstanceRepositoryImpl.getLast() : "+this+" : "+instances);
		return __inject__(CollectionHelper.class).getLast(readAll());
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
