package org.cyk.utility.persistence.server.hibernate;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;

public interface Initializer {

	static void initialize() {
		if(Boolean.TRUE.equals(INITIALIZED.get()))
			return;
		org.cyk.utility.persistence.server.Initializer.initialize();		
		DependencyInjection.setQualifierClassTo(Hibernate.Class.class, ProcedureExecutor.class);
		LogHelper.logInfo(String.format("Persistence server using hibernate has been initialized"), Initializer.class);
		INITIALIZED.set(Boolean.TRUE);
	}
	
	Value INITIALIZED = new Value();
}