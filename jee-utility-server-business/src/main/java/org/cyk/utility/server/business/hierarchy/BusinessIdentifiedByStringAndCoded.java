package org.cyk.utility.server.business.hierarchy;

import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByStringAndCoded;

public interface BusinessIdentifiedByStringAndCoded<ENTITY extends AbstractIdentifiedByStringAndCoded<ENTITY,?>> extends BusinessIdentifiedByString<ENTITY> {
	
}