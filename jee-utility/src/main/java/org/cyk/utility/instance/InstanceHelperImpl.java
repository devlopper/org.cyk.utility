package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.value.ValueUsageType;

@Singleton
public class InstanceHelperImpl extends AbstractHelper implements InstanceHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <INSTANCE> Collection<INSTANCE> getByFieldNameByValueUsageType(Class<INSTANCE> aClass, FieldName fieldName,ValueUsageType valueUsageType, Object value) {
		return (Collection<INSTANCE>) __inject__(InstanceGetter.class).setClazz(aClass).setFieldName(fieldName).setValueUsageType(valueUsageType).setValue(value)
				.execute().getOutput();
	}

	@Override
	public <INSTANCE> INSTANCE getByIdentifierBusiness(Class<INSTANCE> aClass, Object value) {
		return __inject__(CollectionHelper.class).getFirst(getByFieldNameByValueUsageType(aClass, FieldName.IDENTIFIER, ValueUsageType.BUSINESS, value));
	}

	@Override
	public <INSTANCE> INSTANCE buildOne(Class<INSTANCE> aClass, Object fieldsValuesObject) {
		return (INSTANCE) __inject__(InstanceBuilder.class).setClazz(aClass).setFieldsValuesObject(fieldsValuesObject).execute().getOutput();
	}
	
	@Override
	public <INSTANCE> Collection<INSTANCE> buildMany(Class<INSTANCE> aClass, Collection<?> fieldsValuesObjects) {
		Collection<INSTANCE> instances = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(fieldsValuesObjects)) {
			instances = new ArrayList<>();
			for(Object index : fieldsValuesObjects)
				instances.add(buildOne(aClass, index));
		}
		return instances;
	}

}
