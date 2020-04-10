package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString(callSuper = false)
public class Arguments extends AbstractObject implements Serializable {
	
	private String actionIdentifier;
	private String representationEntityClassName;
	private String persistenceEntityClassName;
	private QueryExecutorArguments.Dto queryExecutorArguments;
	private Boolean countable;
	private MapperSourceDestination.Arguments.Dto mappingArguments;
	
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
	
	public QueryExecutorArguments.Dto getQueryExecutorArguments(Boolean injectIfNull) {
		if(queryExecutorArguments == null && Boolean.TRUE.equals(injectIfNull))
			queryExecutorArguments = new QueryExecutorArguments.Dto();
		return queryExecutorArguments;
	}
	
	/**/
	
	public static class Internal implements Serializable {	
		Class<?> representationEntityClass,persistenceEntityClass;
		
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
}