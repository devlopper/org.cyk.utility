package org.cyk.utility.client.controller.data;

import org.cyk.utility.clazz.ClassFunction;

public interface DataRepresentationClassGetter extends ClassFunction {

	Class<?> getDataClass();
	DataRepresentationClassGetter setDataClass(Class<?> dataClass);
}
