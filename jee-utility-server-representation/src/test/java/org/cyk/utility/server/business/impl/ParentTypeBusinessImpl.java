package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.api.ParentTypeBusiness;
import org.cyk.utility.server.persistence.api.ParentTypePersistence;
import org.cyk.utility.server.persistence.entities.ParentType;

@ApplicationScoped
public class ParentTypeBusinessImpl extends AbstractBusinessEntityImpl<ParentType,ParentTypePersistence> implements ParentTypeBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
