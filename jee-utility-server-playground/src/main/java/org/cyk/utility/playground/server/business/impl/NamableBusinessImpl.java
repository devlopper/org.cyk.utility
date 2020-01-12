package org.cyk.utility.playground.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.NamableBusiness;
import org.cyk.utility.playground.server.persistence.api.NamablePersistence;
import org.cyk.utility.playground.server.persistence.entities.Namable;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class NamableBusinessImpl extends AbstractBusinessEntityImpl<Namable,NamablePersistence> implements NamableBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
