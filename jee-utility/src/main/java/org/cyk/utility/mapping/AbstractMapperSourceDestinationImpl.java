package org.cyk.utility.mapping;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.clazz.ClassInstance;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.field.FieldInstancesRuntime;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.instance.InstanceHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;

public abstract class AbstractMapperSourceDestinationImpl<SOURCE,DESTINATION> extends AbstractMapperImpl implements MapperSourceDestination<SOURCE,DESTINATION>,Serializable {
	private static final long serialVersionUID = 1L;
	/*
	protected Class<SOURCE> sourceClass;
	protected Class<DESTINATION> destinationClass;
	
	protected Class<SOURCE> __getSourceClass__() {
		return sourceClass == null ? sourceClass = (Class<SOURCE>) DependencyInjection.inject(ClassHelper.class).getParameterAt(getClass(), 0, Object.class) : sourceClass;
	}
	
	protected Class<DESTINATION> __getDestinationClass__() {
		return destinationClass == null ? destinationClass = (Class<DESTINATION>) DependencyInjection.inject(ClassHelper.class).getParameterAt(getClass(), 1, Object.class) : destinationClass;
	}
	*/
	/*
	@ObjectFactory
	public SOURCE instantiateSource(DESTINATION destination) {
		return DependencyInjection.inject(ClassHelper.class).instanciateOne(__getSourceClass__());
	}
	*/
	/*
	protected SOURCE __instantiateSource__(DESTINATION destination) {
		return DependencyInjection.inject(ClassHelper.class).instanciateOne(sourceClass);
	}
	*/
	/*
	@ObjectFactory
	public DESTINATION instantiateDestination(SOURCE source) {
		return DependencyInjection.inject(ClassHelper.class).instanciateOne(__getDestinationClass__());
	}
	*/
	/*
	protected DESTINATION __instantiateDestination__(SOURCE source) {
		return DependencyInjection.inject(ClassHelper.class).instanciateOne(destinationClass);
	}
	*/
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
		//Find all persistable fields
		Collection<Field> fields = null;
		ClassInstance classInstance = DependencyInjection.inject(ClassInstancesRuntime.class).get(destination.getClass());
		if(classInstance.getFields() != null) {
			for(Field index : classInstance.getFields().get()) {
				FieldInstance fieldInstance = DependencyInjection.inject(FieldInstancesRuntime.class).get(destination.getClass(), index.getName());
				if(Boolean.TRUE.equals(DependencyInjection.inject(ClassInstancesRuntime.class).get(fieldInstance.getType()).getIsPersistable()) && 
						fieldInstance.getField().isAnnotationPresent(javax.persistence.ManyToOne.class)) {
					if(fields == null)
						fields = new ArrayList<>();
					fields.add(index);
				}
			}
		}
		
		//Set all persistable fields with their corresponding persisted value
		if(Boolean.TRUE.equals(DependencyInjection.inject(CollectionHelper.class).isNotEmpty(fields))) {
			for(Field index : fields) {
				Object value = DependencyInjection.inject(FieldValueGetter.class).execute(destination, index).getOutput();
				if(value != null) {
					Object persisted = DependencyInjection.inject(InstanceHelper.class).getBySystemIdentifierOrBusinessIdentifier(value);
					if(persisted != null)
						DependencyInjection.inject(FieldValueSetter.class).execute(destination, index, persisted);
				}
			}
		}
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
	
}
