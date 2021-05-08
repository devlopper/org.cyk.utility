package org.cyk.utility.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface EntityReader {

	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,Arguments<ENTITY> arguments);
	
	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass);
	
	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,String queryIdentifier,Object...filterFieldsValues);
	
	<ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,String queryIdentifier,String[] processableTransientFieldsNames,Object...filterFieldsValues);
	
	<ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass,Arguments<ENTITY> arguments);
	
	<ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass,String queryIdentifier,Object...filterFieldsValues);
	
	<ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass,String queryIdentifier,String[] processableTransientFieldsNames,Object...filterFieldsValues);
	
	<ENTITY> ENTITY readOneBySystemIdentifierAsParent(Class<ENTITY> klass,Arguments<ENTITY> arguments);
	
	<ENTITY> ENTITY readOneBySystemIdentifier(Class<ENTITY> klass,String queryIdentifier,Object identifier,String...processableTransientFieldsNames);
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractObject implements EntityReader,Serializable {
		
		@Override
		public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass) {
			Arguments<ENTITY> arguments = new Arguments<ENTITY>();
			arguments.setControllerEntityClass(controllerEntityClass);
			return readMany(controllerEntityClass, arguments);
		}
		
		@Override
		public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,String queryIdentifier,Object...filterFieldsValues) {
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			return readMany(controllerEntityClass, new Arguments<ENTITY>().setControllerEntityClass(controllerEntityClass)
					.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
					.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues))));
		}
		
		@Override
		public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> controllerEntityClass,String queryIdentifier,String[] processableTransientFieldsNames
				,Object...filterFieldsValues) {
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			return readMany(controllerEntityClass, new Arguments<ENTITY>().setControllerEntityClass(controllerEntityClass)
					.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
					.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues)
					.addProcessableTransientFieldsNames(processableTransientFieldsNames)
					)));
		}
		
		@Override
		public <ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass,String queryIdentifier,Object...filterFieldsValues) {
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			return readOne(controllerEntityClass, new Arguments<ENTITY>().setControllerEntityClass(controllerEntityClass)
					.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
					.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues))));
		}
		
		@Override
		public <ENTITY> ENTITY readOne(Class<ENTITY> controllerEntityClass,String queryIdentifier,String[] processableTransientFieldsNames,Object...filterFieldsValues) {
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			return readOne(controllerEntityClass, new Arguments<ENTITY>().setControllerEntityClass(controllerEntityClass)
					.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
					.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues)
							.addProcessableTransientFieldsNames(processableTransientFieldsNames))));
		}
		
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
		
		@Override
		public <ENTITY> ENTITY readOneBySystemIdentifierAsParent(Class<ENTITY> klass, Arguments<ENTITY> arguments) {
			if(klass == null)
				throw new RuntimeException("controller entity class is required");
			if(arguments == null)
				throw new RuntimeException("arguments is required");			
			if(arguments.getRepresentationArguments() == null || arguments.getRepresentationArguments().getQueryExecutorArguments() == null
					|| arguments.getRepresentationArguments().getQueryExecutorArguments().getFilter() == null
					|| CollectionHelper.isEmpty(arguments.getRepresentationArguments().getQueryExecutorArguments().getFilter().getFields()))
				return null;
			if(arguments.getRepresentationArguments().getQueryExecutorArguments().getFilter().getFields().stream()
						.filter(field -> Querier.PARAMETER_NAME_IDENTIFIER.equals(field.getName())).collect(Collectors.toList()).isEmpty())
				return null;			
			return readOne(klass, arguments);
		}
		
		@Override
		public <ENTITY> ENTITY readOneBySystemIdentifier(Class<ENTITY> klass, String queryIdentifier,Object identifier,String... processableTransientFieldsNames) {
			if(klass == null)
				throw new RuntimeException("controller entity class is required");
			if(Boolean.TRUE.equals(ValueHelper.isBlank(identifier)))
				return null;
			return readOne(klass, new Arguments<ENTITY>().setControllerEntityClass(klass)
					.setRepresentationArguments(new org.cyk.utility.representation.Arguments()
					.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier)
							.addFilterFieldsValues(Querier.PARAMETER_NAME_IDENTIFIER,identifier)
							.addProcessableTransientFieldsNames(processableTransientFieldsNames))));
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