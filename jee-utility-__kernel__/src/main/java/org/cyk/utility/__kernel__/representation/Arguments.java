package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArgumentsDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString(callSuper = false,doNotUseGetters = true)
public class Arguments extends AbstractObject implements Serializable {
	
	private String actionIdentifier;
	private String representationEntityClassName;
	private String persistenceEntityClassName;
	private QueryExecutorArgumentsDto queryExecutorArguments;
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
}