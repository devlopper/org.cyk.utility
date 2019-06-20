package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.value.ValueUsageType;

@Dependent
public class RepresentationFunctionRemoverImpl extends AbstractRepresentationFunctionRemoverImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __executeBusiness__() {
		if(getEntities()!=null)
			__injectBusiness__().deleteMany((Collection<Object>) __inject__(InstanceHelper.class).buildMany(getPersistenceEntityClass(),getEntities()));
		else if(getEntity()!=null) {
			ValueUsageType valueUsageType = null;
			Object identifier = __injectFieldHelper__().getFieldValueBusinessIdentifier(getEntity());
			if(identifier == null) {
				identifier = __injectFieldHelper__().getFieldValueSystemIdentifier(getEntity());
				valueUsageType = ValueUsageType.SYSTEM;
			}else
				valueUsageType = ValueUsageType.BUSINESS;
			
			Object persistenceEntity = __inject__(Persistence.class).readOne(getPersistenceEntityClass(), identifier,valueUsageType);
			//__inject__(InstanceHelper.class).buildOne(getPersistenceEntityClass(),getEntity());
			__injectBusiness__().delete(persistenceEntity);
		}else if(getEntityIdentifier()!=null) {
			//TODO avoid this - TO BE REMOVED
			Object identifier = getEntityIdentifier();
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			if(ValueUsageType.SYSTEM.equals(valueUsageType)) {
				//TODO convert base on field type
				//identifier = __injectNumberHelper__().getLong(identifier);
			}
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
