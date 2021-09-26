package org.cyk.utility.service.server;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface PersistenceEntityClassGetter {

	Class<?> get(Class<?> klass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements PersistenceEntityClassGetter,Serializable {
		
		@Override
		public Class<?> get(Class<?> klass) {
			if(klass == null)
				return null;
			return __get__(klass);
		}
		
		protected abstract Class<?> __get__(Class<?> klass);
	}
	
	/**/
	
	static PersistenceEntityClassGetter getInstance() {
		return Helper.getInstance(PersistenceEntityClassGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}