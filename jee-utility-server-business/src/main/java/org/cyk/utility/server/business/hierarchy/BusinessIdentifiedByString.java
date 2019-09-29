package org.cyk.utility.server.business.hierarchy;

import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString;

public interface BusinessIdentifiedByString<ENTITY extends AbstractIdentifiedByString<ENTITY>> extends BusinessEntity<ENTITY> {

}