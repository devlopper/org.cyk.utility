package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.system.action.SystemActionDelete;

public abstract class AbstractRepresentationFunctionRemoverImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionRemover, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}
	
	@Override
	protected void __executeBusiness__() {
		if(CollectionHelper.isNotEmpty(__entitiesSystemIdentifiers__))
			__injectBusiness__().deleteByIdentifiers(__persistenceEntityClass__, __entitiesSystemIdentifiers__,ValueUsageType.SYSTEM);
		
		if(CollectionHelper.isNotEmpty(__entitiesBusinessIdentifiers__))
			__injectBusiness__().deleteByIdentifiers(__persistenceEntityClass__, __entitiesBusinessIdentifiers__,ValueUsageType.BUSINESS);
	}
	
	@Override
	protected void __initialiseWorkingVariables__() {
		super.__initialiseWorkingVariables__();
		if(Boolean.TRUE.equals(CollectionHelper.isEmpty(__entitiesSystemIdentifiers__)) && 
				Boolean.TRUE.equals(CollectionHelper.isEmpty(__entitiesBusinessIdentifiers__))) {
			//Remove all
			__entitiesSystemIdentifiers__ = __injectBusiness__().findIdentifiers(__persistenceEntityClass__, ValueUsageType.SYSTEM);
		}
	}
}
