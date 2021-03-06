package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.clazz.AbstractClassFunctionImpl;
import org.cyk.utility.client.controller.ControllerLayer;

public class DataTransferObjectClassGetterImpl extends AbstractClassFunctionImpl implements DataTransferObjectClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> dataClass;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class __execute__() throws Exception {
		Class<?> dataClass = getDataClass();
		Class<?> transferClass = __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(dataClass);
		return transferClass;
	}
	
	@Override
	public Class<?> getDataClass() {
		return dataClass;
	}

	@Override
	public DataTransferObjectClassGetter setDataClass(Class<?> dataClass) {
		this.dataClass = dataClass;
		return this;
	}

	
	
}
