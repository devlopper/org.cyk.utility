package org.cyk.utility.system;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.function.FunctionExecutionPhase;
import org.cyk.utility.__kernel__.function.FunctionExecutionPhaseMoment;
import org.cyk.utility.__kernel__.function.FunctionExecutionPhaseMomentBegin;
import org.cyk.utility.__kernel__.function.FunctionExecutionPhaseTry;
import org.cyk.utility.assertion.AssertionsProvider;
import org.cyk.utility.assertion.AssertionsProviderClassMap;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionInstanceString;
import org.cyk.utility.enumeration.EnumGetter;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.system.action.SystemActor;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Christian
 *
 */
@Getter @Setter @Accessors(chain=true)
public abstract class AbstractSystemFunctionImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements SystemFunction, Serializable {
	private static final long serialVersionUID = 1L;

	private CollectionInstanceString entityFieldNames;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setMonitorable(Boolean.TRUE).setLoggable(Boolean.TRUE);
	}
	
	@Override
	protected void __executePhaseMoment__(FunctionExecutionPhase executionPhase,Class<? extends FunctionExecutionPhaseMoment> momentClass) {
		super.__executePhaseMoment__(executionPhase, momentClass);
		if(executionPhase instanceof FunctionExecutionPhaseTry && FunctionExecutionPhaseMomentBegin.class.equals(momentClass)) {
			//we put markers in message to support those logging framework which do not handle markers
			addLogMessageBuilderParameter(__inject__(StringHelper.class).concatenate(__injectCollectionHelper__().cast(String.class, getLog(Boolean.TRUE).getMarkers())
					,CharacterConstant.SPACE.toString()));
		}
	}

	@Override
	protected void ____execute____() {
		SystemAction action = getAction();
		if(action == null){
			__inject__(ThrowableHelper.class).throwRuntimeException(getClass().getSimpleName()+" : action must not be null");
		}else{
			__execute__(action);	
		}
	}
	
	@Override
	protected Log __injectLog__() {
		Log log =  super.__injectLog__();
		log.addMarkers(getSystemActor().getIdentifier().toString(),getSystemLayer().getIdentifier().toString());
		SystemAction action = getAction();
		//info action that might modify system state
		if(action instanceof SystemActionCreate || action instanceof SystemActionUpdate || action instanceof SystemActionDelete)
			log.setLevel(LogLevel.INFO);
		if(action!=null) {
			log.addMarkers(action.getIdentifier().toString());
			log.setSourceMethodName(log.getSourceMethodName()+CharacterConstant.DOT+action.getIdentifier());
		}
		Class<?> entityClass = getEntityClass();
		if(entityClass!=null) {
			log.setSourceClassName(log.getSourceClassName()+CharacterConstant.DOT+entityClass.getSimpleName());
			log.addMarkers(entityClass.getSimpleName());
		}
		return log;
	}
	
	@Override
	public SystemFunction execute() {
		return (SystemFunction) super.execute();
	}
	
	protected abstract void __execute__(SystemAction action);
	
	@Override
	public CollectionInstanceString getEntityFieldNames() {
		return entityFieldNames;
	}
	
	@Override
	public SystemFunction setEntityFieldNames(CollectionInstanceString entityFieldNames) {
		this.entityFieldNames = entityFieldNames;
		return this;
	}
	
	@Override
	public SystemFunction setEntityFieldNames(Collection<String> entityFieldNames) {
		if(__injectCollectionHelper__().isNotEmpty(entityFieldNames)) {
			CollectionInstanceString collection = getEntityFieldNames();
			if(collection == null)
				setEntityFieldNames(collection = __inject__(CollectionInstanceString.class));
			collection.add(entityFieldNames);
		}
		return this;
	}
	
	@Override
	public SystemFunction setEntityFieldNames(String... entityFieldNames) {
		return setEntityFieldNames(__injectCollectionHelper__().instanciate(entityFieldNames));
	}
	
	@Override
	public SystemFunction addEntityFieldNames(Collection<String> entityFieldNames) {
		if(__injectCollectionHelper__().isNotEmpty(entityFieldNames)) {
			CollectionInstanceString collection = getEntityFieldNames();
			if(collection == null)
				setEntityFieldNames(entityFieldNames);
			else
				collection.add(entityFieldNames);
		}
		return this;
	}
	
	@Override
	public SystemFunction addEntityFieldNames(String... entityFieldNames) {
		return addEntityFieldNames(__injectCollectionHelper__().instanciate(entityFieldNames));
	}
	
	@Override
	public SystemAction getAction(){
		return (SystemAction) getProperties().getAction();
	}
	
	@Override
	public SystemFunction setAction(SystemAction action) {
		getProperties().setAction(action);
		if(action!=null) {
			Object entity = getEntity();
			if(Boolean.TRUE.equals(__getIsSetConditionsAssertionsProviderFromEntity__(entity))) {
				__setConditionsAssertionsProvidersIfNull__(entity, action, null);
			}
		}
		return this;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return (Class<?>) getProperties().getEntityClass();
	}
	
	@Override
	public SystemFunction setEntityClass(Class<?> aClass) {
		getProperties().setEntityClass(aClass);
		return this;
	}
	
	@Override
	public Object getEntityIdentifier() {
		return getProperties().getEntityIdentifier();
	}
	
	@Override
	public SystemFunction setEntityIdentifier(Object identifier) {
		getProperties().setEntityIdentifier(identifier);
		return this;
	}
	
	@Override
	public ValueUsageType getEntityIdentifierValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}
	
	@Override
	public SystemFunction setEntityIdentifierValueUsageType(Object object) {
		ValueUsageType valueUsageType = __inject__(EnumGetter.class).setClazz(ValueUsageType.class).setName(object).setIsNameCaseSensitive(Boolean.FALSE).execute()
				.getOutputAs(ValueUsageType.class);
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@Override
	public Object getEntity() {
		return getProperties().getEntity();
	}
	
	@Override
	public SystemFunction setEntity(Object entity) {
		getProperties().setEntity(entity);
		if(entity!=null) {
			setEntityClass(entity.getClass());
			if(Boolean.TRUE.equals(__getIsSetConditionsAssertionsProviderFromEntity__(entity))) {
				__setConditionsAssertionsProvidersIfNull__(entity, getAction(), null);
			}
		}
		return this;
	}
	
	protected Boolean __getIsSetConditionsAssertionsProviderFromEntity__(Object entity) {
		return Boolean.FALSE;
	}
	
	protected void __setConditionsAssertionsProvidersIfNull__(Object entity,Object preFilter,Object postFilter) {
		AssertionsProvider preConditionsAssertionsProvider = (AssertionsProvider) getPreConditionsAssertionsProvider();
		if(entity!=null && preConditionsAssertionsProvider==null)
			setPreConditionsAssertionsProvider(preConditionsAssertionsProvider = __inject__(AssertionsProviderClassMap.class).inject(entity));
		
		if(preConditionsAssertionsProvider!=null) {
			if(preConditionsAssertionsProvider.getFunction() == null)
				preConditionsAssertionsProvider.setFunction(this);
			if(preConditionsAssertionsProvider.getFilter() == null)
				preConditionsAssertionsProvider.setFilter(preFilter);	
		}
		
		AssertionsProvider postConditionsAssertionsProvider = (AssertionsProvider) getPostConditionsAssertionsProvider();
		if(entity!=null && postConditionsAssertionsProvider==null)
			setPostConditionsAssertionsProvider(postConditionsAssertionsProvider = __inject__(AssertionsProviderClassMap.class).inject(entity));
		if(postConditionsAssertionsProvider!=null) {
			if(postConditionsAssertionsProvider.getFunction() == null)
				postConditionsAssertionsProvider.setFunction(this);
			if(postConditionsAssertionsProvider.getFilter() == null)
				postConditionsAssertionsProvider.setFilter(postFilter);	
		}
		
	}
	
	@Override
	public Collection<?> getEntities() {
		return (Collection<Object>) getProperties().getEntities();
	}
	
	@Override
	public SystemFunction setEntities(Collection<?> entities) {
		getProperties().setEntities(entities);
		if(__inject__(CollectionHelper.class).isNotEmpty(entities))
			setEntityClass(entities.iterator().next().getClass());
		return this;
	}
	
	/**/
	
	protected abstract SystemActor getSystemActor();
	
	protected abstract SystemLayer getSystemLayer();
	
	/* following can be more upper*/
	
	protected Collection<FieldName> getLoggedEntityFieldNames(){
		return __inject__(CollectionHelper.class).instanciate(FieldName.IDENTIFIER);
	}
	
	protected Collection<ValueUsageType> getValueUsageTypes(FieldName fieldName){
		return __inject__(CollectionHelper.class).instanciate(ValueUsageType.values());
	}
	
	protected Object getEnityFieldValue(Object entity,FieldName fieldName,ValueUsageType valueUsageType,String derivedFieldName){
		return __inject__(FieldValueGetter.class).setObject(entity).setField(derivedFieldName).execute().getOutput();
	}

}
