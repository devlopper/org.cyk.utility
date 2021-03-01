package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface RepresentationClassGetter {

	Class<?> get(Class<?> controllerEntityClass,Function function);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements RepresentationClassGetter,Serializable {
		
		@Override
		public Class<?> get(Class<?> controllerEntityClass,Function function) {
			if(controllerEntityClass == null)
				throw new IllegalArgumentException("controller entity class is required");
			if(function == null)
				throw new IllegalArgumentException("function is required");
			return __get__(controllerEntityClass, function);
		}
		
		protected Class<?> __get__(Class<?> controllerEntityClass,Function function) {
			String representationClassName = ClassHelper.buildName(controllerEntityClass.getPackageName(), controllerEntityClass.getSimpleName()
					, new NamingModel().client().controller().entities(), new NamingModel().server().representation().entities()
					.setSuffix(StringHelper.applyCase(function.name(), Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER)));
			return ClassHelper.getByName(representationClassName);
		}
	}
	
	/**/
	
	static RepresentationClassGetter getInstance() {
		return Helper.getInstance(RepresentationClassGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static enum Function{
		SAVER
		
		;
	}
}