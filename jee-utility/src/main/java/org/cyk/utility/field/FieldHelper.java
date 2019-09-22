package org.cyk.utility.field;

import org.cyk.utility.__kernel__.properties.Properties;

public interface FieldHelper extends org.cyk.utility.__kernel__.field.FieldHelper {
	
	FieldHelper copy(Object source,Object destination,Properties properties);
	FieldHelper copy(Object source,Object destination);
	
}
