package org.cyk.utility.value;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;

@Singleton
public class ValueHelperImpl extends AbstractHelper implements ValueHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <T> T defaultToIfNull(Class<T> aClass,T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	@Override
	public <T> T defaultToIfNull(T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	@Override
	public <FROM, CLASS> CLASS cast(Object object, CLASS aClass) {
		return (CLASS) object;
	}

	@Override
	public <T> T returnOrThrowIfBlank(String name, T value) {
		Boolean isThrow = value == null;
		if(!Boolean.TRUE.equals(isThrow))
			isThrow = (value instanceof String) && __inject__(StringHelper.class).isBlank((String) value);
		if(!Boolean.TRUE.equals(isThrow))
			isThrow = (value instanceof Collection) && __inject__(CollectionHelper.class).isEmpty((Collection<?>)value);
		if(!Boolean.TRUE.equals(isThrow))
			isThrow = (value instanceof CollectionInstance<?>) && __inject__(CollectionHelper.class).isEmpty((CollectionInstance<?>)value);
		
		if(Boolean.TRUE.equals(isThrow))
			__inject__(ThrowableHelper.class).throwRuntimeException(name+" is required.");
		return value;
	}
}
