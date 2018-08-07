package org.cyk.utility.system;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
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

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setMonitorable(Boolean.TRUE).setLoggable(Boolean.TRUE);
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
	public SystemAction getAction(){
		return (SystemAction) getProperties().getAction();
	}
	
	@Override
	public SystemFunction setAction(SystemAction action) {
		getProperties().setAction(action);
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
	public SystemFunction setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
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
		if(entity!=null)
			setEntityClass(entity.getClass());
		return this;
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
