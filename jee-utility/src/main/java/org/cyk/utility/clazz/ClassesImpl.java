package org.cyk.utility.clazz;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

@Dependent @SuppressWarnings("rawtypes")
public class ClassesImpl extends AbstractCollectionInstanceImpl<Class> implements Classes,Serializable {
	private static final long serialVersionUID = -2467852996446599349L;

	@Override
	public Classes add(Class... elements) {
		return (Classes) super.add(elements);
	}
	
	@Override
	public Classes add(Collection<Class> collection) {
		return (Classes) super.add(collection);
	}
	
}
