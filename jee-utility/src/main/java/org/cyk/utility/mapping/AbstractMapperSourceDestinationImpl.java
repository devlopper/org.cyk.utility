package org.cyk.utility.mapping;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassInstance;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.field.FieldInstancesRuntime;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.runnable.RunnablesExecutor;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

public abstract class AbstractMapperSourceDestinationImpl<SOURCE,DESTINATION> extends AbstractMapperImpl implements MapperSourceDestination<SOURCE,DESTINATION>,Serializable {
	private static final long serialVersionUID = 1L;
	
	public static Integer EXECUTOR_CORE_POOL_SIZE = null;
	public static Integer EXECUTOR_QUEUE_SIZE = null;
	
	protected Class<SOURCE> __sourceClass__;
	protected Class<DESTINATION> __destinationClass__;
	
	@BeforeMapping
	protected void listenGetDestinationBefore(SOURCE source,@MappingTarget DESTINATION destination) {
		__listenGetDestinationBefore__(source, destination);
	}
	
	protected void __listenGetDestinationBefore__(SOURCE source,DESTINATION destination) {
		
	}
	
	@AfterMapping
	protected void listenGetDestinationAfter(SOURCE source,@MappingTarget DESTINATION destination) {
		__listenGetDestinationAfter__(source, destination);
	}
	
	protected void __listenGetDestinationAfter__(SOURCE source,DESTINATION destination) {
		Collection<Field> fields = null;
		fields = __getPersistableFields__(source, destination);
		__processPersistableFields__(source, destination, fields);
	}
	
	/**
	 * Find all persistable fields
	 * @param source
	 * @param destination
	 * @return
	 */
	protected Collection<Field> __getPersistableFields__(SOURCE source,DESTINATION destination) {
		Collection<Field> fields = null;
		ClassInstance classInstance = DependencyInjection.inject(ClassInstancesRuntime.class).get(destination.getClass());
		if(classInstance.getFields() != null) {
			for(Field index : classInstance.getFields().get()) {
				FieldInstance fieldInstance = DependencyInjection.inject(FieldInstancesRuntime.class).get(destination.getClass(), index.getName());
				if(Boolean.TRUE.equals(DependencyInjection.inject(ClassInstancesRuntime.class).get((Class<?>) fieldInstance.getType()).getIsPersistable()) && 
						fieldInstance.getField().isAnnotationPresent(javax.persistence.ManyToOne.class)) {
					if(fields == null)
						fields = new ArrayList<>();
					fields.add(index);
				}
			}
		}
		return fields;
	}
	
	/**
	 * Set all persistable fields with their corresponding persisted value
	 * @param source
	 * @param destination
	 * @param fields
	 * @return
	 */
	protected void __processPersistableFields__(SOURCE source,DESTINATION destination,Collection<Field> fields) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(fields))) {
			for(Field index : fields) {
				Object value = org.cyk.utility.__kernel__.field.FieldHelper.read(destination, index);
				if(value != null) {
					Object persisted = DependencyInjection.inject(InstanceHelper.class).getBySystemIdentifierOrBusinessIdentifier(value);
					if(persisted != null)
						org.cyk.utility.__kernel__.field.FieldHelper.write(destination, index, persisted);
				}
			}
		}
	}
	
	/* Source */
		
	@Override
	public SOURCE getSource(DESTINATION destination, Properties properties) {
		SOURCE source = getSource(destination);
		__listenGetSourceAfter__(destination, source, properties);
		return source;
	}
	
	@Override
	public Collection<SOURCE> getSources(Collection<DESTINATION> destinations, Properties properties) {
		final Collection<SOURCE> sources = new ArrayList<>();
		if(CollectionHelper.isNotEmpty(destinations)) {
			Integer executorCorePoolSize = EXECUTOR_CORE_POOL_SIZE;
			if(executorCorePoolSize!=null && executorCorePoolSize>0) {
				//Parrallel processing
				RunnablesExecutor runner = DependencyInjection.inject(RunnablesExecutor.class);
				runner.getExecutorServiceBuilder(Boolean.TRUE).setCorePoolSize(executorCorePoolSize);
				runner.getExecutorServiceBuilder(Boolean.TRUE).setQueueSize(destinations.size());
				runner.setTimeOut(1l).setTimeOutUnit(TimeUnit.SECONDS);
				for(DESTINATION destination : destinations) {
					runner.addRunnables(new Runnable() {
						@Override
						public void run() {
							sources.add(getSource(destination, properties));
						}
					});
				}
				runner.execute();
			}else {
				//Serial processing
				for(DESTINATION destination : destinations) {
					sources.add(getSource(destination, properties));
				}
			}
		}
		return sources;
	}
	
	@Override
	public Collection<SOURCE> getSources(Collection<DESTINATION> destinations) {
		return getSources(destinations, null);
	}
	
	@BeforeMapping
	protected void listenGetSourceBefore(DESTINATION destination,@MappingTarget SOURCE source) {
		__listenGetSourceBefore__(destination, source);
	}
	
	protected void __listenGetSourceBefore__(DESTINATION destination,SOURCE source) {
		
	}
	
	@AfterMapping
	protected void listenGetSourceAfter(DESTINATION destination,@MappingTarget SOURCE source) {
		__listenGetSourceAfter__(destination, source);
	}
	
	protected void __listenGetSourceAfter__(DESTINATION destination,SOURCE source) {
		
	}
	
	protected void __listenGetSourceAfter__(DESTINATION destination,SOURCE source, Properties properties) {
		
	}
}
