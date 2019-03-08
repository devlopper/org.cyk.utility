package org.cyk.utility.field;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;

@Singleton
public class FieldInstancesRuntimeImpl extends AbstractObject implements FieldInstancesRuntime,Serializable {
	private static final long serialVersionUID = 1L;

	private FieldInstances instances;
	
	@Override
	public FieldInstance get(Class<?> aClass,Collection<String> paths) {
		Log log = __inject__(Log.class).setLevel(LogLevel.TRACE);
		FieldInstance instance = null;
		String path = __inject__(FieldHelper.class).join(paths);
		if(aClass!=null && __inject__(StringHelper.class).isNotBlank(path)) {
			Strings fieldNames = __inject__(FieldHelper.class).disjoin(path);
			instance = getInstances(Boolean.TRUE).get(aClass,path);
			if(instance == null) {
				instance = __inject__(FieldInstance.class);
				instance.setClazz(aClass);
				instance.setPath(path);
				Collection<String> fieldContainerNames = fieldNames.getWithoutLast(1);
				if(__inject__(CollectionHelper.class).isEmpty(fieldContainerNames)) {
					
				}else {
					for(String index : fieldContainerNames)
						aClass = FieldUtils.getDeclaredField(aClass, index, Boolean.TRUE).getType();
				}
				instance.setField(__inject__(FieldGetter.class).execute(aClass, fieldNames.getLast()).getOutput().getFirst());
				instance.setType(__inject__(FieldTypeGetter.class).execute(instance.getField()).getOutput());
				getInstances(Boolean.TRUE).add(instance);
				log.getMessageBuilder(Boolean.TRUE).addParameter("field <<"+instance.getClazz()+"."+path+">> added to runtime collection");
			}else {
				log.getMessageBuilder(Boolean.TRUE).addParameter("field <<"+instance.getClazz()+"."+path+">> fetched from runtime collection");
			}
		}
		log.execute();
		return instance;
	}
	
	@Override
	public FieldInstance get(Class<?> aClass, String... paths) {
		return get(aClass,__inject__(CollectionHelper.class).instanciate(paths));
	}
	
	@Override
	public FieldInstances getInstances() {
		return instances;
	}
	
	@Override
	public FieldInstances getInstances(Boolean injectIfNull) {
		FieldInstances instances = (FieldInstances) __getInjectIfNull__(FIELD_INSTANCES, injectIfNull);
		instances.setCollectionClass(Set.class);
		return instances;
	}

	@Override
	public FieldInstancesRuntime setInstances(FieldInstances instances) {
		this.instances = instances;
		return this;
	}

	/**/
	
	public static final String FIELD_INSTANCES = "instances";
}
