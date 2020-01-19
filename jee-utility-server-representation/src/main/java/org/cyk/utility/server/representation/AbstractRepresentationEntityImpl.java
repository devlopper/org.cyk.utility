package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.clazz.ClassNameBuilder;
import org.cyk.utility.map.MapInstanceIntegerToString;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;

public abstract class AbstractRepresentationEntityImpl<ENTITY> extends AbstractRepresentationServiceProviderImpl implements RepresentationEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<ENTITY> __entityClass__;
	private Class<?> __persistenceEntityClass__;
	
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
			__persistenceEntityClass__ = ValueHelper.returnOrThrowIfBlank("persistence entity class", ClassHelper.getByName(classNameBuilder.execute().getOutput()));
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
	public Response getMany(String queryIdentifier,Boolean isPageable,Long from,Long count,String fields,FilterDto filter) {
		RepresentationFunctionReader function = __inject__(RepresentationFunctionReader.class);
		function.setIsCollectionable(Boolean.TRUE);
		function.setEntityClass(__entityClass__).setPersistenceEntityClass(__persistenceEntityClass__)
				.setEntityFieldNames(__getFieldNames__(fields))
				.setProperty(Properties.QUERY_IDENTIFIER, queryIdentifier)
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
	public Response count(String queryIdentifier,FilterDto filter) {
		RepresentationFunctionCounter function = __inject__(RepresentationFunctionCounter.class);
		function.setPersistenceEntityClass(__persistenceEntityClass__);
		function.setProperty(Properties.QUERY_IDENTIFIER,queryIdentifier);
		function.setProperty(Properties.QUERY_FILTERS,filter);
		return function.execute().getResponse();
	}

	@Override
	public Response import_(List<String> uniformResourceIdentifiers, Boolean ignoreKnownUniformResourceIdentifiers) {
		return Response.serverError().entity("Not yet implemented").build();
	}
	
	/**/
	
	protected static String[] __getFieldNames__(String string) {
		return StringUtils.isBlank(string) ? null : StringUtils.split(string,ConstantCharacter.COMA.toString());
	}
	
	@SuppressWarnings("unchecked") @Deprecated
	protected Map<String,Object> __getFiltersMap__(String filters) {
		Map<String,Object> __filters__ = null;
		if(StringHelper.isNotBlank(filters)) {
			/*
			 * Convert filters from json to map
			 */
			__filters__ = (Map<String, Object>) JsonbBuilder.create().fromJson(filters, Map.class);
		}
		return __filters__;
	}
	
	@SuppressWarnings("unchecked") @Deprecated
	protected Map<String,Object> __buildMapFromString__(String string) {
		Map<String,Object> map = null;
		if(StringHelper.isNotBlank(string)) {
			/*
			 * Convert string from json to map
			 */
			map = (Map<String, Object>) JsonbBuilder.create().fromJson(string, Map.class);
		}
		return map;
	}
}
