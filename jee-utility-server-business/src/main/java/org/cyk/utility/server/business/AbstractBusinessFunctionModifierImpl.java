package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;

public abstract class AbstractBusinessFunctionModifierImpl extends AbstractBusinessFunctionTransactionImpl implements BusinessFunctionModifier, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionUpdate.class));
	}

	@Override
	protected void __execute__(SystemAction action) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(__entities__))) {
			__inject__(Persistence.class).updateMany(__entities__);
		}
	}
	
	@Override @Transactional
	public BusinessFunctionModifier execute() {
		return (BusinessFunctionModifier) super.execute();
	}
}
