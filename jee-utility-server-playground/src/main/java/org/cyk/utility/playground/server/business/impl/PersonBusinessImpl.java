package org.cyk.utility.playground.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.PersonBusiness;
import org.cyk.utility.playground.server.persistence.api.PersonPersistence;
import org.cyk.utility.playground.server.persistence.entities.Person;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class PersonBusinessImpl extends AbstractBusinessEntityImpl<Person,PersonPersistence> implements PersonBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
