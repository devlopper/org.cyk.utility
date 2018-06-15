package org.cyk.utility.instance;

import java.io.Serializable;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.repository.AbstractRepositoryImpl;

public abstract class AbstractInstanceRepositoryImpl<INSTANCE> extends AbstractRepositoryImpl implements InstanceRepository<INSTANCE>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public INSTANCE getAt(Integer index) {
		return __inject__(CollectionHelper.class).getElementAt(readAll(), index);
	}
	
	@Override
	public INSTANCE getFirst() {
		return __inject__(CollectionHelper.class).getFirst(readAll());
	}
	
	@Override
	public INSTANCE getLast() {
		return __inject__(CollectionHelper.class).getLast(readAll());
	}
}
