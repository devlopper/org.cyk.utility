package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public interface EntityReader {

	<ENTITY> Collection<ENTITY> read(Class<ENTITY> dataClass,Arguments arguments);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString(callSuper = false,doNotUseGetters = true)
	public static class Arguments extends AbstractObject implements Serializable {
		private Class<?> controllerEntityClass;
		private Class<?> representationEntityClass;
		private EntityReader.Arguments resourceReaderArguments;
	}
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractObject implements EntityReader,Serializable {
		
		@Override
		public <ENTITY> Collection<ENTITY> read(Class<ENTITY> dataClass,Arguments arguments) {
			/*
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			Class<?> controllerEntityClass = arguments.controllerEntityClass;
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			Class<?> representationEntityClass = arguments.representationEntityClass;
			if(representationEntityClass == null)
				throw new RuntimeException("representation entity class is required");
			if(arguments.resourceReaderArguments == null)
				throw new RuntimeException("resource reader arguments is required");
			ResourceReader resourceReader = ProxyGetter.getInstance().get(ResourceReader.class);
			if(resourceReader == null)
				throw new RuntimeException("resource reader cannot be acquired");
			Response response = resourceReader.read(arguments.resourceReaderArguments);
			Collection<?> dtos = (Collection<?>) response.readEntity(TypeHelper.instantiateGenericCollectionParameterizedTypeForJaxrs(Collection.class,representationEntityClass));
			if(CollectionHelper.isEmpty(dtos))
				return null;
			return (Collection<ENTITY>) MappingHelper.getSources(dtos, controllerEntityClass);
			*/
			return null;
		}
		
		/**/		
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}