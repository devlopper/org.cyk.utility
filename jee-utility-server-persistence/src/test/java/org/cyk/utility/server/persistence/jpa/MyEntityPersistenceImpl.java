package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@Singleton
public class MyEntityPersistenceImpl extends AbstractPersistenceEntityImpl<MyEntity> implements MyEntityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByIntegerValue;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQuery(readByIntegerValue, "SELECT r FROM MyEntity r WHERE r.integerValue = :integerValue");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<MyEntity> readByIntegerValue(Integer value) {
		return (Collection<MyEntity>) __inject__(PersistenceFunctionReader.class)
				.setQueryIdentifier(__buildQueryStringIdentifierFromCurrentCall__())
				.setQueryParameter("integerValue", 2)
				.execute().getProperties().getEntities();
	}
	
	@Override
	public Long countByIntegerValue(Integer value) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
