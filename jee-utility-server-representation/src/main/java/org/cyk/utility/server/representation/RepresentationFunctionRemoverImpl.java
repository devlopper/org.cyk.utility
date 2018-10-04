package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.value.ValueUsageType;

public class RepresentationFunctionRemoverImpl extends AbstractRepresentationFunctionRemoverImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __executeBusiness__() {
		if(getEntities()!=null)
			__injectBusiness__().deleteMany((Collection<Object>) __inject__(InstanceHelper.class).buildMany(getPersistenceEntityClass(),getEntities()));
		else if(getEntity()!=null) {
			Object persistenceEntity = __inject__(InstanceHelper.class).buildOne(getPersistenceEntityClass(),getEntity());
			__injectBusiness__().delete(persistenceEntity);
		}else if(getEntityIdentifier()!=null) {
			Object identifier = getEntityIdentifier();
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			if(ValueUsageType.SYSTEM.equals(valueUsageType))
				identifier = __injectNumberHelper__().getLong(identifier);
			__injectBusiness__().deleteByClassByIdentififerByValueUsageType(getPersistenceEntityClass(),identifier,getEntityIdentifierValueUsageType());
		}else {
			/*Object identifier = getEntityIdentifier();
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			if(ValueUsageType.SYSTEM.equals(valueUsageType))
				identifier = __injectNumberHelper__().getLong(identifier);
			Object entity = __inject__(Business.class).findOne(getPersistenceEntityClass(), identifier, new Properties().setValueUsageType(valueUsageType));
			__inject__(Business.class).delete(__inject__(InstanceHelper.class).buildOne(getPersistenceEntityClass(),entity));
			*/
			
			__injectBusiness__().deleteAll(getPersistenceEntityClass());
		}
	}
	
}
