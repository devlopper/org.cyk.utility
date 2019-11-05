package org.cyk.utility.playground.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.PersonTypeBusiness;
import org.cyk.utility.playground.server.persistence.api.PersonTypePersistence;
import org.cyk.utility.playground.server.persistence.entities.PersonType;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class PersonTypeBusinessImpl extends AbstractBusinessEntityImpl<PersonType,PersonTypePersistence> implements PersonTypeBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
