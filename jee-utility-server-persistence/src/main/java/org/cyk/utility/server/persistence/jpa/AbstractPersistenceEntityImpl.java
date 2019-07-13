package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

@Deprecated
public abstract class AbstractPersistenceEntityImpl<ENTITY extends AbstractIdentifiedByStringAndCoded> extends org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl<ENTITY> implements PersistenceEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ENTITY readByCode(String code) {
		return readByBusinessIdentifier(code);
	}

	@Override
	public PersistenceEntity<ENTITY> deleteByCode(String code) {
		return (PersistenceEntity<ENTITY>) deleteByBusinessIdentifier(code);
	}
	
}
