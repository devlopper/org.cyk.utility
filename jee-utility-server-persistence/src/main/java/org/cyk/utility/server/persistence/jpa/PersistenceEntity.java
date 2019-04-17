package org.cyk.utility.server.persistence.jpa;

@Deprecated
public interface PersistenceEntity<ENTITY extends AbstractIdentifiedByStringAndBusinessCode> extends org.cyk.utility.server.persistence.PersistenceEntity<ENTITY> {

	ENTITY readByCode(String code);
	
	PersistenceEntity<ENTITY> deleteByCode(String code);
}
