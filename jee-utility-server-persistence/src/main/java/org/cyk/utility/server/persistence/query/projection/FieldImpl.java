package org.cyk.utility.server.persistence.query.projection;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.field.FieldInstance;

@Deprecated
public class FieldImpl extends AbstractObject implements Field,Serializable {
	private static final long serialVersionUID = 1L;
	
	private FieldInstance instance;
	
	@Override
	public FieldInstance getInstance() {
		return instance;
	}

	@Override
	public Field setInstance(FieldInstance instance) {
		this.instance = instance;
		return this;
	}

}
