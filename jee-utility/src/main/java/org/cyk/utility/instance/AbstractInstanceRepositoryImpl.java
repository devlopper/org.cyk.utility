package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.log.Log;
import org.cyk.utility.repository.AbstractRepositoryImpl;
import org.cyk.utility.repository.Repository;

public abstract class AbstractInstanceRepositoryImpl<INSTANCE> extends AbstractRepositoryImpl implements InstanceRepository<INSTANCE>, Serializable {
	private static final long serialVersionUID = 1L;

	protected Set<INSTANCE> instances;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setInstanceClass((Class<INSTANCE>) org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt(getClass(), 0));
	}
	
	@Override
	public InstanceRepository<INSTANCE> add(Collection<INSTANCE> instances) {
		this.instances = (Set<INSTANCE>) CollectionHelper.add(LinkedHashSet.class, this.instances, Boolean.TRUE, instances);
		return this;
	}
	
	@Override
	public InstanceRepository<INSTANCE> add(INSTANCE...instances) {
		add(List.of(instances));
		return this;
	}
	
	@Override
	public INSTANCE getAt(Integer index) {
		return CollectionHelper.getElementAt(readAll(), index);
	}
	
	@Override
	public INSTANCE getFirst() {
		return CollectionHelper.getFirst(readAll());
	}
	
	@Override
	public INSTANCE getLast() {
		return CollectionHelper.getLast(readAll());
	}
	
	@Override
	public INSTANCE getBySystemIdentifier(Object identifier,Boolean logIfResultIsNull) {
		INSTANCE instance = null;
		if(identifier == null){
			
		}else{
			if(CollectionHelper.isNotEmpty(instances)){
				for(INSTANCE index : instances){
					Object indexSystemIdentifier = org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifier(index);
					if(identifier.equals(indexSystemIdentifier)){
						instance = index;
						break;
					}
				}
			}
		}
		if(instance == null){
			if(Boolean.TRUE.equals(logIfResultIsNull)){
				__inject__(Log.class).executeWarn(getInstanceClass().getSimpleName()+" with system identifier "+identifier+" not found");
			}
		}
		return instance;
	}
	
	@Override
	public INSTANCE getBySystemIdentifier(Object identifier) {
		return getBySystemIdentifier(identifier, Boolean.FALSE);
	}
	
	@Override
	public Collection<INSTANCE> readAll() {
		return instances;
	}
	
	@Override
	public Long countAll() {
		return NumberHelper.getLong(CollectionHelper.getSize(instances));
	}
	
	@Override
	public Repository clear() {
		if(instances!=null)
			instances.clear();
		return this;
	}

	@Override
	public Class<INSTANCE> getInstanceClass() {
		return (Class<INSTANCE>) getProperties().getFromPath(Properties.INSTANCE,Properties.CLASS);
	}
	
	@Override
	public InstanceRepository<INSTANCE> setInstanceClass(Class<INSTANCE> aClass) {
		getProperties().setFromPath(new Object[]{Properties.INSTANCE,Properties.CLASS}, aClass);
		return this;
	}
}
