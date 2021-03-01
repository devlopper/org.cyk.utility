package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface PersistenceEntityClassGetter {

	Class<?> get(Class<?> representationEntityClass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements PersistenceEntityClassGetter,Serializable {
		
		@Override
		public Class<?> get(Class<?> representationEntityClass) {
			if(representationEntityClass == null)
				throw new IllegalArgumentException("representation entity class is required");
			String persistenceEntityClassName = ClassHelper.buildName(representationEntityClass.getPackageName(), representationEntityClass.getSimpleName()
					, new NamingModel().server().representation().entities().suffix(), new NamingModel().server().persistence().entities());						
			return ClassHelper.getByName(persistenceEntityClassName);
		}
	}
	
	/**/
	
	static PersistenceEntityClassGetter getInstance() {
		return Helper.getInstance(PersistenceEntityClassGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}