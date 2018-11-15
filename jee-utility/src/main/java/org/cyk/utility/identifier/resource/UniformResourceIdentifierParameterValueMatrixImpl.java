package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.map.AbstractMapInstanceImpl;
import org.cyk.utility.object.ObjectByStringMap;

@Singleton
public class UniformResourceIdentifierParameterValueMatrixImpl extends AbstractMapInstanceImpl<String, ObjectByStringMap> implements UniformResourceIdentifierParameterValueMatrix,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		set(CLASS,__inject__(ObjectByStringMap.class));
	}
	
	@Override
	public ObjectByStringMap getClassMap() {
		return get(CLASS);
	}
	
	@Override
	public UniformResourceIdentifierParameterValueMatrix setClass(Class<?> aClass) {
		getClassMap().set(aClass,__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(aClass).execute().getOutput());
		return this;
	}
	
	private static final String CLASS = __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setName(Class.class).execute().getOutput();
	
}
