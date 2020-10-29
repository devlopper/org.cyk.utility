package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.runnable.RunnableHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface MapperSourceDestination<SOURCE,DESTINATION> extends Mapper {

	DESTINATION getDestination(SOURCE source);
	Collection<DESTINATION> getDestinations(Collection<SOURCE> sources);
	
	SOURCE getSource(DESTINATION destination);
	SOURCE getSource(DESTINATION destination,Arguments arguments);
	SOURCE getSource(DESTINATION destination,Properties properties);
	
	Collection<SOURCE> getSources(Collection<DESTINATION> destinations);
	Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Arguments arguments);
	Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Properties properties);
	
	/**/
	
	public abstract class AbstractImpl<SOURCE,DESTINATION> extends Mapper.AbstractImpl implements MapperSourceDestination<SOURCE,DESTINATION>,Serializable {
		private static final long serialVersionUID = 1L;
		
		public static Integer EXECUTOR_CORE_POOL_SIZE = null;
		public static Integer EXECUTOR_QUEUE_SIZE = null;
		
		protected Class<SOURCE> __sourceClass__;
		protected Class<DESTINATION> __destinationClass__;
		protected Collection<Field> __persistableFields__;
		protected Collection<String> __sourceFieldsNameSettable__;
		
		@Override
		protected void __listenPostConstruct__() {
			super.__listenPostConstruct__();
			if(__sourceClass__ == null)
				__sourceClass__ = (Class<SOURCE>) ClassHelper.getParameterAt(getClass(), 0);
			if(__sourceClass__ != null) {
				Collection<Field> fields = FieldHelper.get(__sourceClass__);
				if(CollectionHelper.isNotEmpty(fields))
					for(Field field : fields) {
						if(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()))
							continue;
						if(__sourceFieldsNameSettable__ == null)
							__sourceFieldsNameSettable__ = new ArrayList<>();
						__sourceFieldsNameSettable__.add(field.getName());
					}
			}
			
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
		public SOURCE getSource(DESTINATION destination, Arguments arguments) {
			SOURCE source = getSource(destination);
			__listenGetSourceAfter__(List.of(destination), List.of(source), arguments);
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
				RunnableHelper.run(runnables, "mapper get sources", RunnableHelper.instantiateExecutorService(executorCorePoolSize));
			}else {
				//Serial processing
				for(DESTINATION destination : destinations) {
					sources.add(getSource(destination, properties));
				}
			}
			
			return sources;
		}
		
		@Override
		public Collection<SOURCE> getSources(Collection<DESTINATION> destinations, Arguments arguments) {
			if(CollectionHelper.isEmpty(destinations))
				return null;
			Collection<SOURCE> sources = destinations.parallelStream().map(destination -> getSource(destination, arguments)).collect(Collectors.toList());
			__listenGetSourceAfter__(destinations, sources, arguments);
			return sources;
		}
		
		@Override
		public Collection<SOURCE> getSources(Collection<DESTINATION> destinations) {
			return getSources(destinations, (Properties)null);
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
		
		protected void __listenGetSourceAfter__(DESTINATION destination,SOURCE source, Properties properties) {}
		
		protected void __listenGetSourceAfter__(DESTINATION destination,SOURCE source, Arguments arguments) {}
		
		protected void __listenGetSourceAfter__(Collection<DESTINATION> destinations,Collection<SOURCE> sources, Arguments arguments) {
			if(CollectionHelper.isEmpty(sources))
				return;
			/*Collection<String> fieldsNamesNullable = new HashSet<>();
			if(CollectionHelper.isNotEmpty(arguments.getFieldsNamesNullable()))
				fieldsNamesNullable.addAll(arguments.getFieldsNamesNullable());
			*/
			/*if(CollectionHelper.isNotEmpty(arguments.fieldsNames)) {
				FieldHelper.getNullables(sources, parentFieldName, fieldsNames)
			}
			*/
			//fieldsNamesNullable.addAll(__sourceFieldsNameSettable__.stream().filter(fieldName -> !arguments.fieldsNames.contains(fieldName)).collect(Collectors.toList()));				
			/*
			sources.parallelStream().forEach(source -> {
				Collection<String> nullables = FieldHelper.getNullables(source, null, arguments.fieldsNames);
				FieldHelper.nullifyByFieldsNames(source, fieldsNamesNullable);
			});*/				
		}
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {
		private String mapIdentifier;
		private Collection<String> fieldsNames;
		private Collection<String> fieldsNamesNullable;
		
		@XmlRootElement @Getter @Setter @Accessors(chain=true)
		public static class Dto extends AbstractObject implements Serializable {
			private String mapIdentifier;
			private ArrayList<String> fieldsNames;
			private ArrayList<String> fieldsNamesNullable;
			
			@Override
			public String toString() {
				Collection<String> strings = new ArrayList<>();
				if(StringHelper.isNotBlank(mapIdentifier))
					strings.add("ID="+mapIdentifier);
				if(CollectionHelper.isNotEmpty(fieldsNames))
					strings.add("FN="+fieldsNames);
				if(CollectionHelper.isNotEmpty(fieldsNamesNullable))
					strings.add("FNN="+fieldsNamesNullable);
				return StringHelper.concatenate(strings, " ");
			}
			
			@org.mapstruct.Mapper
			public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<MapperSourceDestination.Arguments.Dto, MapperSourceDestination.Arguments>{
				
			}
		}
	}
}