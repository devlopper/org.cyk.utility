package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractRepresentationFunctionRemoverImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionRemover, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}
	
	@Override
	protected void __executeBusiness__() {
		//TODO be carefull. identifier value must be converted to persistence entity identifier type
		if(__injectCollectionHelper__().isNotEmpty(__entitiesSystemIdentifiers__))
			__injectBusiness__().deleteByIdentifiers(__persistenceEntityClass__, __entitiesSystemIdentifiers__,ValueUsageType.SYSTEM);
		
		if(__injectCollectionHelper__().isNotEmpty(__entitiesBusinessIdentifiers__))
			__injectBusiness__().deleteByIdentifiers(__persistenceEntityClass__, __entitiesBusinessIdentifiers__,ValueUsageType.BUSINESS);
	}
	
	@Override
	protected void __initialiseEntitiesIdentifiers__() {
		super.__initialiseEntitiesIdentifiers__();
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isEmpty(__entitiesSystemIdentifiers__)) && 
				Boolean.TRUE.equals(__injectCollectionHelper__().isEmpty(__entitiesBusinessIdentifiers__))) {
			//Remove all
			__entitiesSystemIdentifiers__ = __injectBusiness__().find(__persistenceEntityClass__).stream().map(x -> ((AbstractIdentifiedByString)x).getIdentifier())
					.collect(Collectors.toList());
		}
	}
}
