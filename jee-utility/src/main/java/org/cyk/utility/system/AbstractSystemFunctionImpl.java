package org.cyk.utility.system;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.function.FunctionExecutionPhase;
import org.cyk.utility.__kernel__.function.FunctionExecutionPhaseMoment;
import org.cyk.utility.__kernel__.function.FunctionExecutionPhaseMomentBegin;
import org.cyk.utility.__kernel__.function.FunctionExecutionPhaseTry;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionsProvider;
import org.cyk.utility.assertion.AssertionsProviderClassMap;
import org.cyk.utility.assertion.AssertionsProviderFor;
import org.cyk.utility.clazz.ClassInstance;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.enumeration.EnumGetter;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.function.FunctionHelper;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.notification.NotificationBuilders;
import org.cyk.utility.notification.Notifications;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.system.action.SystemActor;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.type.BooleanHelper;
import org.cyk.utility.value.ValueHelper;
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

	private Strings entityFieldNames;
	private NotificationBuilders notificationBuilders;
	private Notifications notifications;
	private Long entitiesCount;
	private Boolean isActionRequired;
	
	/* Working variables */
	protected SystemAction __action__;
	protected ClassInstance __classInstance__;
	protected Throwable __throwable__;
	protected Collection<Object> __entities__,__entitiesSystemIdentifiers__,__entitiesBusinessIdentifiers__;
	protected Field __entityClassSystemIdentifierField__,__entityClassBusinessIdentifierField__;
	protected ValueUsageType __entityIdentifierValueUsageType__;
	protected Boolean __isBatchable__;
	protected Integer __batchSize__;
	
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
					,ConstantCharacter.SPACE.toString()));
		}
	}

	@Override
	protected void ____execute____() {
		__action__ = getAction();
		Boolean isActionRequired = __injectValueHelper__().defaultToIfNull(getIsActionRequired(),Boolean.TRUE);
		if(__action__ == null && Boolean.TRUE.equals(isActionRequired)) {			
			__inject__(ThrowableHelper.class).throwRuntimeException(getClass().getSimpleName()+" : action must not be null");
		} else {
			__initialiseWorkingVariables__();
			__execute__(__action__);	
		}
	}
	
	protected void __initialiseWorkingVariables__() {
		if(getEntityClass() != null) {
			__classInstance__ = __inject__(ClassInstancesRuntime.class).get(getEntityClass());
			__entityClassSystemIdentifierField__ = __classInstance__.getSystemIdentifierField();
			__entityClassBusinessIdentifierField__ = __classInstance__.getBusinessIdentifierField();	
		}
		
		__entityIdentifierValueUsageType__ = getEntityIdentifierValueUsageType();
		if(__entityIdentifierValueUsageType__ == null)
			__entityIdentifierValueUsageType__ = ValueUsageType.SYSTEM;
		
		__entities__ = __getEntities__();
		
		__initialiseEntitiesIdentifiers__();
		
		Integer numberOfProcessedElement = null;
		if(__injectCollectionHelper__().isNotEmpty(__entities__))
			numberOfProcessedElement = __entities__.size();
		else { 
			numberOfProcessedElement = __injectCollectionHelper__().getSize(__entitiesSystemIdentifiers__) + __injectCollectionHelper__().getSize(__entitiesBusinessIdentifiers__);
		}
		
		if(numberOfProcessedElement != null && numberOfProcessedElement > 0)
			addLogMessageBuilderParameter("count", numberOfProcessedElement);
		
		__isBatchable__ = __inject__(ValueHelper.class).defaultToIfNull(__inject__(BooleanHelper.class).get(getProperty(Properties.IS_BATCHABLE)),Boolean.FALSE);
		if(Boolean.TRUE.equals(__isBatchable__)) {
			__batchSize__ = __injectNumberHelper__().getInteger(getProperty(Properties.BATCH_SIZE), 30);
			addLogMessageBuilderParameter("batch", __batchSize__);
		}
	}
	
	@Override
	protected Log __injectLog__() {
		Log log =  super.__injectLog__();
		log.addMarkers(getSystemActor().getIdentifier().toString(),getSystemLayer().getIdentifier().toString());
		SystemAction action = getAction();
		//info action that might modify system state
		/*if(action instanceof SystemActionCreate || action instanceof SystemActionUpdate || action instanceof SystemActionDelete)
			log.setLevel(LogLevel.INFO);
		*/
		if(action!=null) {
			log.addMarkers(action.getIdentifier().toString());
			log.setSourceMethodName(log.getSourceMethodName()+ConstantCharacter.DOT+action.getIdentifier());
		}
		Class<?> entityClass = getEntityClass();
		if(entityClass!=null) {
			log.setSourceClassName(log.getSourceClassName()+ConstantCharacter.DOT+entityClass.getSimpleName());
			log.addMarkers(entityClass.getSimpleName());
		}
		return log;
	}
	
	@Override
	protected LogLevel __getLogLevel__(LogLevel logLevel) {
		if(logLevel == null) {
			SystemAction action = getAction();
			//info action that might modify system state
			if(action instanceof SystemActionCreate || action instanceof SystemActionUpdate || action instanceof SystemActionDelete)
				logLevel = LogLevel.INFO;	
		}
		return super.__getLogLevel__(logLevel);
	}
	
	@Override
	public SystemFunction execute() {
		return (SystemFunction) super.execute();
	}
	
	protected abstract void __execute__(SystemAction action);
	
	protected Collection<Object> __getEntities__() {
		Collection<Object> entities = null;
		if(getEntities() != null) {
			if(entities == null)
				entities = new ArrayList<>();
			entities.addAll(getEntities());
		}
		if(getEntity() != null) {
			if(entities == null)
				entities = new ArrayList<>();
			entities.add(getEntity());
		}
		return entities;
	}
	
	protected Collection<Object> __getEntitiesIdentifiers__(ValueUsageType valueUsageType) {
		Collection<Object> identifiers = null;
		
		if(__injectCollectionHelper__().isNotEmpty(__entities__)) {
			if(identifiers == null)
				identifiers = new ArrayList<>();
			
			Object identifier = null;
			for(Object index : __entities__) {
				if(ValueUsageType.SYSTEM.equals(valueUsageType) && __entityClassSystemIdentifierField__ != null)
					identifier = __injectFieldValueGetter__().execute(index, __entityClassSystemIdentifierField__).getOutput();
				
				if(identifier == null && ValueUsageType.BUSINESS.equals(valueUsageType) && __entityClassBusinessIdentifierField__ != null)
					identifier = __injectFieldValueGetter__().execute(index, __entityClassBusinessIdentifierField__).getOutput();
				
				if(identifier != null)
					identifiers.add(identifier);
			}			
		}
		
		if(__entityIdentifierValueUsageType__.equals(valueUsageType)) {
			if(getEntityIdentifier() != null) {
				if(identifiers == null)
					identifiers = new ArrayList<>();
				identifiers.add(getEntityIdentifier());
			}	
			
			if(__injectCollectionHelper__().isNotEmpty(getAction().getEntitiesIdentifiers())) {
				if(identifiers == null)
					identifiers = new ArrayList<>();
				identifiers.addAll(getAction().getEntitiesIdentifiers().get());
			}
		}
		
		return identifiers;
	}
	
	protected void __initialiseEntitiesIdentifiers__() {
		__entitiesSystemIdentifiers__ = __getEntitiesIdentifiers__(ValueUsageType.SYSTEM);
		__entitiesBusinessIdentifiers__ = __getEntitiesIdentifiers__(ValueUsageType.BUSINESS);
	}
	
	@Override
	public Boolean getIsActionRequired() {
		return isActionRequired;
	}
	
	@Override
	public SystemFunction setIsActionRequired(Boolean isActionRequired) {
		this.isActionRequired = isActionRequired;
		return this;
	}
	
	@Override
	public Strings getEntityFieldNames() {
		return entityFieldNames;
	}
	
	@Override
	public SystemFunction setEntityFieldNames(Strings entityFieldNames) {
		this.entityFieldNames = entityFieldNames;
		return this;
	}
	
	@Override
	public SystemFunction setEntityFieldNames(Collection<String> entityFieldNames) {
		if(__injectCollectionHelper__().isNotEmpty(entityFieldNames)) {
			Strings collection = getEntityFieldNames();
			if(collection == null)
				setEntityFieldNames(collection = __inject__(Strings.class));
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
			Strings collection = getEntityFieldNames();
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
	public SystemFunction setActionEntityClass(Class<?> entityClass) {
		SystemAction action = getAction();
		if(action!=null)
			action.setEntityClass(entityClass);
		return this;
	}
	
	@Override
	public SystemFunction addActionEntities(Collection<Object> entities) {
		SystemAction action = getAction();
		if(action!=null)
			action.getEntities(Boolean.TRUE).add(entities);
		return this;
	}
	
	@Override
	public SystemFunction addActionEntities(Object... entities) {
		return addActionEntities(__injectCollectionHelper__().instanciate(entities));
	}
	
	@Override
	public SystemFunction setActionEntityIdentifierClass(Class<?> entityIdentifierClass) {
		SystemAction action = getAction();
		if(action!=null)
			action.setEntityIdentifierClass(entityIdentifierClass);
		return this;
	}
	
	@Override
	public SystemFunction addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers) {
		SystemAction action = getAction();
		if(action!=null)
			action.getEntitiesIdentifiers(Boolean.TRUE).add(entitiesIdentifiers);
		return this;
	}
	
	@Override
	public SystemFunction addActionEntitiesIdentifiers(Object... entitiesIdentifiers) {
		SystemAction action = getAction();
		if(action!=null)
			action.getEntitiesIdentifiers(Boolean.TRUE).add(entitiesIdentifiers);
		return this;
	}
	
	@Override
	public Class<?> getEntityClass() {
		return (Class<?>) getProperties().getEntityClass();
	}
	
	@Override //TODO to be removed
	public SystemFunction setEntityClass(Class<?> aClass) {
		getProperties().setEntityClass(aClass);
		setActionEntityClass(aClass);
		return this;
	}
	
	@Override
	public Object getEntityIdentifier() {
		return getProperties().getEntityIdentifier();
	}
	
	@Override
	public SystemFunction setEntityIdentifier(Object identifier) {
		getProperties().setEntityIdentifier(identifier);
		getAction().getEntitiesIdentifiers(Boolean.TRUE).removeAll();
		getAction().getEntitiesIdentifiers(Boolean.TRUE).add(identifier);
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
		SystemAction action = getAction();
		if(action!=null) {
			if(action.getEntities()!=null)
				action.getEntities().removeAll();
		}
		if(entity!=null) {
			if(action!=null) {
				action.getEntities(Boolean.TRUE).add(entity);
			}
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
			if(preConditionsAssertionsProvider instanceof AssertionsProviderFor<?> && ((AssertionsProviderFor<?>)preConditionsAssertionsProvider).getFor() == null)
				((AssertionsProviderFor<Object>)preConditionsAssertionsProvider).setFor(entity);	
		}
		
		AssertionsProvider postConditionsAssertionsProvider = (AssertionsProvider) getPostConditionsAssertionsProvider();
		if(entity!=null && postConditionsAssertionsProvider==null)
			setPostConditionsAssertionsProvider(postConditionsAssertionsProvider = __inject__(AssertionsProviderClassMap.class).inject(entity));
		if(postConditionsAssertionsProvider!=null) {
			if(postConditionsAssertionsProvider.getFunction() == null)
				postConditionsAssertionsProvider.setFunction(this);
			if(postConditionsAssertionsProvider.getFilter() == null)
				postConditionsAssertionsProvider.setFilter(postFilter);	
			if(postConditionsAssertionsProvider instanceof AssertionsProviderFor<?> && ((AssertionsProviderFor<?>)postConditionsAssertionsProvider).getFor() == null)
				((AssertionsProviderFor<Object>)postConditionsAssertionsProvider).setFor(entity);
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
	
	@Override
	public Long getEntitiesCount() {
		return entitiesCount;
	}
	
	@Override
	public SystemFunction setEntitiesCount(Long entitiesCount) {
		this.entitiesCount = entitiesCount;
		return this;
	}
	
	/**/
	
	protected abstract SystemActor getSystemActor();
	
	protected abstract SystemLayer getSystemLayer();
	
	/* following can be more upper*/
	
	protected Collection<FieldName> __getLoggedEntityFieldNames__(){
		return __inject__(CollectionHelper.class).instanciate(FieldName.IDENTIFIER);
	}
	
	protected Collection<ValueUsageType> getValueUsageTypes(FieldName fieldName){
		return __inject__(CollectionHelper.class).instanciate(ValueUsageType.values());
	}
	
	protected Object getEnityFieldValue(Object entity,FieldName fieldName,ValueUsageType valueUsageType,String derivedFieldName){
		return __inject__(FieldValueGetter.class).setObject(entity).setField(derivedFieldName).execute().getOutput();
	}

	@Override
	public NotificationBuilders getNotificationBuilders() {
		return notificationBuilders;
	}
	
	@Override
	public NotificationBuilders getNotificationBuilders(Boolean injectIfNull) {
		return (NotificationBuilders) __getInjectIfNull__(FIELD_NOTIFICATION_BUILDERS, injectIfNull);
	}
	
	@Override
	public SystemFunction setNotificationBuilders(NotificationBuilders notificationBuilders) {
		this.notificationBuilders = notificationBuilders;
		return this;
	}
	
	@Override
	public Notifications getNotifications() {
		return notifications;
	}
	
	@Override
	public Notifications getNotifications(Boolean injectIfNull) {
		return (Notifications) __getInjectIfNull__(FIELD_NOTIFICATIONS, injectIfNull);
	}
	
	@Override
	public SystemFunction setNotifications(Notifications notifications) {
		this.notifications = notifications;
		return this;
	}
	
	/**/
	
	protected void __produceFunction__(String function,Map<String,String> inputs,Map<String,String> outputs) {
		__inject__(FunctionHelper.class).produce(function, inputs, outputs);
	}
	
	/**/
	
	/**/
	
	public static final String FIELD_NOTIFICATION_BUILDERS = "notificationBuilders";
	public static final String FIELD_NOTIFICATIONS = "notifications";
	public static final String FIELD_ENTITY_FIELD_NAMES = "entityFieldNames";
}
