package org.cyk.utility.persistence.server.audit;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface AuditClassGetter {

	Class<?> get(Class<?> klass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements AuditClassGetter,Serializable  {
		
		@Override
		public Class<?> get(Class<?> klass) {
			if(klass == null)
				return null;
			return ClassHelper.getByName(klass.getName()+SUFFIX,Boolean.FALSE);
		}
		
		/**/
		
		private static final String SUFFIX = "Audit";
	}
	
	/**/
	
	static AuditClassGetter getInstance() {
		return Helper.getInstance(AuditClassGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}