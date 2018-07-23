package org.cyk.utility.instance;

import java.util.Collection;

import org.cyk.utility.repository.Repository;

public interface InstanceRepository<INSTANCE> extends Repository {

	InstanceRepository<INSTANCE> add(Collection<INSTANCE> instances);
	InstanceRepository<INSTANCE> add(INSTANCE...instances);
	
	Collection<INSTANCE> readAll();
	
	INSTANCE getAt(Integer index);
    
	INSTANCE getFirst();
	
	INSTANCE getLast();
	
	INSTANCE getBySystemIdentifier(Object identifier,Boolean logIfResultIsNull);
	INSTANCE getBySystemIdentifier(Object identifier);
	
	InstanceRepository<INSTANCE> setInstanceClass(Class<INSTANCE> aClass);
	Class<INSTANCE> getInstanceClass();
}
