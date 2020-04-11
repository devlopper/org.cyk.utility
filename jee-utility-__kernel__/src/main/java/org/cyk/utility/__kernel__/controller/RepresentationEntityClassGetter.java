package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.jboss.weld.exceptions.IllegalArgumentException;

public interface RepresentationEntityClassGetter {

	Class<?> get(Class<?> controllerEntityClass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements RepresentationEntityClassGetter,Serializable {
		
		@Override
		public Class<?> get(Class<?> controllerEntityClass) {
			if(controllerEntityClass == null)
				throw new IllegalArgumentException("controller entity class is required");
			String representationEntityClassName = ClassHelper.buildName(controllerEntityClass.getPackageName(), controllerEntityClass.getSimpleName()
					, new NamingModel().client().controller().entities(), new NamingModel().server().representation().entities().suffix());
			return ClassHelper.getByName(representationEntityClassName);
		}
	}
	
	/**/
	
	static RepresentationEntityClassGetter getInstance() {
		return Helper.getInstance(RepresentationEntityClassGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}