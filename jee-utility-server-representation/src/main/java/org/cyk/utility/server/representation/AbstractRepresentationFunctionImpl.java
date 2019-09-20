package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.AbstractSystemFunctionServerImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerRepresentation;

public abstract class AbstractRepresentationFunctionImpl extends AbstractSystemFunctionServerImpl implements RepresentationFunction, Serializable {
	private static final long serialVersionUID = 1L;
	
	public static LogLevel LOG_LEVEL = LogLevel.TRACE;
	
	protected ResponseBuilder __responseBuilder__;
	protected Class<?> __persistenceEntityClass__;
	protected Collection<Object> __persistenceEntities__;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setLogLevel(LOG_LEVEL);
	}
	
	@Override
	protected void __initialiseWorkingVariables__() {
		super.__initialiseWorkingVariables__();
		__persistenceEntityClass__ = getPersistenceEntityClass();
		if(__persistenceEntityClass__ == null) {
			Class<?> klass = null;
			if(getEntityClass()==null) {
				if(__entities__!= null && !__entities__.isEmpty())
					klass = __entities__.iterator().next().getClass();	
			}else {
				klass = getEntityClass();
			}			
			if(klass != null) {
				String className = klass.getName();
				className = StringUtils.replaceOnce(className, ".representation.", ".persistence.");
				className = StringUtils.removeEnd(className, "Dto");
				__persistenceEntityClass__ = __injectClassHelper__().getByName(className);	
			}
		}
	}
	
	@Override
	protected void __execute__(SystemAction action) {
		__responseBuilder__ = __instanciateResponseBuilder__();
		try {
			__executeBusiness__();
			if(__persistenceEntities__ != null && !__persistenceEntities__.isEmpty()) {
				Collection<String> identifiersSystem = new ArrayList<String>();
				Collection<String> identifiersBusiness = new ArrayList<String>();
				//TODO use instanceof interface to gain speed in processing. create an interface for PersistenceEntity and use it
				Field persistenceSystemIdentifierField = __inject__(ClassInstancesRuntime.class).get(__persistenceEntityClass__).getSystemIdentifierField();
				Field persistenceBusinessIdentifierField = __inject__(ClassInstancesRuntime.class).get(__persistenceEntityClass__).getBusinessIdentifierField();
				Integer count = 0;
				for(Object index : __persistenceEntities__) {
					Object dto = CollectionHelperImpl.__getElementAt__(__entities__, count);
					Object identifier = FieldHelperImpl.__read__(index, persistenceSystemIdentifierField); 
					if(identifier != null) {
						identifiersSystem.add(identifier.toString());
						if(__entityClassSystemIdentifierField__ != null) {
							FieldHelperImpl.__write__(dto, __entityClassSystemIdentifierField__, identifier.toString());
						}
					}					
					identifier = FieldHelperImpl.__read__(index, persistenceBusinessIdentifierField);
					if(identifier != null) {
						identifiersBusiness.add(identifier.toString());
						if(__entityClassBusinessIdentifierField__ != null) {
							FieldHelperImpl.__write__(dto, __entityClassBusinessIdentifierField__, identifier.toString());
						}
					}				
					count++;
				}
				ResponseHelper.addHeader(__responseBuilder__, Constant.RESPONSE_HEADER_X_TOTAL_COUNT, __entities__.size());
				ResponseHelper.addHeader(__responseBuilder__, Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_SYSTEM, identifiersSystem);
				ResponseHelper.addHeader(__responseBuilder__, Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_BUSINESS, identifiersBusiness);
			}
		} catch (Exception exception) {
			__throwable__ = exception;
			System.out.println("AbstractRepresentationFunctionImpl.__execute__() THROWABLE");
			exception.printStackTrace();
			//__log__(exception);
		} finally {
			__processResponseBuilder__();
		}
		setResponse(__responseBuilder__.build());
	}
	
	protected abstract void __executeBusiness__();
	
	/*@Override
	protected LogLevel __getLogLevel__() {
		return LogLevel.INFO;
	}*/
	
	protected ResponseBuilder __instanciateResponseBuilder__() {
		return Response.noContent();
	}
	
	protected void __processResponseBuilder__() {
		__responseBuilder__.status(__computeResponseStatus__()).entity(__computeResponseEntity__());
	}
	
	protected Response.Status __computeResponseStatus__(){
		if(__throwable__==null)
			return Response.Status.OK;
		return Response.Status.INTERNAL_SERVER_ERROR;
	}
	
	protected Object __computeResponseEntity__(){
		ResponseEntityDto responseEntityDto = new ResponseEntityDto();
		if(__throwable__==null) {
			__computeResponseEntity__(responseEntityDto);
		}else {
			__computeResponseEntity__(responseEntityDto, __throwable__);
		}
		return responseEntityDto;
	}
	
	protected void __computeResponseEntity__(ResponseEntityDto responseEntityDto){
		responseEntityDto.setStatusUsingEnumeration(ResponseEntityDto.Status.SUCCESS);
		responseEntityDto.addMessage(new MessageDto().setHead(__persistenceEntityClass__.getSimpleName()+" a été "+getAction().getIdentifier()+" avec succès."));
	}
	
	protected void __computeResponseEntity__(ResponseEntityDto responseEntityDto,Throwable throwable){
		responseEntityDto.setStatusUsingEnumeration(ResponseEntityDto.Status.FAILURE);
		Throwable cause = __injectThrowableHelper__().getFirstCause(throwable);	
		//TODO cause can be null , find the cause which is not null otherwise use throwable itself
		//throwable.printStackTrace();
		responseEntityDto.addMessage(new MessageDto().setHead("Une erreur est survenue lors de "+getAction().getIdentifier()+" de "+getPersistenceEntityClass()+". "+cause.getMessage()));
	}
	
	protected void __processResponseBuilder__(ResponseBuilder builder,Throwable throwable) {
		builder.status(__computeResponseStatus__()).entity(__computeResponseEntity__());
	}
	
	@Override
	public RepresentationFunction execute() {
		return (RepresentationFunction) super.execute();
	}
	
	@Override
	public Class<?> getPersistenceEntityClass() {
		return (Class<?>) getProperties().getFromPath(Properties.PERSISTENCE,Properties.ENTITY_CLASS);
	}
	
	@Override
	public RepresentationFunction setPersistenceEntityClass(Class<?> aClass) {
		getProperties().setFromPath(new Object[] {Properties.PERSISTENCE,Properties.ENTITY_CLASS},aClass);
		return this;
	}
	
	@Override
	public Response getResponse() {
		return (Response) getProperties().getResponse();
	}
	
	@Override
	public RepresentationFunction setResponse(Response response) {
		getProperties().setResponse(response);
		return this;
	}
	
	@Override
	public RepresentationFunction setEntity(Object entity) {
		return (RepresentationFunction) super.setEntity(entity);
	}
	
	@Override
	public RepresentationFunction setEntityIdentifierValueUsageType(Object valueUsageType) {
		return (RepresentationFunction) super.setEntityIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public RepresentationFunction setEntityIdentifier(Object identifier) {
		return (RepresentationFunction) super.setEntityIdentifier(identifier);
	}
	
	@Override
	public RepresentationFunction setAction(SystemAction action) {
		return (RepresentationFunction) super.setAction(action);
	}
	
	@Override
	public RepresentationFunction setEntityClass(Class<?> aClass) {
		return (RepresentationFunction) super.setEntityClass(aClass);
	}
	
	@Override
	public RepresentationFunction addEntityFieldNames(Collection<String> entityFieldNames) {
		return (RepresentationFunction) super.addEntityFieldNames(entityFieldNames);
	}
	
	@Override
	public RepresentationFunction setEntityFieldNames(String... entityFieldNames) {
		return (RepresentationFunction) super.setEntityFieldNames(entityFieldNames);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerRepresentation.class);
	}

	protected Business __injectBusiness__() {
		return __inject__(Business.class);
	}
	
	@Override
	public RepresentationFunction setProperty(Object key, Object value) {
		return (RepresentationFunction) super.setProperty(key, value);
	}
	
	protected static ResponseHelper __injectResponseHelper__() {
		return __inject__(ResponseHelper.class);
	}
	
	/*
	@Override
	public Strings getFieldNamesStrings() {
		return fieldNamesStrings;
	}
	
	@Override
	public Strings getFieldNamesStrings(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_FIELD_NAMES_STRINGS, injectIfNull);
	}
	
	@Override
	public RepresentationFunction setFieldNamesStrings(Strings fieldNamesStrings) {
		this.fieldNamesStrings = fieldNamesStrings;
		return this;
	}
	
	@Override
	public RepresentationFunction addFieldNamesStrings(Collection<String> fieldNamesStrings) {
		getFieldNamesStrings(Boolean.TRUE).add(fieldNamesStrings);
		return this;
	}
	
	@Override
	public RepresentationFunction addFieldNamesStrings(String... fieldNamesStrings) {
		getFieldNamesStrings(Boolean.TRUE).add(fieldNamesStrings);
		return this;
	}
	*/
	
	
	/**/
	
	//public static final String FIELD_FIELD_NAMES_STRINGS = "fieldNamesStrings";
}
