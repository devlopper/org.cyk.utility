package org.cyk.utility.__kernel__.field.container;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

public interface FieldContainer {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	default <T> T getBySystemIdentifier(Class<T> klass,Object identifier) {
		if(klass == null || identifier == null)
			return null;
		if(klass.getAnnotation(Entity.class) == null) {
			T instance = ClassHelper.instanciate(klass);
			if(instance instanceof IdentifiableSystem)
				((IdentifiableSystem)instance).setSystemIdentifier(identifier);
			else
				throw new RuntimeException("get by system identifier : cannot set system identifier to type "+klass);
			return instance;
		}
		return InstanceHelper.getBySystemIdentifier(klass, identifier);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	default <T> T getByBusinessIdentifier(Class<T> klass,Object identifier) {
		if(klass == null || identifier == null)
			return null;
		if(klass.getAnnotation(Entity.class) == null) {
			T instance = ClassHelper.instanciate(klass);
			if(instance instanceof IdentifiableBusiness)
				((IdentifiableBusiness)instance).setBusinessIdentifier(identifier);
			else
				throw new RuntimeException("get by business identifier : cannot set business identifier to type "+klass);
			return instance;
		}
		return InstanceHelper.getByBusinessIdentifier(klass, identifier);
	}
	
}
