package org.cyk.utility.persistence.server.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Queries extends AbstractCollectionInstanceImpl<Query> implements Serializable {

	public static Boolean IS_REGISTERABLE_TO_ENTITY_MANAGER = Boolean.FALSE;
	
	private Boolean isRegisterableToEntityManager = IS_REGISTERABLE_TO_ENTITY_MANAGER;
	
	public Queries() {
		setElementClass(Query.class);
	}
	
	@Override
	public Queries setCollectionClass(Class<?> collectionClass) {
		return (Queries) super.setCollectionClass(collectionClass);
	}
	
}
