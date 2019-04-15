package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.value.ValueUsageType;

@Singleton
public class InstanceHelperImpl extends AbstractHelper implements InstanceHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <INSTANCE> Collection<INSTANCE> getByFieldNameByValueUsageType(Class<INSTANCE> aClass, FieldName fieldName,ValueUsageType valueUsageType, Object value,Properties properties) {
		InstanceGetter instanceGetter = __inject__(InstanceGetter.class);
		instanceGetter.copyProperty(Properties.CONTEXT, properties);
		instanceGetter.copyProperty(Properties.REQUEST, properties);
		instanceGetter.copyProperty(Properties.FIELDS_NAMES, properties);
		return (Collection<INSTANCE>) instanceGetter.setClazz(aClass).setFieldName(fieldName).setValueUsageType(valueUsageType).setValue(value)
				.execute().getOutput();
	}

	@Override
	public <INSTANCE> Collection<INSTANCE> getByFieldNameByValueUsageType(Class<INSTANCE> aClass, FieldName fieldName,ValueUsageType valueUsageType, Object value) {
		return getByFieldNameByValueUsageType(aClass, fieldName, valueUsageType, value,null);
	}
	
	@Override
	public <INSTANCE> INSTANCE getByIdentifierSystem(Class<INSTANCE> aClass, Object value,Properties properties) {
		return __inject__(CollectionHelper.class).getFirst(getByFieldNameByValueUsageType(aClass, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, value,properties));
	}
	
	@Override
	public <INSTANCE> INSTANCE getByIdentifierSystem(Class<INSTANCE> aClass, Object value) {
		return getByIdentifierSystem(aClass, value, null);
	}

	@Override
	public <INSTANCE> INSTANCE getByIdentifierBusiness(Class<INSTANCE> aClass, Object value,Properties properties) {
		return __inject__(CollectionHelper.class).getFirst(getByFieldNameByValueUsageType(aClass, FieldName.IDENTIFIER, ValueUsageType.BUSINESS, value,properties));
	}
	
	@Override
	public <INSTANCE> INSTANCE getByIdentifierBusiness(Class<INSTANCE> aClass, Object value) {
		return getByIdentifierBusiness(aClass, value, null);
	}
	
	@Override
	public <INSTANCE> INSTANCE buildOne(Class<INSTANCE> aClass, Object fieldsValuesObject,Properties properties) {
		InstanceBuilder instanceBuilder = __inject__(InstanceBuilder.class);
		instanceBuilder.copyProperty(Properties.CONTEXT, properties);
		instanceBuilder.copyProperty(Properties.REQUEST, properties);
		instanceBuilder.copyProperty(Properties.FIELDS, properties);
		return (INSTANCE) instanceBuilder.setClazz(aClass).setFieldsValuesObject(fieldsValuesObject).execute().getOutput();
	}
	
	@Override
	public <INSTANCE> INSTANCE buildOne(Class<INSTANCE> aClass, Object fieldsValuesObject) {
		return buildOne(aClass, fieldsValuesObject, null);
	}
	
	@Override
	public <INSTANCE> Collection<INSTANCE> buildMany(Class<INSTANCE> aClass, Collection<?> fieldsValuesObjects,Properties properties) {
		Collection<INSTANCE> instances = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(fieldsValuesObjects)) {
			instances = new ArrayList<>();
			for(Object index : fieldsValuesObjects)
				instances.add(buildOne(aClass, index,properties));
		}
		return instances;
	}
	
	@Override
	public <INSTANCE> Collection<INSTANCE> buildMany(Class<INSTANCE> aClass, Collection<?> fieldsValuesObjects) {
		return buildMany(aClass, fieldsValuesObjects, null);
	}

}
