package org.cyk.utility.clazz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.ClassUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.number.NumberHelper;

public class ClassHelperImpl extends AbstractHelper implements ClassHelper , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isInstanceOf(Class<?> aClass, Class<?> baseClass) {
		if (aClass == null || baseClass == null)
			return Boolean.FALSE;
		return baseClass.isAssignableFrom(aClass);
	}

	@Override
	public <T> T instanciateOne(Class<T> aClass) {
		try {
			return aClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public <T> Collection<T> instanciate(Class<T> aClass, Integer count) {
		Collection<T> collection = null;
		if(DependencyInjection.inject(NumberHelper.class).isGreaterThanZero(count)){
			collection = new ArrayList<T>();
			for(Integer index = 0; index < count; index++)
				collection.add(instanciateOne(aClass));
		}
		return collection;
	}

	@Override
	public <T> T instanciate(Class<T> aClass) {
		return instanciateOne(aClass);
	}
	
	@Override
	public Boolean areEqual(Class<?> class1, Class<?> class2,Class<?>...classes) {
		if(class1 == null || class2 == null)
			return null;
		return class1.equals(class2);
	}
	
	@Override
	public Class<?> getWrapper(Class<?> aClass) {
		return ClassUtils.primitiveToWrapper(aClass);
	}

	@Override
	public Boolean isNumber(Class<?> aClass) {
		return isInstanceOf(Number.class, getWrapper(aClass));
	}

	@Override
	public Boolean isString(Class<?> aClass) {
		return areEqual(java.lang.String.class, aClass);
	}

	@Override
	public Boolean isDate(Class<?> aClass) {
		return areEqual(java.util.Date.class, aClass);
	}

	@Override
	public Boolean isBoolean(Class<?> aClass) {
		return areEqual(Boolean.class, getWrapper(aClass));
	}

}
