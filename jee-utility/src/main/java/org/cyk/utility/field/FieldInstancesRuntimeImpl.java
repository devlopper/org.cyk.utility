package org.cyk.utility.field;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.GeneratedValue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.__kernel__.annotation.Generatable;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;

@ApplicationScoped
public class FieldInstancesRuntimeImpl extends AbstractObject implements FieldInstancesRuntime,Serializable {
	private static final long serialVersionUID = 1L;

	private FieldInstances instances;
	
	@Override
	public FieldInstance get(Class<?> klass,Collection<String> paths) {
		Log log = __inject__(Log.class).setLevel(LogLevel.TRACE);
		FieldInstance instance = null;
		String path = org.cyk.utility.__kernel__.field.FieldHelper.join(paths);
		if(klass!=null && StringHelper.isNotBlank(path)) {
			Strings fieldNames = __inject__(Strings.class).add(org.cyk.utility.__kernel__.field.FieldHelper.disjoin(path));
			instance = getInstances(Boolean.TRUE).get(klass,path);
			if(instance == null) {
				instance = __inject__(FieldInstance.class);
				instance.setClazz(klass);
				instance.setPath(path);
				Collection<String> fieldContainerNames = fieldNames.getWithoutLast(1);
				if(CollectionHelper.isEmpty(fieldContainerNames)) {
					
				}else {
					for(String index : fieldContainerNames)
						klass = FieldUtils.getDeclaredField(klass, index, Boolean.TRUE).getType();
				}
				instance.setField(FieldHelper.getByName(klass, fieldNames.getLast()));
				instance.setType(FieldHelper.getType(instance.getField(),klass));
				instance.setIsGeneratable(instance.getField().isAnnotationPresent(GeneratedValue.class) || instance.getField().isAnnotationPresent(Generatable.class));
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
		return get(aClass,List.of(paths));
	}
	
	@Override
	public FieldInstances getInstances() {
		return instances;
	}
	
	@Override
	public FieldInstances getInstances(Boolean injectIfNull) {
		if(instances == null && Boolean.TRUE.equals(injectIfNull))
			instances = __inject__(FieldInstances.class);
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
