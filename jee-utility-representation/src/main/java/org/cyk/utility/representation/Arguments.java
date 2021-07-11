package org.cyk.utility.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Arguments extends AbstractObject implements Serializable {
	
	private String actionIdentifier;
	private String representationEntityClassName;
	private String persistenceEntityClassName;
	private QueryExecutorArguments.Dto queryExecutorArguments;
	private Boolean countable;
	private Boolean loggableAsInfo;
	private MapperSourceDestination.Arguments.Dto mappingArguments;
	private Listener listener;
	private ResponseBuilder.Arguments responseBuilderArguments;
	
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
	
	public ResponseBuilder.Arguments getResponseBuilderArguments(Boolean injectIfNull) {
		if(responseBuilderArguments == null && Boolean.TRUE.equals(injectIfNull))
			responseBuilderArguments = new ResponseBuilder.Arguments();
		return responseBuilderArguments;
	}
	
	public QueryExecutorArguments.Dto getQueryExecutorArguments(Boolean injectIfNull) {
		if(queryExecutorArguments == null && Boolean.TRUE.equals(injectIfNull))
			queryExecutorArguments = new QueryExecutorArguments.Dto();
		return queryExecutorArguments;
	}
	
	@Override
	public String toString() {
		Collection<String> strings = new ArrayList<>();
		if(StringHelper.isNotBlank(actionIdentifier))
			strings.add("AID="+actionIdentifier);
		if(StringHelper.isNotBlank(representationEntityClassName))
			strings.add("REC="+representationEntityClassName);
		if(StringHelper.isNotBlank(persistenceEntityClassName))
			strings.add("CEC="+persistenceEntityClassName);
		if(queryExecutorArguments != null)
			strings.add("Q.EXE("+queryExecutorArguments+")");
		if(countable != null)
			strings.add("Countable="+countable);
		if(mappingArguments != null)
			strings.add("Mapping("+mappingArguments+")");
		return StringHelper.concatenate(strings, " ");
	}
	
	/**/
	
	public static class Internal implements Serializable {	
		public Class<?> representationEntityClass,persistenceEntityClass;
		
		public Internal(Arguments arguments,Class<?> actionClass) {
			if(StringHelper.isBlank(arguments.getRepresentationEntityClassName()))
				throw new RuntimeException("representation entity class name is required");
			representationEntityClass = ClassHelper.getByName(arguments.getRepresentationEntityClassName());
			if(representationEntityClass == null)
				throw new RuntimeException("representation entity class named "+arguments.getRepresentationEntityClassName()+" not found");			
			String persistenceEntityClassName = arguments.getPersistenceEntityClassName();
			if(StringHelper.isNotBlank(persistenceEntityClassName))
				persistenceEntityClass = ClassHelper.getByName(persistenceEntityClassName);
			if(persistenceEntityClass == null)
				persistenceEntityClass = PersistenceEntityClassGetter.getInstance().get(representationEntityClass);
			if(persistenceEntityClass == null)
				throw new RuntimeException("persistence entity class of representation entity class "+representationEntityClass+" not found");
		}
		
		public Internal(Arguments arguments) {
			this(arguments,null);
		}
	}
	
	/**/
	
	public static interface Listener {
		void processPersistenceEntities(Collection<?> persistenceEntities);
		void processRepresentationEntities(Collection<?> representationEntities);
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			@Override public void processPersistenceEntities(Collection<?> persistenceEntities) {}
			@Override public void processRepresentationEntities(Collection<?> representationEntities) {
				if(CollectionHelper.isEmpty(representationEntities))
					return;
				representationEntities.forEach(entity -> {
					processRepresentationEntity(entity);
				});
			}
			
			protected void processRepresentationEntity(Object representationEntity) {}
		}
	}
}