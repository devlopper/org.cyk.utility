package org.cyk.utility.method;

import java.io.Serializable;
import java.lang.reflect.Constructor;

import javax.inject.Singleton;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class MethodHelperImpl extends AbstractHelper implements MethodHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <CLASS> Constructor<CLASS> getConstructor(Class<CLASS> aClass, Class<?>[] parameters) {
		try {
			return ConstructorUtils.getMatchingAccessibleConstructor(aClass, parameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
