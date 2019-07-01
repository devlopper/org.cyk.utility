package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.map.MapInstanceIntegerToString;
import org.cyk.utility.object.ObjectFromStringBuilder;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.business.BusinessLayer;

import lombok.Getter;

public abstract class AbstractRepresentationEntityImpl<PERSISTENCE_ENTITY,BUSINESS extends BusinessEntity<PERSISTENCE_ENTITY>,ENTITY extends AbstractEntityFromPersistenceEntity,ENTITY_COLLECTION> extends AbstractRepresentationServiceProviderImpl<PERSISTENCE_ENTITY,ENTITY> implements RepresentationEntity<PERSISTENCE_ENTITY,ENTITY,ENTITY_COLLECTION>,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter private Class<ENTITY> entityClass;
	@Getter private Class<PERSISTENCE_ENTITY> persistenceEntityClass;
	@Getter private BUSINESS business;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		persistenceEntityClass = (Class<PERSISTENCE_ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
		business = (BUSINESS) __inject__(BusinessLayer.class).injectInterfaceClassFromPersistenceEntityClass(getPersistenceEntityClass());
		entityClass =  (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 2, AbstractEntityFromPersistenceEntity.class);
	}
	
	/* */
	
	@Override
	public Response createOne(ENTITY entity) {
		return __inject__(RepresentationFunctionCreator.class).setEntity(entity).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	@Override
	public Response createMany(Collection<ENTITY> entities) {
		return __inject__(RepresentationFunctionCreator.class).setEntities(entities).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	@Override
	public Response createMany(ENTITY_COLLECTION entityCollection) {
		return createMany(__getEntities__(entityCollection));
	}
	
	@Override
	public Response getMany(Boolean isPageable,Long from,Long count,String fields,String filters) {
		RepresentationFunctionReader function = __inject__(RepresentationFunctionReader.class);
		function.setEntityClass(getEntityClass()).setPersistenceEntityClass(getPersistenceEntityClass())
				.setEntityFieldNames(__getFieldNames__(fields))
				.setProperty(Properties.IS_QUERY_RESULT_PAGINATED, isPageable == null ? Boolean.TRUE : isPageable)
				.setProperty(Properties.QUERY_FIRST_TUPLE_INDEX, from)
				.setProperty(Properties.QUERY_NUMBER_OF_TUPLE, count);
		Map<String,Object> __filters__ = __getFiltersMap__(filters);
		if(__filters__ != null)
			function.setProperty(Properties.QUERY_FILTERS, __filters__);
		return function.execute().getResponse();
	}
	
	@Override
	public Response getOne(String identifier,String type,String fields) {
		return __inject__(RepresentationFunctionReader.class).setEntityClass(getEntityClass()).setEntityIdentifier(identifier).setEntityIdentifierValueUsageType(type)
				.setPersistenceEntityClass(getPersistenceEntityClass()).setEntityFieldNames(__getFieldNames__(fields)).execute().getResponse();
	}
	
	@Override
	public Response updateOne(ENTITY entity,String fields) {
		return __inject__(RepresentationFunctionModifier.class).setPersistenceEntityClass(getPersistenceEntityClass()).setEntity(entity)
				.setEntityFieldNames(__getFieldNames__(fields)).execute().getResponse();
	}
	
	@Override
	public Response updateMany(Collection<ENTITY> entities,String fields) {
		return __inject__(RepresentationFunctionModifier.class).setEntities(entities).execute().getResponse();
	}
	
	/*
	@Override
	public Response deleteOne(String identifier,String type) {
		System.out.println("AbstractRepresentationEntityImpl.deleteOne() DELETEONE "+identifier+" : "+type);
		return __inject__(RepresentationFunctionRemover.class).setEntityIdentifier(identifier).setEntityIdentifierValueUsageType(type)
			.setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	*/
	@Override
	public Response deleteOne(ENTITY entity) {
		return __inject__(RepresentationFunctionRemover.class).setEntity(entity)
			.setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	@Override
	public Response deleteMany() {
		return __inject__(RepresentationFunctionRemover.class).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}

	@Override
	public Response deleteAll() {
		return __inject__(RepresentationFunctionRemover.class).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	@Override
	public Response saveFromFileExcelSheet(String workbookName, String sheetName,List<String> columnIndexFieldNames) {
		if(__injectCollectionHelper__().isNotEmpty(columnIndexFieldNames)) {
			Properties properties = new Properties();
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
	public Response count(String filters) {
		RepresentationFunctionCounter function = __inject__(RepresentationFunctionCounter.class);
		function.setPersistenceEntityClass(getPersistenceEntityClass());
		Map<String,Object> map = __getFiltersMap__(filters);
		if(map != null)
			function.setProperty(Properties.QUERY_FILTERS,map);
		return function.execute().getResponse();
	}

	/**/
	
	@SuppressWarnings("unchecked")
	protected Collection<ENTITY> __getEntities__(ENTITY_COLLECTION entityCollection) {
		if(entityCollection instanceof AbstractEntityCollection<?>)
			return (Collection<ENTITY>) ((AbstractEntityCollection<?>)entityCollection).getCollection();
		return null;
	}
	
	/**/
	
	protected static String[] __getFieldNames__(String string) {
		return StringUtils.isBlank(string) ? null : StringUtils.split(string,ConstantCharacter.COMA.toString());
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String,Object> __getFiltersMap__(String filters) {
		Map<String,Object> __filters__ = null;
		if(__injectStringHelper__().isNotBlank(filters)) {
			/*
			 * Convert filters from json to map
			 */
			__filters__ = (Map<String, Object>) __injectByQualifiersClasses__(ObjectFromStringBuilder.class,JavaScriptObjectNotation.Class.class)
				.setString(filters).setKlass(Map.class).execute().getOutput();
		}
		return __filters__;
	}
}
