package org.cyk.utility.server.business.hierarchy;

import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString;
import org.cyk.utility.server.persistence.jpa.hierarchy.Hierarchies;

public interface HierarchyBusiness<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByString<?>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>> extends BusinessEntity<HIERARCHY> {

}
