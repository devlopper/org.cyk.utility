package org.cyk.utility.server.persistence.jpa;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.server.persistence.AbstractPersistenceFunctionModifierImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionModifier;

@Dependent
public class PersistenceFunctionModifierImpl extends AbstractPersistenceFunctionModifierImpl implements PersistenceFunctionModifier {
	private static final long serialVersionUID = 1L;
	
	private EntityManager __entityManager__;
	
	@Override
	protected void __initialiseWorkingVariables__() {
		super.__initialiseWorkingVariables__();
		__entityManager__ = PersistenceHelper.getEntityManager(getProperties());
	}
	
	@Override
	protected void __executeWithEntity__(Object object) {
		__entityManager__.merge(object);
	}
	
	@Override
	protected void __flush__() {
		__entityManager__.flush();
	}
	
	@Override
	protected void __clear__() {
		__entityManager__.clear();
	}
	
	@Override
	protected void __executeQuery__(SystemAction action, Query query) {
		QueryHelper.execute(query, getProperties());
	}
	
}
