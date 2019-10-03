package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.api.ParentChildBusiness;
import org.cyk.utility.server.persistence.api.ParentChildPersistence;
import org.cyk.utility.server.persistence.entities.ParentChild;

@ApplicationScoped
public class ParentChildBusinessImpl extends AbstractBusinessEntityImpl<ParentChild,ParentChildPersistence> implements ParentChildBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
