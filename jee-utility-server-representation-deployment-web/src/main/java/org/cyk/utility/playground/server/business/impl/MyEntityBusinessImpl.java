package org.cyk.utility.playground.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.MyEntityBusiness;
import org.cyk.utility.playground.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.playground.server.persistence.entities.MyEntity;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class MyEntityBusinessImpl extends AbstractBusinessEntityImpl<MyEntity,MyEntityPersistence> implements MyEntityBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
