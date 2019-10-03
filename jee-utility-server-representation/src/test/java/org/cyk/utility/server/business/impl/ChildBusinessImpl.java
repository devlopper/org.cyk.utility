package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.api.ChildBusiness;
import org.cyk.utility.server.persistence.api.ChildPersistence;
import org.cyk.utility.server.persistence.entities.Child;

@ApplicationScoped
public class ChildBusinessImpl extends AbstractBusinessEntityImpl<Child,ChildPersistence> implements ChildBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
