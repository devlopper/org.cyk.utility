package org.cyk.utility.persistence.server.hibernate;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.view.MaterializedViewManager;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApplicationScoped @Hibernate @Getter @Setter @Accessors(chain=true)
public class MaterializedViewManagerImpl extends MaterializedViewManager.AbstractImpl implements Serializable {

	@Inject @Hibernate
	private ProcedureExecutor __procedureExecutor__;
	
	@Override
	public ProcedureExecutor getProcedureExecutor() {
		return __procedureExecutor__;
	}
	
	@Override
	public AbstractImpl setProcedureExecutor(ProcedureExecutor procedureExecutor) {
		this.__procedureExecutor__ = procedureExecutor;
		return this;
	}
}