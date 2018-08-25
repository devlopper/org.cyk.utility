package org.cyk.utility.method;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.cyk.utility.collection.CollectionHelper;

public abstract class AbstractMethodCallListenerPostConstructImpl extends AbstractMethodCallListenerImpl implements MethodCallListenerPostConstruct,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MethodCallListener setObject(Object object) {
		super.setObject(object);
		if(getMethod() == null)
			setMethod(__inject__(CollectionHelper.class).getFirst(__inject__(MethodGetter.class).setClazz(getObject().getClass()).addAnnotationClasses(PostConstruct.class).execute().getOutput()));
		return this;
	}
	
}
