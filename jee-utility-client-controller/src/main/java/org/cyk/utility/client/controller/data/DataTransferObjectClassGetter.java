package org.cyk.utility.client.controller.data;

import org.cyk.utility.clazz.ClassFunction;

public interface DataTransferObjectClassGetter extends ClassFunction {

	Class<?> getDataClass();
	DataTransferObjectClassGetter setDataClass(Class<?> dataClass);
}
