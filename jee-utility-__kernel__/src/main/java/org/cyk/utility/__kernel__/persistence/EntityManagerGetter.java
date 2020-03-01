package org.cyk.utility.__kernel__.persistence;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityManagerGetter {

	EntityManager get();
	
	/**/
	
	static EntityManagerGetter getInstance() {
		return Helper.getInstance(EntityManagerGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
