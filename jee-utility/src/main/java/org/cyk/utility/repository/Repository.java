package org.cyk.utility.repository;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface Repository extends Singleton {

	Long countAll();
	Repository clear();
}
