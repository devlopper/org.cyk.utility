package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MapperClassNameGetter {

	default String get(String className) {
		if(StringHelper.isBlank(className))
			return null;
		return String.format("%sMapper", className);
	}
	
	default String get(Class<?> klass) {
		if(klass == null)
			return null;
		return get(klass.getName());
	}
	
	/**/

	public abstract class AbstractImpl extends AbstractObject implements MapperClassNameGetter,Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	/**/
	
	static MapperClassNameGetter getInstance() {
		return Helper.getInstance(MapperClassNameGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
