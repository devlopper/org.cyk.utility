package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.layer.SystemLayerBusiness;

public class BusinessImpl extends AbstractBusinessServiceProviderImpl<Object> implements Business,Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> ENTITY findOne(Class<ENTITY> aClass, Object identifier, Properties properties) {
		return (ENTITY) __inject__(BusinessFunctionReader.class).setEntityClass(aClass).setEntityIdentifier(identifier).execute().getProperties().getEntity();
	}

	@Override
	public <ENTITY> ENTITY findOne(Class<ENTITY> aClass, Object identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Collection<ENTITY> findMany(Class<ENTITY> aClass, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Collection<ENTITY> findMany(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessServiceProvider<Object> deleteAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessServiceProvider<Object> create(Object object, Properties properties) {
		@SuppressWarnings("unchecked")
		Class<BusinessEntity<Object>> interfaceClass = (Class<BusinessEntity<Object>>) __inject__(SystemLayerBusiness.class).getInterfaceClassFromEntityClassName(object.getClass());
		if(interfaceClass == null){
			__logWarn__("No specific business interface found for persistence entity "+object.getClass());
			super.create(object, properties);
		}else{
			__inject__(interfaceClass).create(object, properties);
		}
		return this;
	}
}
