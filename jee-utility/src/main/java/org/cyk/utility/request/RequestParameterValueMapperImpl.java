package org.cyk.utility.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionAdd;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.__kernel__.system.action.SystemActionProcess;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.system.action.SystemActionRemove;
import org.cyk.utility.__kernel__.system.action.SystemActionSelect;
import org.cyk.utility.__kernel__.system.action.SystemActionTree;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.__kernel__.system.action.SystemActionView;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterNameStringBuilder;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueMatrix;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueStringBuilder;
import org.cyk.utility.object.ObjectByStringMap;

@Dependent @Deprecated
public class RequestParameterValueMapperImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements RequestParameterValueMapper,Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Map<String,Class<?>> SYSTEM_ACTIONS_CLASSES_VALUES = new HashMap<>();
	
	static {
		//TODO use reflection to get all SystemAction sub interfaces
		for(Class<?> index : new Class<?>[] {SystemActionAdd.class,SystemActionCreate.class,SystemActionDelete.class,SystemActionList.class
			,SystemActionProcess.class,SystemActionRead.class,SystemActionRemove.class,SystemActionSelect.class,SystemActionUpdate.class,SystemActionView.class
			,SystemActionTree.class}) {
			SYSTEM_ACTIONS_CLASSES_VALUES.put(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(index).execute().getOutput(), index);
		}
	}
	
	private Object parameterName;
	private String parameterValue;
	private ObjectByStringMap parameters;
	
	@Override
	protected Object __execute__() throws Exception {
		Object value = null;
		Object parameterName = getParameterName();
		if(parameterName!=null) {
			String parameterValue = getParameterValue();
			if(parameterValue == null) {
				parameterName = __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setName(parameterName).execute().getOutput();
				parameterValue = __inject__(RequestParameterValueGetter.class).setParameterName(parameterName).execute().getOutputAs(String.class);
			
			}
			/*
			if(parameterValue == null) {
				ObjectByStringMap parameters = getParameters();
				if(parameters!=null) {
					Object object = parameters.get(parameterName);
					if(object instanceof String)
						parameterValue = object.toString();
					else
						throw new RuntimeException("Multiple parameter value not yet handled");
				}
			}
			*/
			if(StringHelper.isNotBlank(parameterValue)) {
				if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsActionClass().execute().getOutput().equals(parameterName)) {
					value = __getSystemAction__(parameterValue,UniformResourceIdentifierParameterNameStringBuilder.Name.ACTION_IDENTIFIER);					
					if(value!=null) {
						SystemAction systemAction = (SystemAction) value;
						Class<?> aClass = __inject__(RequestParameterValueMapper.class).setParameterNameAsEntityClass().execute().getOutputAs(Class.class);
						if(aClass!=null) {
							((SystemAction)value).getEntities(Boolean.TRUE).setElementClass(aClass);
							String entityIdentifier = __inject__(RequestParameterValueMapper.class).setParameterNameAsEntityIdentifier().execute().getOutputAs(String.class);
							if(entityIdentifier!=null) {
								//TODO Is it a Number ? a String ? ... How to find out the target identifier field type ?
								//For now we will use string
								systemAction.getEntitiesIdentifiers(Boolean.TRUE).add(entityIdentifier);
							}
						}
						
						systemAction.setNextAction(__inject__(RequestParameterValueMapper.class).setParameterNameAsNextActionClass().execute().getOutputAs(SystemAction.class));
					}
				}else if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsActionIdentifier().execute().getOutput().equals(parameterName)) {
					value = parameterValue;
				}else if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsNextActionClass().execute().getOutput().equals(parameterName)) {
					value = __getSystemAction__(parameterValue,UniformResourceIdentifierParameterNameStringBuilder.Name.NEXT_ACTION_IDENTIFIER);
				}else if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsNextActionIdentifier().execute().getOutput().equals(parameterName)) {
					value = parameterValue;
				}else if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsEntityIdentifier().execute().getOutput().equals(parameterName)) {
					//TODO Is it a Number ? a String ? ... How to find out the target identifier field type ?
					//value = __inject__(NumberHelper.class).getLong(parameterValue);
					//For now we will use string
					value = parameterValue;
				}else if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsEntityClass().execute().getOutput().equals(parameterName)) {
					value = CollectionHelper.getFirst(__inject__(UniformResourceIdentifierParameterValueMatrix.class).getClassMap().getKeys(parameterValue));
				}else if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsWindowRenderTypeClass().execute().getOutput().equals(parameterName)) {
					value = CollectionHelper.getFirst(__inject__(UniformResourceIdentifierParameterValueMatrix.class).getClassMap().getKeys(parameterValue));
				}
				
				if(value == null)
					throw new RuntimeException("request parameter "+parameterName+":"+parameterValue+" cannot be mapped.");
			}
		}
		return value;
	}
	
	protected SystemAction __getSystemAction__(Object parameterValue,Object identifierName) {
		Class<? extends SystemAction> systemActionClass = null;
		SystemAction systemAction = null;
		systemActionClass = (Class<? extends SystemAction>) SYSTEM_ACTIONS_CLASSES_VALUES.get(parameterValue);
		
		if(systemActionClass == null) {
			/*
			if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionCreate.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionCreate.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionRead.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionRead.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionUpdate.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionUpdate.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionDelete.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionDelete.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionList.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionList.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionSelect.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionSelect.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionProcess.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionProcess.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionAdd.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionAdd.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionRemove.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionRemove.class;
			else if(__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionView.class).execute().getOutput().equals(parameterValue))
				systemActionClass = SystemActionView.class;
			*/
		}
		
		if(systemActionClass!=null) {
			systemAction = __inject__(systemActionClass);
			Object identifier = __inject__(RequestParameterValueMapper.class).setParameterName(identifierName).execute().getOutput();
			if(identifier!=null)	
				systemAction.setIdentifier(identifier);
		}
		
		return systemAction;
	}
	
	@Override
	public Object getParameterName() {
		return parameterName;
	}

	@Override
	public RequestParameterValueMapper setParameterName(Object parameterName) {
		this.parameterName = parameterName;
		return this;
	}

	@Override
	public String getParameterValue() {
		return parameterValue;
	}

	@Override
	public RequestParameterValueMapper setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
		return this;
	}
	
	@Override
	public RequestParameterValueMapper setParameterNameAsActionClass() {
		return setParameterName(UniformResourceIdentifierParameterNameStringBuilder.Name.ACTION_CLASS);
	}
	
	@Override
	public RequestParameterValueMapper setParameterNameAsActionIdentifier() {
		return setParameterName(UniformResourceIdentifierParameterNameStringBuilder.Name.ACTION_IDENTIFIER);
	}
	
	@Override
	public RequestParameterValueMapper setParameterNameAsNextActionClass() {
		return setParameterName(UniformResourceIdentifierParameterNameStringBuilder.Name.NEXT_ACTION_CLASS);
	}
	
	@Override
	public RequestParameterValueMapper setParameterNameAsNextActionIdentifier() {
		return setParameterName(UniformResourceIdentifierParameterNameStringBuilder.Name.NEXT_ACTION_IDENTIFIER);
	}
	
	@Override
	public RequestParameterValueMapper setParameterNameAsEntityClass() {
		return setParameterName(UniformResourceIdentifierParameterNameStringBuilder.Name.ENTITY_CLASS);
	}
	
	@Override
	public RequestParameterValueMapper setParameterNameAsEntityIdentifier() {
		return setParameterName(UniformResourceIdentifierParameterNameStringBuilder.Name.ENTITY_IDENTIFIER);
	}
	
	@Override
	public RequestParameterValueMapper setParameterNameAsWindowRenderTypeClass() {
		return setParameterName(UniformResourceIdentifierParameterNameStringBuilder.Name.WINDOW_RENDER_TYPE_CLASS);
	}

	@Override
	public ObjectByStringMap getParameters() {
		return parameters;
	}
	
	@Override
	public ObjectByStringMap getParameters(Boolean injectIfNull) {
		return (ObjectByStringMap) __getInjectIfNull__(FIELD_PARAMETERS, injectIfNull);
	}

	@Override
	public RequestParameterValueMapper setParameters(ObjectByStringMap parameters) {
		this.parameters = parameters;
		return this;
	}

	public static final String FIELD_PARAMETERS = "parameters";
	
}
