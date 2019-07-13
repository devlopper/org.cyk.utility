package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.type.BooleanHelper;

public abstract class AbstractRepresentationFunctionCreatorImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionCreator, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void __executeBusiness__() {
		if(__injectCollectionHelper__().isNotEmpty(__entities__)) {
			__persistenceEntities__ = (Collection<Object>) __inject__(MappingHelper.class).getDestinations(__entities__, __persistenceEntityClass__);
			Boolean isBatchable = __inject__(BooleanHelper.class).get(getProperties().getIsBatchable());
			if(Boolean.TRUE.equals(isBatchable)) {
				Integer batchSize = __injectNumberHelper__().getInteger(getProperties().getBatchSize());
				__inject__(Business.class).createByBatch(__persistenceEntities__, batchSize);
			}else {
				__inject__(Business.class).createMany(__persistenceEntities__);	
			}					
		}
	}
	
	@Override
	protected Status __computeResponseStatus__() {
		if(__throwable__==null)
			return javax.ws.rs.core.Response.Status.CREATED;
		return super.__computeResponseStatus__();
	}
}
