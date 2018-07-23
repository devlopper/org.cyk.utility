package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@Singleton
public class MyEntityPersistenceImpl extends AbstractPersistenceEntityImpl<MyEntity> implements MyEntityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByIntegerValue,countByIntegerValue;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQuery(readByIntegerValue, "SELECT r FROM MyEntity r WHERE r.integerValue = :integerValue");
		addQuery(countByIntegerValue, "SELECT COUNT(r) FROM MyEntity r WHERE r.integerValue = :integerValue",Long.class);
	}
	
	@Override
	public Collection<MyEntity> readByIntegerValue(Integer value) {
		return __readMany__(MyEntity.FIELD_INTEGER_VALUE, value);
	}
	
	@Override
	public Long countByIntegerValue(Integer value) {
		return __count__(MyEntity.FIELD_INTEGER_VALUE, value);
	}
	
}
