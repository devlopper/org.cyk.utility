package org.cyk.utility.__kernel__.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Singleton
public class KernelHelperImpl extends AbstractObject implements KernelHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getInterfaceImplementationNameSuffix() {
		return IMPL;
	}
	
	@Override
	public String getInterfaceSimpleName(Class<?> aClass) {
		return aClass == null ? null : StringUtils.substringBefore(aClass.getSimpleName(),getInterfaceImplementationNameSuffix());
	}
	
	/**/
	
	private static final String IMPL = "Impl";
}
