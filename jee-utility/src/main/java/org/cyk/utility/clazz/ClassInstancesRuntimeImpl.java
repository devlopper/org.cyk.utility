package org.cyk.utility.clazz;

import java.io.Serializable;
import java.util.Set;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Singleton
public class ClassInstancesRuntimeImpl extends AbstractObject implements ClassInstancesRuntime,Serializable {
	private static final long serialVersionUID = 1L;

	private ClassInstances instances;
	
	@Override
	public ClassInstance get(Class<?> aClass) {
		ClassInstance instance = aClass == null ? null : getInstances(Boolean.TRUE).get(aClass);
		if(instance == null) {
			instance = __inject__(ClassInstance.class).setClazz(aClass);
			instance.setIsPersistable(aClass.isAnnotationPresent(javax.persistence.Entity.class));
			instance.setIsTransferable(aClass.isAnnotationPresent(javax.xml.bind.annotation.XmlRootElement.class));
		}
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
