package org.cyk.utility.persistence.server.hibernate;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.persistence.server.MetricsManager;
import org.cyk.utility.persistence.server.audit.AuditReader;
import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorGetter;

public interface Initializer {

	static void initialize() {
		org.cyk.utility.persistence.server.Initializer.initialize();		
		DependencyInjection.setQualifierClassTo(Hibernate.Class.class, ProcedureExecutor.class,MetricsManager.class,AuditReader.class);
		DependencyInjection.inject(ProcedureExecutorGetter.class).setProcedureExecutor(DependencyInjection.inject(ProcedureExecutor.class));
		LogHelper.logConfig(String.format("Persistence server using hibernate has been initialized"), Initializer.class);
	}
}