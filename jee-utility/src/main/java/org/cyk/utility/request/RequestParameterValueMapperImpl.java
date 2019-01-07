package org.cyk.utility.request;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterNameStringBuilder;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueMatrix;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueStringBuilder;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.object.ObjectByStringMap;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionRemove;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.system.action.SystemActionUpdate;

public class RequestParameterValueMapperImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements RequestParameterValueMapper,Serializable {
	private static final long serialVersionUID = 1L;
	
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
						__injectThrowableHelper__().throwRuntimeException("Multiple parameter value not yet handled");
				}
			}
			*/
			if(__injectStringHelper__().isNotBlank(parameterValue)) {
				if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsActionClass().execute().getOutput().equals(parameterName)) {
					value = __getSystemAction__(parameterValue,UniformResourceIdentifierParameterNameStringBuilder.Name.ACTION_IDENTIFIER);					
					if(value!=null) {
						SystemAction systemAction = (SystemAction) value;
						Class<?> aClass = __inject__(RequestParameterValueMapper.class).setParameterNameAsEntityClass().execute().getOutputAs(Class.class);
						if(aClass!=null) {
							((SystemAction)value).getEntities(Boolean.TRUE).setElementClass(aClass);
							Long entityIdentifier = __inject__(RequestParameterValueMapper.class).setParameterNameAsEntityIdentifier().execute().getOutputAs(Long.class);
							if(entityIdentifier!=null)
								systemAction.getEntitiesIdentifiers(Boolean.TRUE).add(entityIdentifier);
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
					value = __inject__(NumberHelper.class).getLong(parameterValue);
				}else if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsEntityClass().execute().getOutput().equals(parameterName)) {
					value = __injectCollectionHelper__().getFirst(__inject__(UniformResourceIdentifierParameterValueMatrix.class).getClassMap().getKeys(parameterValue));
				}else if(__inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsWindowRenderTypeClass().execute().getOutput().equals(parameterName)) {
					value = __injectCollectionHelper__().getFirst(__inject__(UniformResourceIdentifierParameterValueMatrix.class).getClassMap().getKeys(parameterValue));
				}
				
				if(value == null)
					__injectThrowableHelper__().throwRuntimeException("request parameter "+parameterName+":"+parameterValue+" cannot be mapped.");
			}
		}
		return value;
	}
	
	protected SystemAction __getSystemAction__(Object parameterValue,Object identifierName) {
		Class<? extends SystemAction> systemActionClass = null;
		SystemAction systemAction = null;
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
