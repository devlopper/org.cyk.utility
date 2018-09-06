package org.cyk.utility.server.persistence.jpa;

public interface PersistenceEntity<ENTITY extends AbstractEntity> extends org.cyk.utility.server.persistence.PersistenceEntity<ENTITY> {

	ENTITY readByCode(String code);
	
	PersistenceEntity<ENTITY> deleteByCode(String code);
}
