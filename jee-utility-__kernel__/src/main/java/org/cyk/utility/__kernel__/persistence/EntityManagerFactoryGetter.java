package org.cyk.utility.__kernel__.persistence;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityManagerFactoryGetter {

	EntityManagerFactory get();
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityManagerFactoryGetter,Serializable {

	}
	
	/**/
	
	static EntityManagerFactoryGetter getInstance() {
		return Helper.getInstance(EntityManagerFactoryGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);	
}