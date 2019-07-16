package org.cyk.utility.object;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

@Dependent
public class ObjectsImpl extends AbstractCollectionInstanceImpl<Object> implements Objects,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Objects add(Collection<Object> collection) {
		return (Objects) super.add(collection);
	}
	
	@Override
	public Objects add(Object... elements) {
		return (Objects) super.add(elements);
	}
	
}
