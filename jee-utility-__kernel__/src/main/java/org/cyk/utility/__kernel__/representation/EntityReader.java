package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.TypeHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArgumentsDto;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Path(EntityReader.PATH)
public interface EntityReader {

	@GET
	@Path(PATH_READ)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	Response read(Arguments arguments);
	
	/**/
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString(callSuper = false,doNotUseGetters = true)
	public static class Arguments extends AbstractObject implements Serializable {
		private String representationEntityClassName;
		private String persistenceEntityClassName;
		private QueryExecutorArgumentsDto queryExecutorArguments;
		
		public Arguments setRepresentationEntityClass(Class<?> entityClass) {
			if(entityClass == null)
				return this;
			setRepresentationEntityClassName(entityClass.getName());
			return this;
		}
		
		public Arguments setPersistenceEntityClass(Class<?> entityClass) {
			if(entityClass == null)
				return this;
			setPersistenceEntityClassName(entityClass.getName());
			return this;
		}
	}
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractObject implements EntityReader,Serializable {
		
		@Override
		public Response read(Arguments arguments) {
			if(arguments == null)
				return __buildResponse__(Status.BAD_REQUEST,"arguments are required");
			if(StringHelper.isBlank(arguments.representationEntityClassName))
				return __buildResponse__(Status.BAD_REQUEST,"representation entity class name is required");
			Class<?> representationEntityClass = ClassHelper.getByName(arguments.representationEntityClassName);
			if(representationEntityClass == null)
				return __buildResponse__(Status.BAD_REQUEST,"representation entity class named "+arguments.representationEntityClassName+" not found");			
			String persistenceEntityClassName = arguments.getPersistenceEntityClassName();
			if(StringHelper.isBlank(persistenceEntityClassName))
				persistenceEntityClassName = ClassHelper.buildName(representationEntityClass.getPackageName(), representationEntityClass.getSimpleName()
					, new NamingModel().server().representation().entities().suffix(), new NamingModel().server().persistence().entities());						
			Class<?> persistenceEntityClass = ClassHelper.getByName(persistenceEntityClassName);
			if(persistenceEntityClass == null)
				return __buildResponse__(Status.BAD_REQUEST,"persistence entity class of representation entity class "+representationEntityClass+" not found");
			QueryExecutorArguments queryExecutorArguments = null;
			if(arguments.queryExecutorArguments != null)
				queryExecutorArguments = MappingHelper.getDestination(arguments.queryExecutorArguments, QueryExecutorArguments.class);
			Collection<?> persistences = org.cyk.utility.__kernel__.persistence.query.EntityReader.getInstance().read(persistenceEntityClass,queryExecutorArguments);
			if(CollectionHelper.isEmpty(persistences))
				return Response.ok().build();
			Collection<?> representations = MappingHelper.getSources(persistences, representationEntityClass);
			return Response.ok(new GenericEntity<List<?>>((List<?>) representations,(Type) TypeHelper.instantiateCollectionParameterizedType(List.class, representationEntityClass))).build();
		}
		
		protected Response __buildResponse__(Status status,Object entity) {
			return Response.status(status).entity(entity).build();
		}
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	String PATH = "/cyk/entity/reader";
	String PATH_READ = "read";
}