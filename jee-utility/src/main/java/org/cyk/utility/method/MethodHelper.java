package org.cyk.utility.method;

import java.lang.reflect.Constructor;

import org.cyk.utility.helper.Helper;

public interface MethodHelper extends Helper {

	<CLASS> Constructor<CLASS> getConstructor(Class<CLASS> aClass, Class<?>[] parameters);

}
