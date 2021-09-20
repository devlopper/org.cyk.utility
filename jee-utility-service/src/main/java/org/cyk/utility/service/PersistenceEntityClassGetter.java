package org.cyk.utility.service;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface PersistenceEntityClassGetter {

	Class<?> get(Class<?> serviceEntityClass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements PersistenceEntityClassGetter,Serializable {
		
		@Override
		public Class<?> get(Class<?> serviceEntityClass) {
			if(serviceEntityClass == null)
				throw new IllegalArgumentException("service entity class is required");
			String persistenceEntityClassName = StringUtils.replace(serviceEntityClass.getName(), ".service.", ".persistence.");
			persistenceEntityClassName = StringUtils.substringBeforeLast(persistenceEntityClassName, "Dto");
			return ClassHelper.getByName(persistenceEntityClassName);
		}
	}
	
	/**/
	
	static PersistenceEntityClassGetter getInstance() {
		return Helper.getInstance(PersistenceEntityClassGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}