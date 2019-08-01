package org.cyk.utility.server.persistence;

import java.io.Serializable;

public abstract class AbstractPersistenceEntityIdentifiedByStringImpl<ENTITY> extends AbstractPersistenceEntityImpl<ENTITY> implements PersistenceEntityIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

}
