package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.value.ValueHelper;
import org.cyk.utility.value.ValueUsageType;

@ApplicationScoped
public class InstanceHelperImpl extends AbstractHelper implements InstanceHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private static InstanceHelper INSTANCE;
	public static InstanceHelper getInstance(Boolean isNew) {
		//if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE =  DependencyInjection.inject(InstanceHelper.class);
		return INSTANCE;
	}
	public static InstanceHelper getInstance() {
		return getInstance(null);
	}
	
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
	public <INSTANCE> INSTANCE getBySystemIdentifierOrBusinessIdentifier(INSTANCE instance) {
		INSTANCE result = null;
		if(instance != null) {
			Object identifier = __inject__(FieldHelper.class).getFieldValueSystemIdentifier(instance);
			if(identifier == null) {
				identifier = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(instance);
				if(identifier != null)
					result = (INSTANCE) getByIdentifierBusiness(instance.getClass(), identifier);
			}else {
				result = (INSTANCE) getByIdentifierSystem(instance.getClass(), identifier);
			}
		}
		return result;
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
		Collection<INSTANCE> instances = Collections.synchronizedList(new ArrayList<>());
		if(__inject__(CollectionHelper.class).isNotEmpty(fieldsValuesObjects)) {
			//instances = new ArrayList<>();
			for(Object index : fieldsValuesObjects) {
				instances.add(buildOne(aClass, index,properties));
			}
			/*
			RunnablesExecutor runnablesExecutor = __inject__(RunnablesExecutor.class);
			runnablesExecutor.getExecutorServiceBuilder(Boolean.TRUE).setCorePoolSize(10);
			runnablesExecutor.getExecutorServiceBuilder(Boolean.TRUE).setMaximumPoolSize(15);
			for(Object index : fieldsValuesObjects) {
				runnablesExecutor.addRunnables(new Runnable() {
					@Override
					public void run() {
						instances.add(buildOne(aClass, index,properties));
					}
				});	
			}
			runnablesExecutor.execute();
			*/
		}
		return instances;
	}
	
	@Override
	public <INSTANCE> Collection<INSTANCE> buildMany(Class<INSTANCE> aClass, Collection<?> fieldsValuesObjects) {
		return buildMany(aClass, fieldsValuesObjects, null);
	}
	
	@Override
	public Boolean isPersisted(Object instance) {
		return Boolean.TRUE.equals(__inject__(ValueHelper.class).isNotBlank(__inject__(FieldHelper.class).getFieldValueSystemIdentifier(instance)));
	}

}
