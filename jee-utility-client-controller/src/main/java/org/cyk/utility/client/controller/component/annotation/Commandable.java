package org.cyk.utility.client.controller.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cyk.utility.system.action.SystemAction;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface Commandable {
	
	Class<? extends SystemAction> systemActionClass();
	
}
