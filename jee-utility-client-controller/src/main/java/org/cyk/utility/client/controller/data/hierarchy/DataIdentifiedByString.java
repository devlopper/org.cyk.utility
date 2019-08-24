package org.cyk.utility.client.controller.data.hierarchy;

import java.util.Collection;

public interface DataIdentifiedByString<NODE> extends org.cyk.utility.client.controller.data.DataIdentifiedByString {
	
	Collection<NODE> get__parents__();
	Collection<NODE> get__parents__(Boolean instanciateIfNull);
	DataIdentifiedByString<NODE> set__parents__(Collection<NODE> __parents__);
	DataIdentifiedByString<NODE> add__parents__(Collection<NODE> __parents__);
	DataIdentifiedByString<NODE> add__parents__(NODE...__parents__);
	
	/**/
	
	String PROPERTY_PARENTS = "parents";
}
