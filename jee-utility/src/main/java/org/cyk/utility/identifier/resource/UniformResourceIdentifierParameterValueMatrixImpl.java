package org.cyk.utility.identifier.resource;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.map.AbstractMapInstanceImpl;
import org.cyk.utility.object.ObjectByStringMap;

@ApplicationScoped @Deprecated
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
	public UniformResourceIdentifierParameterValueMatrix setClasses(Collection<Class<?>> classes) {
		if(CollectionHelper.isNotEmpty(classes))
			for(Class<?> index : classes)
				getClassMap().set(index,__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(index).execute().getOutput());
		return this;
	}
	
	@Override
	public UniformResourceIdentifierParameterValueMatrix setClasses(Class<?>... classes) {
		return setClasses(List.of(classes));
	}
	
	@Override
	public UniformResourceIdentifierParameterValueMatrix setClass(Class<?> aClass) {
		return setClasses(aClass);
	}
	
	private static final String CLASS = __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setName(Class.class).execute().getOutput();
	
}
