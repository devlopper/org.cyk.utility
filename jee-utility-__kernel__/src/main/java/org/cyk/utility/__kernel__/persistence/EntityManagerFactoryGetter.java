package org.cyk.utility.__kernel__.persistence;

import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityManagerFactoryGetter {

	EntityManagerFactory get();
	
	/**/
	
	static EntityManagerFactoryGetter getInstance() {
		return Helper.getInstance(EntityManagerFactoryGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
