package org.cyk.utility.instance;

import java.util.Collection;

import org.cyk.utility.repository.Repository;

public interface InstanceRepository<INSTANCE> extends Repository {

	Collection<INSTANCE> readAll();
	
	INSTANCE getAt(Integer index);
    
	INSTANCE getFirst();
	
	INSTANCE getLast();
	
}
