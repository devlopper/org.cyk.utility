package org.cyk.utility.instance;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.helper.Helper;
import org.cyk.utility.value.ValueUsageType;

public interface InstanceHelper extends Helper {

	<INSTANCE> Collection<INSTANCE> getByFieldNameByValueUsageType(Class<INSTANCE> aClass,FieldName fieldName,ValueUsageType valueUsageType,Object value,Properties properties);
	<INSTANCE> Collection<INSTANCE> getByFieldNameByValueUsageType(Class<INSTANCE> aClass,FieldName fieldName,ValueUsageType valueUsageType,Object value);
	<INSTANCE> INSTANCE getByIdentifierBusiness(Class<INSTANCE> aClass,Object value,Properties properties);
	<INSTANCE> INSTANCE getByIdentifierBusiness(Class<INSTANCE> aClass,Object value);
	<INSTANCE> INSTANCE getByIdentifierSystem(Class<INSTANCE> aClass,Object value,Properties properties);
	<INSTANCE> INSTANCE getByIdentifierSystem(Class<INSTANCE> aClass,Object value);
	
	<INSTANCE> INSTANCE getBySystemIdentifierOrBusinessIdentifier(INSTANCE instance);
	
	<INSTANCE> INSTANCE buildOne(Class<INSTANCE> aClass,Object fieldsValuesObject,Properties properties);
	<INSTANCE> INSTANCE buildOne(Class<INSTANCE> aClass,Object fieldsValuesObject);
	<INSTANCE> Collection<INSTANCE> buildMany(Class<INSTANCE> aClass,Collection<?> fieldsValuesObjects,Properties properties);
	<INSTANCE> Collection<INSTANCE> buildMany(Class<INSTANCE> aClass,Collection<?> fieldsValuesObjects);
	
	Boolean isPersisted(Object instance);
}
