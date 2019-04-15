package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.layer.SystemLayerRepresentation;
import org.cyk.utility.value.ValueUsageType;

@Singleton
public class RepresentationImpl extends AbstractObject implements Representation, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Representation create(Object object) {
		@SuppressWarnings("unchecked")
		Class<RepresentationEntity<Object,Object,Object>> interfaceClass = (Class<RepresentationEntity<Object,Object,Object>>) __inject__(SystemLayerRepresentation.class).getInterfaceClassFromEntityClassName(object.getClass());
		if(interfaceClass == null){
			System.err.println("No specific representation interface found for entity "+object.getClass());
			//super.create(object, properties);
		}else{
			__inject__(interfaceClass).createOne(object);
		}
		return this;
	}

	@Override
	public Representation createMany(Collection<Object> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getOne(Class<?> aClass, Object identifier, ValueUsageType valueUsageType,Strings fieldNames) {
		@SuppressWarnings("unchecked")
		Class<RepresentationEntity<Object,Object,Object>> interfaceClass = (Class<RepresentationEntity<Object,Object,Object>>) __inject__(SystemLayerRepresentation.class).getInterfaceClassFromEntityClassName(aClass);
		if(interfaceClass == null){
			System.err.println("No specific representation interface found for class "+aClass);
			return null;
		}else{
			return __inject__(interfaceClass).getOne(identifier.toString(), valueUsageType.name(),fieldNames == null ? null : fieldNames.concatenate(",")).getEntity();
		}
	}
	
	@Override
	public Representation delete(Object object) {
		@SuppressWarnings("unchecked")
		Class<RepresentationEntity<Object,Object,Object>> interfaceClass = (Class<RepresentationEntity<Object,Object,Object>>) __inject__(SystemLayerRepresentation.class).getInterfaceClassFromEntityClassName(object.getClass());
		if(interfaceClass == null){
			System.err.println("No specific representation interface found for entity "+object.getClass());
			//super.create(object, properties);
		}else{
			__inject__(interfaceClass).deleteOne(object);
		}
		return this;
	}

	@Override
	public Representation deleteMany(Collection<Object> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Representation deleteAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
