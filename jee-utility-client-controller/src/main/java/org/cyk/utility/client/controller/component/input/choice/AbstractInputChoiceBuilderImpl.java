package org.cyk.utility.client.controller.component.input.choice;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.component.input.AbstractInputBuilderImpl;
import org.cyk.utility.field.FieldTypeGetter;
import org.cyk.utility.object.Objects;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.exception.ServiceNotFoundException;
import org.cyk.utility.system.layer.SystemLayerController;

public abstract class AbstractInputChoiceBuilderImpl<INPUT extends InputChoice<CHOICE>,CHOICE> extends AbstractInputBuilderImpl<INPUT,CHOICE> implements InputChoiceBuilder<INPUT,CHOICE> {
	private static final long serialVersionUID = 1L;

	private Objects choices;
	private Class<? extends ChoicePropertyValueBuilder> choiceLabelBuilderClass;
	
	@Override
	protected void __execute__(INPUT inputChoice, Object object, Field field) {
		super.__execute__(inputChoice, object, field);
		org.cyk.utility.client.controller.component.annotation.InputChoice inputChoiceAnnotation = field.getAnnotation(org.cyk.utility.client.controller.component.annotation.InputChoice.class);
		Objects choices = getChoices();
		if(choices == null) {
			choices = getChoices(Boolean.TRUE);
			Class<?> fieldType = __inject__(FieldTypeGetter.class).execute(field).getOutput();
			if(__injectClassHelper__().isInstanceOf(fieldType, Collection.class)) {
				fieldType = __injectFieldHelper__().getParameterAt(field, 0, Object.class);
			}
			
			if(fieldType.isEnum()) {
				for(Object index : fieldType.getEnumConstants()) {
					choices.add(index);
				}
			}else if(__inject__(SystemLayerController.class).getEntityLayer().isPackage(fieldType.getName())) {				
				//TODO can be move upper
				Collection<?> objects = null;
				Properties properties = new Properties();
				properties.copyFrom(getProperties(), Properties.REQUEST);
				properties.copyFrom(getProperties(), Properties.CONTEXT);
				try {
					objects = __inject__(Controller.class).readMany(fieldType,properties);
					Response response = (Response) properties.getResponse();					
					if(Boolean.TRUE.equals(__inject__(ResponseHelper.class).isFamilyClientError(response)))
						setThrowable((Throwable) __inject__(ServiceNotFoundException.class).setSystemAction((SystemAction) properties.getAction()).setResponse(response));
				}catch(Exception exception) {
					//Because we do not want to break view building we need to handle exception
					setThrowable(__injectThrowableHelper__().getFirstCause(exception));	
				}
				
				if(__injectCollectionHelper__().isNotEmpty(objects)) {
					for(Object index : objects) {
						choices.add(index);
					}	
				}
			}
		}
		
		if(__injectCollectionHelper__().isNotEmpty(choices)) {
			Class<? extends ChoicePropertyValueBuilder> choiceLabelBuilderClass = getChoiceLabelBuilderClass();
			if(choiceLabelBuilderClass == null)
				choiceLabelBuilderClass = inputChoiceAnnotation.labelBuilderClass();
			if(choiceLabelBuilderClass == null)
				choiceLabelBuilderClass = ChoicePropertyValueBuilderImpl.class;
			for(Object index : choices.get()) {
				ChoiceBuilder builder = __inject__(ChoiceBuilder.class).setValue(index);
				//builder.setValue(index);
				if(choiceLabelBuilderClass!=null)
					builder.setLabel(__inject__(choiceLabelBuilderClass).setObject(index).execute().getOutput());
				Object choice = builder.execute().getOutput();
				inputChoice.addChoices(choice);
			}
		}
	}
	
	@Override
	public Objects getChoices() {
		return choices;
	}
	
	@Override
	public Objects getChoices(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_CHOICES, injectIfNull);
	}
	
	@Override
	public InputChoiceBuilder<INPUT,CHOICE> setChoices(Objects choices) {
		this.choices = choices;
		return this;
	}
	
	@Override
	public InputChoiceBuilder<INPUT,CHOICE> addChoices(Collection<Object> choices) {
		getChoices(Boolean.TRUE).add(choices);
		return this;
	}
	
	@Override
	public InputChoiceBuilder<INPUT,CHOICE> addChoices(Object... choices) {
		getChoices(Boolean.TRUE).add(choices);
		return this;
	}
	
	@Override
	public Class<? extends ChoicePropertyValueBuilder> getChoiceLabelBuilderClass() {
		return choiceLabelBuilderClass;
	}
	
	@Override
	public InputChoiceBuilder<INPUT, CHOICE> setChoiceLabelBuilderClass(Class<? extends ChoicePropertyValueBuilder> choiceLabelBuilderClass) {
		this.choiceLabelBuilderClass = choiceLabelBuilderClass;
		return this;
	}
	
	/**/
	
	public static final String FIELD_CHOICES = "choices";
	
}
