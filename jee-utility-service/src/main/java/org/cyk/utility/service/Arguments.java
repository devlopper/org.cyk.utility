package org.cyk.utility.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.rest.ResponseBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Arguments extends AbstractObject implements Serializable {
	
	private String actionIdentifier;
	private Class<?> representationEntityClass;
	private Class<?> persistenceEntityClass;
	private QueryExecutorArguments.Dto queryExecutorArguments;
	private Boolean countable;
	private Boolean loggableAsInfo;
	private MapperSourceDestination.Arguments.Dto mappingArguments;
	@SuppressWarnings("rawtypes")
	private Listener listener;
	private ResponseBuilder.Arguments responseBuilderArguments;
	
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
		if(representationEntityClass!=null)
			strings.add("REC="+representationEntityClass.getSimpleName());
		if(persistenceEntityClass!=null)
			strings.add("CEC="+persistenceEntityClass.getSimpleName());
		if(queryExecutorArguments != null)
			strings.add("Q.EXE("+queryExecutorArguments+")");
		if(countable != null)
			strings.add("Countable="+countable);
		if(mappingArguments != null)
			strings.add("Mapping("+mappingArguments+")");
		return StringHelper.concatenate(strings, " ");
	}
	
	/**/
	
	public static interface Listener<REPRESENTATION,PERSISTENCE> {
		void processPersistenceEntities(Collection<PERSISTENCE> persistenceEntities);
		void processRepresentationEntities(Collection<REPRESENTATION> representationEntities);
		public static abstract class AbstractImpl<REPRESENTATION,PERSISTENCE> extends AbstractObject implements Listener<REPRESENTATION,PERSISTENCE>,Serializable {
			@Override public void processPersistenceEntities(Collection<PERSISTENCE> persistenceEntities) {}
			@Override public void processRepresentationEntities(Collection<REPRESENTATION> representationEntities) {
				if(CollectionHelper.isEmpty(representationEntities))
					return;
				representationEntities.forEach(entity -> {
					processRepresentationEntity(entity);
				});
			}
			
			protected void processRepresentationEntity(REPRESENTATION representationEntity) {}
		}	
	}
	
	/**/
}