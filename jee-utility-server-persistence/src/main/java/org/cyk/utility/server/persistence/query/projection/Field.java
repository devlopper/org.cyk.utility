package org.cyk.utility.server.persistence.query.projection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.field.FieldInstance;

public interface Field extends Objectable {

	FieldInstance getInstance();
	Field setInstance(FieldInstance instance);
	
}
