package org.cyk.utility.__kernel__.object.dynamic;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Default;

public abstract class AbstractProducerImpl extends AbstractObject implements Producer,Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<?> __getQualifierClass__(Class<?> aClass){
		return Default.class;
	}
	
	protected <T> T __produce__(Class<T> aClass) {
		Class<?> qualifierClass = __getQualifierClass__(aClass);
		if(qualifierClass == null) {
			System.out.println("Qualifier class for <<"+aClass+">> is null. Default will be used.");
			qualifierClass = Default.class;
		}
		return __injectByQualifiersClasses__(aClass,qualifierClass);
	}
	
}
