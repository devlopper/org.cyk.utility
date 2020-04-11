package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityCounter {

	<ENTITY> Long count(Class<ENTITY> controllerEntityClass,Arguments<ENTITY> arguments);
	
	default <ENTITY> Long count(Class<ENTITY> controllerEntityClass) {
		Arguments<ENTITY> arguments = new Arguments<ENTITY>();
		arguments.setControllerEntityClass(controllerEntityClass);
		return count(controllerEntityClass, arguments);
	}
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractObject implements EntityCounter,Serializable {
		
		@Override
		public <ENTITY> Long count(Class<ENTITY> controllerEntityClass,Arguments<ENTITY> arguments) {
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			if(arguments.getResponseEntityClass() == null)
				arguments.setResponseEntityClass(Long.class);
			arguments.prepare(controllerEntityClass, org.cyk.utility.__kernel__.representation.EntityCounter.class);
			Response response = ((org.cyk.utility.__kernel__.representation.EntityCounter)arguments.__representation__).count(arguments.__representationArguments__);
			arguments.finalise(response);
			return (Long) arguments.__responseEntity__;			
		}
	}
	
	/**/
	
	static EntityCounter getInstance() {
		return Helper.getInstance(EntityCounter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}