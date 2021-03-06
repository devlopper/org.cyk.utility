package org.cyk.utility.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityReader {

	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,Arguments<ENTITY> arguments);
	
	default <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass) {
		Arguments<ENTITY> arguments = new Arguments<ENTITY>();
		arguments.setControllerEntityClass(controllerEntityClass);
		return readMany(controllerEntityClass, arguments);
	}
	
	default <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,String queryIdentifier,Object...filterFieldsValues) {
		if(controllerEntityClass == null)
			throw new RuntimeException("controller entity class is required");
		return readMany(controllerEntityClass, new Arguments<ENTITY>().setControllerEntityClass(controllerEntityClass)
				.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
				.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues))));
	}
	
	default <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,String queryIdentifier,String[] processableTransientFieldsNames
			,Object...filterFieldsValues) {
		if(controllerEntityClass == null)
			throw new RuntimeException("controller entity class is required");
		return readMany(controllerEntityClass, new Arguments<ENTITY>().setControllerEntityClass(controllerEntityClass)
				.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
				.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues)
				.addProcessableTransientFieldsNames(processableTransientFieldsNames)
				)));
	}
	
	<ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass,Arguments<ENTITY> arguments);
	
	default <ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass,String queryIdentifier,Object...filterFieldsValues) {
		if(controllerEntityClass == null)
			throw new RuntimeException("controller entity class is required");
		return readOne(controllerEntityClass, new Arguments<ENTITY>().setControllerEntityClass(controllerEntityClass)
				.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
				.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues))));
	}
	
	default <ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass,String queryIdentifier,String[] processableTransientFieldsNames,Object...filterFieldsValues) {
		if(controllerEntityClass == null)
			throw new RuntimeException("controller entity class is required");
		return readOne(controllerEntityClass, new Arguments<ENTITY>().setControllerEntityClass(controllerEntityClass)
				.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
				.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues)
						.addProcessableTransientFieldsNames(processableTransientFieldsNames))));
	}
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractObject implements EntityReader,Serializable {
		
		@Override
		public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,Arguments<ENTITY> arguments) {
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			if(arguments.getResponseEntityClass() == null)
				arguments.setResponseEntityClass(Collection.class);
			arguments.prepare(controllerEntityClass, org.cyk.utility.representation.EntityReader.class);
			Response response = ((org.cyk.utility.representation.EntityReader)arguments.__representation__).read(arguments.__representationArguments__);
			arguments.finalise(response);
			return (Collection<ENTITY>) arguments.__responseEntity__;			
		}
		
		@Override
		public <ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass, Arguments<ENTITY> arguments) {
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			if(arguments.getResponseEntityClass() == null)
				arguments.setResponseEntityClass(controllerEntityClass);
			arguments.prepare(controllerEntityClass, org.cyk.utility.representation.EntityReader.class);
			arguments.__representationArguments__.getQueryExecutorArguments(Boolean.TRUE).setCollectionable(Boolean.FALSE);
			Response response = __readOne__(controllerEntityClass, arguments);
			arguments.finalise(response);
			return (ENTITY) arguments.__responseEntity__;	
		}
		
		protected <ENTITY> Response __readOne__(Class<ENTITY> controllerEntityClass, Arguments<ENTITY> arguments) {
			return ((org.cyk.utility.representation.EntityReader)arguments.__representation__).read(arguments.__representationArguments__);
		}
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}