package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.AbstractObject;

public class TestedEntityChildByParentsImpl extends AbstractObject implements TestedEntityChildByParents,Serializable {

	@Override
	public Collection<TestedEntityChild> readByBusinessIdentifiers(Collection<String> businessIdentifiers) {
		// TODO Auto-generated method stub
		return null;
	}

}
