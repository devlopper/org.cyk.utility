package org.cyk.utility.instance;

import java.util.Collection;

import org.cyk.utility.repository.Repository;

public interface InstanceRepository<INSTANCE> extends Repository {

	InstanceRepository<INSTANCE> add(INSTANCE instance);
	
	Collection<INSTANCE> readAll();
	
	INSTANCE getAt(Integer index);
    
	INSTANCE getFirst();
	
	INSTANCE getLast();
	
	INSTANCE getBySystemIdentifier(Object identifier);
	
}
