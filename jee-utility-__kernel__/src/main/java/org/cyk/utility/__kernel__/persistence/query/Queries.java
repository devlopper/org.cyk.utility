package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

public class Queries extends AbstractCollectionInstanceImpl<Query> implements Serializable {

	public Queries() {
		setElementClass(Query.class);
	}
	
	@Override
	public Queries setCollectionClass(Class<?> collectionClass) {
		return (Queries) super.setCollectionClass(collectionClass);
	}
	
}
