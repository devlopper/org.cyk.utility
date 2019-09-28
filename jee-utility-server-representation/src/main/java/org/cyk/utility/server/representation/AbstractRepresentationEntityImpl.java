package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.clazz.ClassNameBuilder;
import org.cyk.utility.map.MapInstanceIntegerToString;
import org.cyk.utility.object.ObjectFromStringBuilder;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.persistence.query.filter.FilterDto;

public abstract class AbstractRepresentationEntityImpl<PERSISTENCE_ENTITY,BUSINESS extends BusinessEntity<PERSISTENCE_ENTITY>,ENTITY,ENTITY_COLLECTION> extends AbstractRepresentationServiceProviderImpl implements RepresentationEntity<PERSISTENCE_ENTITY,ENTITY,ENTITY_COLLECTION>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<ENTITY> __entityClass__;
	private Class<PERSISTENCE_ENTITY> __persistenceEntityClass__;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		if(__entityClass__ == null) {
			ClassNameBuilder classNameBuilder = __inject__(ClassNameBuilder.class).setKlass(getClass());
			classNameBuilder.getSourceNamingModel(Boolean.TRUE).server().representation().impl().suffix();
			classNameBuilder.getDestinationNamingModel(Boolean.TRUE).server().representation().entities().suffix();
			__entityClass__ = ValueHelper.returnOrThrowIfBlank("entity class",(Class<ENTITY>) ClassHelper.getByName(classNameBuilder.execute().getOutput()));
		}
		
		if(__persistenceEntityClass__ == null) {
			ClassNameBuilder classNameBuilder = __inject__(ClassNameBuilder.class).setKlass(getClass());
			classNameBuilder.getSourceNamingModel(Boolean.TRUE).server().representation().impl().suffix();
			classNameBuilder.getDestinationNamingModel(Boolean.TRUE).server().persistence().entities();
			__persistenceEntityClass__ = ValueHelper.returnOrThrowIfBlank("persistence entity class",(Class<PERSISTENCE_ENTITY>) ClassHelper.getByName(classNameBuilder.execute().getOutput()));
		}
		
	}
	
	/* */
	
	@Override
	public Response createOne(ENTITY entity) {
		return __inject__(RepresentationFunctionCreator.class).setEntity(entity).setPersistenceEntityClass(__persistenceEntityClass__).execute().getResponse();
	}
	
	@Override
	public Response createMany(Collection<ENTITY> entities,String properties) {
		Map<String,Object> map = __buildMapFromString__(properties);
		RepresentationFunctionCreator function = __inject__(RepresentationFunctionCreator.class);
		function.setEntities(entities).setPersistenceEntityClass(__persistenceEntityClass__);
		if(map != null) {
			function.getProperties().setIsBatchable(map.get(Properties.IS_BATCHABLE));
			function.getProperties().setBatchSize(map.get(Properties.BATCH_SIZE));	
		}
		return function.execute().getResponse();
	}
	
	@Override
	public Response createMany(ENTITY_COLLECTION entityCollection,String properties) {
		return createMany(__getEntities__(entityCollection),properties);
	}
	
	@Override
	public Response getMany(Boolean isPageable,Long from,Long count,String fields,FilterDto filter) {
		RepresentationFunctionReader function = __inject__(RepresentationFunctionReader.class);
		function.setIsCollectionable(Boolean.TRUE);
		function.setEntityClass(__entityClass__).setPersistenceEntityClass(__persistenceEntityClass__)
				.setEntityFieldNames(__getFieldNames__(fields))
				.setProperty(Properties.IS_QUERY_RESULT_PAGINATED, isPageable == null ? Boolean.TRUE : isPageable)
				.setProperty(Properties.QUERY_FIRST_TUPLE_INDEX, from)
				.setProperty(Properties.QUERY_NUMBER_OF_TUPLE, count)
				.setProperty(Properties.QUERY_FILTERS, filter);
		return function.execute().getResponse();
	}
	
	@Override
	public Response getOne(String identifier,String type,String fields) {
		return __inject__(RepresentationFunctionReader.class).setIsCollectionable(Boolean.FALSE).setEntityClass(__entityClass__).setEntityIdentifier(identifier)
				.setEntityIdentifierValueUsageType(type).setPersistenceEntityClass(__persistenceEntityClass__).setEntityFieldNames(__getFieldNames__(fields))
				.execute().getResponse();
	}
	
	@Override
	public Response updateOne(ENTITY entity,String fields) {
		return __inject__(RepresentationFunctionModifier.class).setPersistenceEntityClass(__persistenceEntityClass__).setEntity(entity)
				.setEntityFieldNames(__getFieldNames__(fields)).execute().getResponse();
	}
	
	@Override
	public Response updateMany(Collection<ENTITY> entities,String fields) {
		return __inject__(RepresentationFunctionModifier.class).setEntities(entities).setEntityFieldNames(__getFieldNames__(fields)).execute().getResponse();
	}
	
	/*
	@Override
	public Response deleteOne(String identifier,String type) {
		System.out.println("AbstractRepresentationEntityImpl.deleteOne() DELETEONE "+identifier+" : "+type);
		return __inject__(RepresentationFunctionRemover.class).setEntityIdentifier(identifier).setEntityIdentifierValueUsageType(type)
			.setPersistenceEntityClass(persistenceEntityClass).execute().getResponse();
	}
	*/
	@Override
	public Response deleteOne(ENTITY entity) {
		return __inject__(RepresentationFunctionRemover.class).setEntity(entity).setPersistenceEntityClass(__persistenceEntityClass__).execute().getResponse();
	}
	
	@Override
	public Response deleteMany() {
		return __inject__(RepresentationFunctionRemover.class).setPersistenceEntityClass(__persistenceEntityClass__).execute().getResponse();
	}
	
	@Override
	public Response deleteByIdentifiers(List<String> identifiers, String type) {
		ValueUsageType valueUsageType = ValueUsageType.BUSINESS.name().equalsIgnoreCase(type) ? ValueUsageType.BUSINESS : ValueUsageType.SYSTEM;
		RepresentationFunctionRemover function = __inject__(RepresentationFunctionRemover.class);
		function.setPersistenceEntityClass(__persistenceEntityClass__).setEntityIdentifierValueUsageType(valueUsageType);
		function.addActionEntitiesIdentifiers(CollectionHelper.cast(Object.class, identifiers));
		return function.execute().getResponse();
	}

	@Override
	public Response deleteAll() {
		return __inject__(RepresentationFunctionRemover.class).setPersistenceEntityClass(__persistenceEntityClass__).execute().getResponse();
	}
	
	@Override
	public Response saveFromFileExcelSheet(String workbookName, String sheetName,List<String> columnIndexFieldNames) {
		if(CollectionHelper.isNotEmpty(columnIndexFieldNames)) {
			//Properties properties = new Properties();
			MapInstanceIntegerToString columnIndexFieldNameMap = __inject__(MapInstanceIntegerToString.class);
			for(String index : columnIndexFieldNames) {
				String[] strings = StringUtils.split(index, ",");
				columnIndexFieldNameMap.set(strings[0],strings[1]);
			}
			//getBusiness().saveFromFileExcelSheet(workbookName, sheetName, columnIndexFieldNameMap, properties);
		}
		return Response.ok().build();
	}

	@Override
	public Response count(FilterDto filter) {
		RepresentationFunctionCounter function = __inject__(RepresentationFunctionCounter.class);
		function.setPersistenceEntityClass(__persistenceEntityClass__);
		function.setProperty(Properties.QUERY_FILTERS,filter);
		return function.execute().getResponse();
	}

	/**/
	
	@SuppressWarnings("unchecked")
	protected Collection<ENTITY> __getEntities__(ENTITY_COLLECTION collection) {
		if(collection == null)
			return null;
		if(collection instanceof org.cyk.utility.__kernel__.object.__static__.representation.Collection)
			return ((org.cyk.utility.__kernel__.object.__static__.representation.Collection<ENTITY>)collection).getElements();
		if(collection instanceof AbstractEntityCollection<?>)
			return (Collection<ENTITY>) ((AbstractEntityCollection<?>)collection).getCollection();
		throw new RuntimeException("we cannot get entities from collection of type "+collection.getClass());
	}
	
	/**/
	
	protected static String[] __getFieldNames__(String string) {
		return StringUtils.isBlank(string) ? null : StringUtils.split(string,ConstantCharacter.COMA.toString());
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String,Object> __getFiltersMap__(String filters) {
		Map<String,Object> __filters__ = null;
		if(StringHelper.isNotBlank(filters)) {
			/*
			 * Convert filters from json to map
			 */
			__filters__ = (Map<String, Object>) __injectByQualifiersClasses__(ObjectFromStringBuilder.class,JavaScriptObjectNotation.Class.class)
				.setString(filters).setKlass(Map.class).execute().getOutput();
		}
		return __filters__;
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String,Object> __buildMapFromString__(String string) {
		Map<String,Object> map = null;
		if(StringHelper.isNotBlank(string)) {
			/*
			 * Convert string from json to map
			 */
			map = (Map<String, Object>) __injectByQualifiersClasses__(ObjectFromStringBuilder.class,JavaScriptObjectNotation.Class.class)
				.setString(string).setKlass(Map.class).execute().getOutput();
		}
		return map;
	}
}
