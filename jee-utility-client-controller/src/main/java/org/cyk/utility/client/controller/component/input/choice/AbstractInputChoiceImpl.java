package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.client.controller.component.input.AbstractInputImpl;
import org.cyk.utility.object.Objects;
import org.cyk.utility.value.ValueHelper;

public abstract class AbstractInputChoiceImpl<CHOICE> extends AbstractInputImpl<CHOICE> implements InputChoice<CHOICE>,Serializable {
	private static final long serialVersionUID = 1L;

	private Objects choices;
	private Class<? extends ChoicePropertyValueBuilder> choicePropertyValueBuilderClass,__choicePropertyValueBuilderClass__;
	private Integer maximumNumberOfChoice,__maximumNumberOfChoice__;
	private ChoicesLayout choicesLayout;
	
	@Override
	public Objects getChoices() {
		return choices;
	}
	
	@Override
	public Objects getChoices(Boolean injectIfNull) {
		if(choices == null && Boolean.TRUE.equals(injectIfNull))
			choices = __inject__(Objects.class);
		return choices;
	}
	
	@Override
	public InputChoice<CHOICE> setChoices(Objects choices) {
		this.choices = choices;
		return this;
	}
	
	@Override
	public InputChoice<CHOICE> addChoices(Collection<Object> choices) {
		getChoices(Boolean.TRUE).add(choices);
		return this;
	}
	
	@Override
	public InputChoice<CHOICE> addChoices(Object... choices) {
		getChoices(Boolean.TRUE).add(choices);
		return this;
	}
	
	@Override
	public Class<? extends ChoicePropertyValueBuilder> getChoicePropertyValueBuilderClass() {
		return choicePropertyValueBuilderClass;
	}
	
	@Override
	public InputChoice<CHOICE> setChoicePropertyValueBuilderClass(Class<? extends ChoicePropertyValueBuilder> choicePropertyValueBuilderClass) {
		this.choicePropertyValueBuilderClass = choicePropertyValueBuilderClass;
		return this;
	}
	
	@Override
	public Object getChoiceProperty(Object choice, ChoiceProperty property) {
		if(choice == null)
			return null;
		if(__choicePropertyValueBuilderClass__ == null) {
			Class<? extends ChoicePropertyValueBuilder> choicePropertyValueBuilderClass = getChoicePropertyValueBuilderClass();
			if(choicePropertyValueBuilderClass == null) {
				Field field = getField();
				if(field != null) {
					org.cyk.utility.client.controller.component.annotation.InputChoice inputChoiceAnnotation = field.getAnnotation(org.cyk.utility.client.controller.component.annotation.InputChoice.class);
					if(inputChoiceAnnotation != null)
						choicePropertyValueBuilderClass = inputChoiceAnnotation.choicePropertyValueBuilderClass();	
				}
			}
			if(choicePropertyValueBuilderClass == null)
				choicePropertyValueBuilderClass = ChoicePropertyValueBuilder.class;		
			
			if(choicePropertyValueBuilderClass != null) {
				__choicePropertyValueBuilderClass__ = choicePropertyValueBuilderClass;
			}	
		}
		
		return __choicePropertyValueBuilderClass__ == null ? null : __inject__(__choicePropertyValueBuilderClass__).setObject(choice).setProperty(property).execute().getOutput();
	}
	
	@Override
	public String getChoiceLabel(Object choice) {
		return (String) getChoiceProperty(choice, ChoiceProperty.LABEL);
	}
	
	@Override
	public Object getChoiceValue(Object choice) {
		return getChoiceProperty(choice, ChoiceProperty.VALUE);
	}
	
	@Override
	public String getChoiceDescription(Object choice) {
		return (String) getChoiceProperty(choice, ChoiceProperty.DESCRIPTION);
	}
	
	@Override
	public Boolean getChoiceIsDisable(Object choice) {
		return (Boolean) getChoiceProperty(choice, ChoiceProperty.IS_DISABLE);
	}
	
	@Override
	public Boolean getChoiceIsLabelEscaped(Object choice) {
		return (Boolean) getChoiceProperty(choice, ChoiceProperty.IS_LABEL_ESCAPABLE);
	}
	
	@Override
	public Integer getMaximumNumberOfChoice() {
		return maximumNumberOfChoice;
	}
	
	@Override
	public InputChoice<CHOICE> setMaximumNumberOfChoice(Integer maximumNumberOfChoice) {
		this.maximumNumberOfChoice = maximumNumberOfChoice;
		return this;
	}
	
	@Override
	public ChoicesLayout getChoicesLayout() {
		return choicesLayout;
	}
	
	@Override
	public InputChoice<CHOICE> setChoicesLayout(ChoicesLayout choicesLayout) {
		this.choicesLayout = choicesLayout;
		return this;
	}
	
	@Override
	public List<Object> getChoicesByQuery(String query) {
		if(__maximumNumberOfChoice__ == null) {
			__maximumNumberOfChoice__ = getMaximumNumberOfChoice();
			if(__maximumNumberOfChoice__ == null)
				__maximumNumberOfChoice__ = MAXIMUM_NUMBER_OF_CHOICES_BY_QUERY;
		}
		InputChoiceBuilder<?,?> builder =  (InputChoiceBuilder<?,?>) getBuilder();
		ChoicesGetter choicesGetter = __inject__(__inject__(ValueHelper.class).defaultToIfNull(builder.getChoicesGetterClass(),ChoicesGetter.class));
		choices = choicesGetter.setFieldDeclaringClass(object.getClass()).setField(builder.getField()).setRequest(builder.getProperties().getRequest()).setContext(builder.getProperties().getContext()).setQuery(query)
				.setMaximumNumberOfChoice(__maximumNumberOfChoice__+1).execute().getOutput();
		return choices == null ? null : (List<Object>) choices.get();
		
		//return list;
	}
	
	/**/
	
	public static final String FIELD_CHOICES = "choices";
	
	public static final Integer MAXIMUM_NUMBER_OF_CHOICES_BY_QUERY = 10;
}
