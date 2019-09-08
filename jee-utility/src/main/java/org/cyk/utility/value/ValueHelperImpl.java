package org.cyk.utility.value;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.throwable.ThrowableHelperImpl;

@ApplicationScoped
public class ValueHelperImpl extends AbstractHelper implements ValueHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private static ValueHelper INSTANCE;
	public static ValueHelper getInstance(Boolean isNew) {
		//if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE = DependencyInjection.inject(ValueHelper.class);
		return INSTANCE;
	}
	public static ValueHelper getInstance() {
		return getInstance(null);
	}
	
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
	public Boolean isEmpty(Object value) {
		Boolean isEmpty = value == null;
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof String) && __inject__(StringHelper.class).isEmpty((String) value);
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof Collection) && __inject__(CollectionHelper.class).isEmpty((Collection<?>)value);
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof CollectionInstance<?>) && __inject__(CollectionHelper.class).isEmpty((CollectionInstance<?>)value);
		
		return Boolean.TRUE.equals(isEmpty);
	}
	
	@Override
	public Boolean isNotEmpty(Object value) {
		return !Boolean.TRUE.equals(isEmpty(value));
	}
	
	@Override
	public Boolean isBlank(Object value) {
		Boolean isBlank = isEmpty(value);
		if(!Boolean.TRUE.equals(isBlank))
			isBlank = (value instanceof String) && __inject__(StringHelper.class).isBlank((String) value);
		return Boolean.TRUE.equals(isBlank);
	}

	@Override
	public Boolean isNotBlank(Object value) {
		return !Boolean.TRUE.equals(isBlank(value));
	}
	
	@Override
	public <T> T returnOrThrowIfBlank(String name, T value) {
		//TODO use isBlank method
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
	
	public static <T> T __defaultToIfNull__(Class<T> aClass,T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	public static <T> T __defaultToIfNull__(T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	public static <T> T __defaultToIfBlank__(T value,T defaultValue){
		return __isBlank__(value) ? defaultValue : value;
	}
	
	/**/
	
	public static <FROM, CLASS> CLASS __cast__(Object object, CLASS aClass) {
		return (CLASS) object;
	}
	
	public static Boolean __isEmpty__(Object value) {
		Boolean isEmpty = value == null;
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof String) && StringHelperImpl.__isEmpty__((String) value);
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof Collection) && CollectionHelperImpl.__isEmpty__((Collection<?>)value);
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof CollectionInstance<?>) && CollectionHelperImpl.__isEmpty__((CollectionInstance<?>)value);
		
		return Boolean.TRUE.equals(isEmpty);
	}
	
	public static Boolean __isNotEmpty__(Object value) {
		return !Boolean.TRUE.equals(__isEmpty__(value));
	}
	
	public static Boolean __isBlank__(Object value) {
		Boolean isBlank = __isEmpty__(value);
		if(!Boolean.TRUE.equals(isBlank))
			isBlank = (value instanceof String) && __inject__(StringHelper.class).isBlank((String) value);
		return Boolean.TRUE.equals(isBlank);
	}

	public static Boolean __isNotBlank__(Object value) {
		return !Boolean.TRUE.equals(__isBlank__(value));
	}
	
	public static void __throwIfBlank__(String name, Object value) {
		if(Boolean.TRUE.equals(__isBlank__(value)))
			ThrowableHelperImpl.__throwRuntimeException__(name+" is required.");
	}
}
