package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityIdentifiedByStringImpl;
import org.cyk.utility.server.persistence.api.MyEntityDetailPersistence;
import org.cyk.utility.server.persistence.entities.MyEntityDetail;

@ApplicationScoped
public class MyEntityDetailPersistenceImpl extends AbstractPersistenceEntityIdentifiedByStringImpl<MyEntityDetail> implements MyEntityDetailPersistence,Serializable {
	private static final long serialVersionUID = 1L;


}
