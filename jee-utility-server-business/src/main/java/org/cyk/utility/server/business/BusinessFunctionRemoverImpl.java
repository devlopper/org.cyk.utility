package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;

public class BusinessFunctionRemoverImpl extends AbstractBusinessFunctionRemoverImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override @Transactional
	public BusinessFunctionRemover execute() {
		return super.execute();
	}
	
	@Override
	protected void __execute__(SystemAction action) {
		Object entity = getEntity();
		if(entity == null) {
			__inject__(Persistence.class).deleteAll(getEntityClass());
		}else {
			__inject__(Persistence.class).delete(entity);	
		}
		
	}

}
