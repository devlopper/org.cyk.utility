package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.runnable.RunnableHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

public abstract class AbstractMapperSourceDestinationImpl<SOURCE,DESTINATION> extends AbstractMapperImpl implements MapperSourceDestination<SOURCE,DESTINATION>,Serializable {
	private static final long serialVersionUID = 1L;
	
	public static Integer EXECUTOR_CORE_POOL_SIZE = null;
	public static Integer EXECUTOR_QUEUE_SIZE = null;
	
	protected Class<SOURCE> __sourceClass__;
	protected Class<DESTINATION> __destinationClass__;
	protected Collection<Field> __persistableFields__;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		if(__sourceClass__ == null)
			__sourceClass__ = (Class<SOURCE>) ClassHelper.getParameterAt(getClass(), 0);
		if(__destinationClass__ == null)
			__destinationClass__ = (Class<DESTINATION>) ClassHelper.getParameterAt(getClass(), 1);
		if(__persistableFields__ == null)
			__persistableFields__ = FieldHelper.getPersistablesSingleValueAssociation(__destinationClass__);
	}
	
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
		if(__persistableFields__ != null)
			__processPersistableFields__(source, destination, __persistableFields__);
	}
	
	/**
	 * Set all persistable fields with their corresponding persisted value
	 * @param source
	 * @param destination
	 * @param fields
	 * @return
	 */
	protected void __processPersistableFields__(SOURCE source,DESTINATION destination,Collection<Field> fields) {
		if(fields == null || fields.isEmpty())
			return;		
		for(Field index : fields) {
			Type type = FieldHelper.getType(index, null);
			//if(type instanceof Collection<?>) {
				
			//}else {
				Object value = FieldHelper.read(destination, index);
				if(value != null) {
					Object persisted = InstanceHelper.getByIdentifier((Class<?>)type,FieldHelper.readIdentifier(value));
					if(persisted != null)
						FieldHelper.write(destination, index, persisted);
				}
			//}			
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
		if(destinations == null || destinations.isEmpty())
			return null;		
		final Collection<SOURCE> sources = new ArrayList<>();		
		Integer executorCorePoolSize = EXECUTOR_CORE_POOL_SIZE;
		if(executorCorePoolSize!=null && executorCorePoolSize>0) {
			//Parrallel processing
			Collection<Runnable> runnables = new ArrayList<>();
			for(DESTINATION destination : destinations) {
				runnables.add(new Runnable() {
					@Override
					public void run() {
						sources.add(getSource(destination, properties));
					}
				});
			}
			RunnableHelper.run(runnables, "mapper get sources", RunnableHelper.instanciateExecutorService(executorCorePoolSize));
		}else {
			//Serial processing
			for(DESTINATION destination : destinations) {
				sources.add(getSource(destination, properties));
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
