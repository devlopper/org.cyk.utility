package org.cyk.utility.clazz;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.Fields;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.value.ValueUsageType;

@ApplicationScoped
public class ClassInstancesRuntimeImpl extends AbstractObject implements ClassInstancesRuntime,Serializable {
	private static final long serialVersionUID = 1L;

	private ClassInstances instances;
	
	@Override
	public ClassInstance get(Class<?> aClass) {
		Log log = __inject__(Log.class).setLevel(LogLevel.TRACE);
		ClassInstance instance = aClass == null ? null : getInstances(Boolean.TRUE).get(aClass);
		if(instance == null) {
			instance = __inject__(ClassInstance.class).setClazz(aClass);
			instance.setIsPersistable(aClass.isAnnotationPresent(javax.persistence.Entity.class));
			instance.setIsTransferable(aClass.isAnnotationPresent(javax.xml.bind.annotation.XmlRootElement.class));
			Fields systemIdentifiersFields = __inject__(FieldGetter.class).setClazz(aClass).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM).execute().getOutput();
			instance.setSystemIdentifierField(__inject__(CollectionHelper.class).getFirst(systemIdentifiersFields));
			getInstances(Boolean.TRUE).add(instance);
			log.getMessageBuilder(Boolean.TRUE).addParameter("class <<"+aClass+">> added to runtime collection");
		}else {
			log.getMessageBuilder(Boolean.TRUE).addParameter("class <<"+aClass+">> fetched from runtime collection");
		}
		log.execute();
		return instance;
	}
	
	@Override
	public ClassInstances getInstances() {
		return instances;
	}
	
	@Override
	public ClassInstances getInstances(Boolean injectIfNull) {
		ClassInstances instances = (ClassInstances) __getInjectIfNull__(FIELD_INSTANCES, injectIfNull);
		instances.setCollectionClass(Set.class);
		return instances;
	}

	@Override
	public ClassInstancesRuntime setInstances(ClassInstances instances) {
		this.instances = instances;
		return this;
	}

	/**/
	
	public static final String FIELD_INSTANCES = "instances";
}
