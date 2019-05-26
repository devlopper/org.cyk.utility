package org.cyk.utility.client.controller.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cyk.utility.client.controller.component.input.choice.ChoicePropertyValueBuilder;
import org.cyk.utility.client.controller.component.input.choice.ChoicePropertyValueBuilderImpl;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface InputChoice {
	
	Class<? extends ChoicePropertyValueBuilder> choicePropertyValueBuilderClass() default ChoicePropertyValueBuilderImpl.class;
}
